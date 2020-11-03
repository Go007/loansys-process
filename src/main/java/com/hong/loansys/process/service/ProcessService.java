package com.hong.loansys.process.service;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

/**
 * @Description:
 * @Author wanghong
 * @Date 2020/11/3 16:19
 * @Version V1.0
 **/
@Service
public class ProcessService {

    @Autowired(required = false)
    protected RepositoryService repositoryService;

    /**
     * 动态部署流程
     *
     * @param resourceName
     * @param inputStream
     */
    public void dynamicDeployment(String resourceName, InputStream inputStream) {
        repositoryService.createDeployment().name(resourceName)
                .addInputStream(resourceName, inputStream).deploy();
    }

    /**
     * 获取所有最新版本的流程定义
     *
     * @return
     */
    public List<ProcessDefinition> getProcessDefinitions() {
        return repositoryService.createProcessDefinitionQuery().latestVersion()
                .orderByProcessDefinitionName().asc().list();
    }
}

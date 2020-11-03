package com.hong.loansys.process.service;

import com.hong.loansys.process.bo.ProcessDefinitionBo;
import com.hong.loansys.process.bo.UserInfoBo;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<ProcessDefinitionBo> findAllProcessDefinitions(UserInfoBo userInfoBo) {
        List<ProcessDefinition> processDefinitions = getProcessDefinitions();
        Collections.sort(processDefinitions, (o1, o2) -> o1.getKey().compareTo(o2.getKey()));
        List<ProcessDefinitionBo> processDefinitionBos = CollectionUtils.emptyIfNull(processDefinitions)
                .stream().map(item -> {
                    ProcessDefinitionBo bo = new ProcessDefinitionBo();
                    BeanUtils.copyProperties(item, bo);
                    return bo;
                }).collect(Collectors.toList());
        return processDefinitionBos;
    }
}

package com.hong.loansys.process.service;

import com.google.common.collect.Lists;
import com.hong.loansys.process.bo.ActRuJobBo;
import com.hong.loansys.process.bo.CustomJobBo;
import com.hong.loansys.process.bo.ProcessDefinitionBo;
import com.hong.loansys.process.bo.UserInfoBo;
import com.hong.loansys.process.doamin.ActRuJob;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ExecutionQuery;
import org.activiti.engine.runtime.Job;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
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

    @Autowired(required = false)
    private RuntimeService runtimeService;

    @Autowired(required = false)
    protected ManagementService managementService;

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

    public List<CustomJobBo> findAllJob(UserInfoBo userInfoBo, ActRuJobBo actRuJobBo) {
        String procDefKey = actRuJobBo.getProcDefKey();
        List<CustomJobBo> customJobsList = new ArrayList<>();
        if (StringUtils.isBlank(procDefKey)) {
            if (StringUtils.isNotBlank(actRuJobBo.getBusinessType()) && StringUtils
                    .isNotBlank(actRuJobBo.getBizId())) {
                ExecutionEntity executionEntityByBizKey = getExecutionEntityByBizKey(actRuJobBo.getBizId());
                if (executionEntityByBizKey != null
                        && executionEntityByBizKey.getProcessDefinitionId() != null) {
                    procDefKey = executionEntityByBizKey.getProcessDefinitionKey();
                }
            }
        }
        if (StringUtils.isNotBlank(procDefKey)) {
            customJobsList = getJobEntityListByTypeAndDefKey(actRuJobBo.getType(), procDefKey);
            CollectionUtils.emptyIfNull(customJobsList).stream().forEach(item -> {
                item.setBizKey(getBizKeyByProcInstId(item.getProcessInstanceId()));
            });
            if (null != customJobsList && !customJobsList.isEmpty()) {
                Collections.sort(customJobsList, (o1, o2) -> (null == o2.getBizKey() ? "" : o2.getBizKey())
                        .compareTo(null == o1.getBizKey() ? "" : o1.getBizKey()));
            }
        }
        return customJobsList;
    }

    public ExecutionEntity getExecutionEntityByBizKey(String bizKey) {
        ExecutionQuery query = runtimeService.createExecutionQuery();
        ExecutionEntity executionEntity = (ExecutionEntity) query
                .processInstanceBusinessKey(bizKey, false).singleResult();
        return executionEntity;
    }

    public List<CustomJobBo> getJobEntityListByTypeAndDefKey(String type, String procDefKey) {
        List<Job> jobs = getJobEntityList(type, procDefKey);
        return getCustomJobEntityList(jobs, type, procDefKey);
    }

    public List<Job> getJobEntityList(String type, String procDefKey) {
        List<Job> jobList = Lists.newArrayList();
        if ("message".equalsIgnoreCase(type)) {
            List<Job> findJobList = managementService.createJobQuery().messages().orderByJobDuedate()
                    .desc().list();
            List<Job> findDeadLetterJobList = managementService.createDeadLetterJobQuery().messages()
                    .orderByJobDuedate().desc().list();
            List<Job> findSuspendedJobList = managementService.createSuspendedJobQuery().messages()
                    .orderByJobDuedate().desc().list();
            List<Job> findTimerJobList = managementService.createTimerJobQuery().messages()
                    .orderByJobDuedate().desc().list();
            jobList.addAll(findJobList);
            jobList.addAll(findDeadLetterJobList);
            jobList.addAll(findSuspendedJobList);
            jobList.addAll(findTimerJobList);
        } else if ("timer".equalsIgnoreCase(type)) {
            List<Job> findJobList = managementService.createJobQuery().timers().orderByJobDuedate().desc()
                    .list();
            List<Job> findDeadLetterJobList = managementService.createDeadLetterJobQuery().timers()
                    .orderByJobDuedate().desc().list();
            List<Job> findSuspendedJobList = managementService.createSuspendedJobQuery().timers()
                    .orderByJobDuedate().desc().list();
            List<Job> findTimerJobList = managementService.createTimerJobQuery().timers()
                    .orderByJobDuedate().desc().list();
            jobList.addAll(findJobList);
            jobList.addAll(findDeadLetterJobList);
            jobList.addAll(findSuspendedJobList);
            jobList.addAll(findTimerJobList);
        } else {
            List<Job> findJobList = managementService.createJobQuery().orderByJobDuedate().desc().list();
            List<Job> findDeadLetterJobList = managementService.createDeadLetterJobQuery()
                    .orderByJobDuedate().desc().list();
            List<Job> findSuspendedJobList = managementService.createSuspendedJobQuery()
                    .orderByJobDuedate().desc().list();
            List<Job> findTimerJobList = managementService.createTimerJobQuery().orderByJobDuedate()
                    .desc().list();
            jobList.addAll(findJobList);
            jobList.addAll(findDeadLetterJobList);
            jobList.addAll(findSuspendedJobList);
            jobList.addAll(findTimerJobList);
        }
        List<Job> jobs = CollectionUtils.emptyIfNull(jobList).stream().filter(t -> t != null).collect(
                Collectors.toList());
        return jobs;
    }

    private List<CustomJobBo> getCustomJobEntityList(List<Job> jobList, String type,
                                                     String procDefKey) {
        List<CustomJobBo> customJobEntityList = new ArrayList<>();
        HashSet<String> stringSet = new HashSet<>();
        if (StringUtils.isNotBlank(procDefKey)) {
            List<ProcessDefinition> processDefinitions =
                    repositoryService.createProcessDefinitionQuery().processDefinitionKey(procDefKey)
                            .orderByProcessDefinitionVersion().desc().list();
            List<String> procDefIds = new ArrayList<>();
            for (ProcessDefinition processDefinition : processDefinitions) {
                procDefIds.add(processDefinition.getId());
            }
            StringBuilder sb = new StringBuilder();
            sb.append("'").append(StringUtils.join(procDefIds.toArray(), "','")).append("'");
            List<Execution> executionList =
                    runtimeService.createNativeExecutionQuery().sql(
                            "select * from act_ru_execution where proc_def_id_ in (" + sb.toString()
                                    + ") and parent_id_ is null").list();
            for (Job job : jobList) {
                for (Execution execution : executionList) {
                    if (job.getProcessInstanceId().equalsIgnoreCase(execution.getProcessInstanceId())) {
                        stringSet.add(job.getProcessInstanceId());
                    }
                }
            }
        } else {
            for (Job job : jobList) {
                stringSet.add(job.getProcessInstanceId());
            }
        }
        for (String str : stringSet) {
            List<ActRuJob> actRuJobList = actRuJobRepository.getAllJob(str, type);
            for (ActRuJob actRuJob : actRuJobList) {
                CustomJobBo customJobEntity = new CustomJobBo();
                customJobEntity.setId(actRuJob.getId());
                customJobEntity.setProcessDefinitionId(actRuJob.getProcDefId());
                customJobEntity.setProcessInstanceId(actRuJob.getProcessInstanceId());
                customJobEntity.setRetries(actRuJob.getRetries());
                customJobEntity.setDuedate(actRuJob.getDuedate());
                customJobEntity.setLockExpirationTime(actRuJob.getLockExpTime());
                customJobEntity.setExceptionMessage(actRuJob.getExceptionMsg());
                customJobEntity.setJobType(actRuJob.getType());
                customJobEntityList.add(customJobEntity);
            }
        }
        return customJobEntityList;
    }

}

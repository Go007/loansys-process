package com.hong.loansys.process.doamin;

import java.util.Date;

public class ActRuJob {

  private String id;

  private Integer rev;

  private String type;

  private Date lockExpTime;

  private String lockOwner;

  private Boolean exclusive;

  private String executionId;

  private String processInstanceId;

  private String procDefId;

  private Integer retries;

  private String exceptionStackId;

  private String exceptionMsg;

  private Date duedate;

  private String repeat;

  private String handlerType;

  private String handlerCfg;

  private String tenantId;

  private String queryJob;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Integer getRev() {
    return rev;
  }

  public void setRev(Integer rev) {
    this.rev = rev;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Date getLockExpTime() {
    return lockExpTime;
  }

  public void setLockExpTime(Date lockExpTime) {
    this.lockExpTime = lockExpTime;
  }

  public String getLockOwner() {
    return lockOwner;
  }

  public void setLockOwner(String lockOwner) {
    this.lockOwner = lockOwner;
  }

  public Boolean getExclusive() {
    return exclusive;
  }

  public void setExclusive(Boolean exclusive) {
    this.exclusive = exclusive;
  }

  public String getExecutionId() {
    return executionId;
  }

  public void setExecutionId(String executionId) {
    this.executionId = executionId;
  }

  public String getProcessInstanceId() {
    return processInstanceId;
  }

  public void setProcessInstanceId(String processInstanceId) {
    this.processInstanceId = processInstanceId;
  }

  public String getProcDefId() {
    return procDefId;
  }

  public void setProcDefId(String procDefId) {
    this.procDefId = procDefId;
  }

  public Integer getRetries() {
    return retries;
  }

  public void setRetries(Integer retries) {
    this.retries = retries;
  }

  public String getExceptionStackId() {
    return exceptionStackId;
  }

  public void setExceptionStackId(String exceptionStackId) {
    this.exceptionStackId = exceptionStackId;
  }

  public String getExceptionMsg() {
    return exceptionMsg;
  }

  public void setExceptionMsg(String exceptionMsg) {
    this.exceptionMsg = exceptionMsg;
  }

  public Date getDuedate() {
    return duedate;
  }

  public void setDuedate(Date duedate) {
    this.duedate = duedate;
  }

  public String getRepeat() {
    return repeat;
  }

  public void setRepeat(String repeat) {
    this.repeat = repeat;
  }

  public String getHandlerType() {
    return handlerType;
  }

  public void setHandlerType(String handlerType) {
    this.handlerType = handlerType;
  }

  public String getHandlerCfg() {
    return handlerCfg;
  }

  public void setHandlerCfg(String handlerCfg) {
    this.handlerCfg = handlerCfg;
  }

  public String getTenantId() {
    return tenantId;
  }

  public void setTenantId(String tenantId) {
    this.tenantId = tenantId;
  }

  public String getQueryJob() {
    return queryJob;
  }

  public void setQueryJob(String queryJob) {
    this.queryJob = queryJob;
  }
}

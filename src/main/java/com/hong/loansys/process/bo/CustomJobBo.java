package com.hong.loansys.process.bo;

import lombok.Data;
import org.activiti.engine.impl.persistence.entity.JobEntityImpl;

@Data
public class CustomJobBo extends JobEntityImpl {

  private String bizKey;

  public String getBizKey() {
    return bizKey;
  }

  public void setBizKey(String bizKey) {
    this.bizKey = bizKey;
  }
}

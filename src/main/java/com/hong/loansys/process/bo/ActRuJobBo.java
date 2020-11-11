package com.hong.loansys.process.bo;

import lombok.Data;

import java.util.List;

@Data
public class ActRuJobBo {

  private String taskId;

  private String processId;

  private String procInstId;

  private String businessType;

  private String bizId;

  private List<String> category;

  private String procDefKey;

  private String type;
}

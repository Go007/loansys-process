package com.hong.loansys.process.bo;

import lombok.Data;

import java.util.Map;

@Data
public class ManualStartProcessInfoBo {

  private String processDefinitionKey;
  private String bizKey;
  private Map<String, Object> params;
}

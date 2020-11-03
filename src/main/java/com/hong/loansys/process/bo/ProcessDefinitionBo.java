package com.hong.loansys.process.bo;

import lombok.Data;

@Data
public class ProcessDefinitionBo {

  private String id;
  private String name;
  private String category;
  private String key;
  private String description;
  private int version;
  private int revision;
  private String deploymentId;
  private String resourceName;
  private String tenantId;
  private Integer historyLevel;
  private String diagramResourceName;
  private boolean isGraphicalNotationDefined;
  private boolean hasStartFormKey;
  private int suspensionState;
  private boolean isIdentityLinksInitialized;
}

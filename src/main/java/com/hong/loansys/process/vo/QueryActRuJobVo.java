package com.hong.loansys.process.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class QueryActRuJobVo {

  @ApiModelProperty(value = "任务id", required = true)
  private String taskId;

  @ApiModelProperty("流程id")
  private String processId;

  @ApiModelProperty("流程实例id")
  private String procInstId;

  @ApiModelProperty("业务类型（用户-USER，项目-PROJECT，授信-CREDIT_APPLY，支用-LOAN_APPLY，放款-ISSUE，还款-REPAYMENT）")
  private String businessType;

  @ApiModelProperty("业务编号Id")
  private String bizId;

  @ApiModelProperty("任务分类()")
  private List<String> category;

  @ApiModelProperty("流程定义")
  private String procDefKey;

  @ApiModelProperty("任务类型（message|timer|null）")
  private String type;

}

package com.hong.loansys.process.domain;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author wanghong
 * @since 2020-11-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ActRuJob implements Serializable {

    private static final long serialVersionUID=1L;

    private String id;

    private Integer rev;

    private String type;

    private LocalDateTime lockExpTime;

    private String lockOwner;

    private Boolean exclusive;

    private String executionId;

    private String processInstanceId;

    private String procDefId;

    private Integer retries;

    private String exceptionStackId;

    private String exceptionMsg;

    private LocalDateTime duedate;

    private String repeat;

    private String handlerType;

    private String handlerCfg;

    private String tenantId;


}

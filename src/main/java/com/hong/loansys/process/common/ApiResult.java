package com.hong.loansys.process.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("API响应对象")
public class ApiResult<T> {

  @ApiModelProperty(value = "响应CODE", required = true)
  private String code;
  @ApiModelProperty(value = "处理结果对象", required = false)
  private T data;
  @ApiModelProperty(value = "响应描述", required = false)
  private String message;

  public ApiResult() {
  }

  public ApiResult(ErrorCode errorCode, String message, T data) {
    this.code = errorCode.name();
    this.message = message;
    this.data = data;
  }

  public ApiResult(ErrorCode errorCode, T data) {
    this(errorCode, errorCode.getDesc(), data);
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public static ApiResult success() {
    return success(null);
  }

  public static <T> ApiResult success(T data) {
    return new ApiResult<>(ErrorCode.SUCCESS, data);
  }

  public static <T> ApiResult success(String message, T data) {
    return new ApiResult<>(ErrorCode.SUCCESS, message, data);
  }

  public static ApiResult error(ErrorCode errorCode, String message) {
    return new ApiResult<>(errorCode, message, null);
  }


  public static ApiResult error(ErrorCode errorCode) {
    return new ApiResult<>(errorCode, errorCode.getDesc(), null);
  }

  public static <T> ApiResult error(ErrorCode errorCode, String message, T data) {
    return new ApiResult<>(errorCode, message, data);
  }
}

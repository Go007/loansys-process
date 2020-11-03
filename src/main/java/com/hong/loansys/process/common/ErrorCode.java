package com.hong.loansys.process.common;

public enum ErrorCode {
  // 20000
  SUCCESS(20000, "SUCCESS"),

  SESSION_TIMEOUT(302, "Session Timeout"),

  // 30000: rule verification error
  RULE_PARSE_ERROR(30003, "Rule Parsing Error"),
  //
  COMMON_VALIDATION_FAIL(30004, "Validation fail"),
  //
  PARAM_MISS(30005, "Required Parameter is missing"),
  //
  MONTH_QUOTA_FAIL(30006, "month quota fail"),
  //
  DATE_QUOTA_FAIL(30007, "date quota fail"),

  // 40000
  COMMON_ERROR(40000, "Request fail"),
  //
  INVALID_PARAMETER(40001, "Invalid Parameter"),
  //
  INVALID_OPERATION(40002, "Invalid Operation"),
  //
  FORBIDDEN(40003, "Auth failed"),
  //
  NOT_FOUND(40004, "Data not found"),
  //
  LOSE_EFFICACY(40005, "Data lose effectiveness"),
  //
  TIME_OUT(40006, "Request time out"),
  //
  CLIENT_EXP(40007, "client invoke fail"),
  //
  DATA_EXISTED(40008, "Data existed"),

  CLIENT_RESPONSE_ERR(40009, "client response invalid"),

  // 50000
  INTERNAL_ERROR(50000, "Internal Error"),

  INVALID_SHELVE_PARAMETER(50001, "Invalid shelve parameter"),

  TERMINATE_ACTIVITI(50002, "Internal Error, activiti workflow should be terminated"),

  CRC_PROXY_ERROR(50003, "CRC Proxy Error");
  private int code;
  private String desc;

  ErrorCode(int code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public static boolean isOk(int code) {
    return SUCCESS.getCode() == code;
  }
}

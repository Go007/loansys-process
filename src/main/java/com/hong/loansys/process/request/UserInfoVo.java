package com.hong.loansys.process.request;

import com.hong.loansys.process.common.RequestHeaderParamVo;
import com.hong.loansys.process.util.JsonUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

@Data
@ToString
@Slf4j
public class UserInfoVo implements RequestHeaderParamVo {

  @ApiModelProperty(value = "用户id", required = true)
  private String id;
  @ApiModelProperty(value = "用户登录名")
  private String userName;
  @ApiModelProperty(value = "用户名", required = true)
  private String name;
  @ApiModelProperty(value = "邮箱", required = true)
  private String email;
  @ApiModelProperty(value = "手机号")
  private String cellphone;
  @ApiModelProperty(value = "用户组")
  private List<String> userGroups;
  @ApiModelProperty(value = "拥有角色")
  private List<String> roles;
  @ApiModelProperty(value = "拥有权限")
  private List<String> permissions;
  @ApiModelProperty(value = "拥有项目")
  private List<String> projectIds;

  @Override
  public String toString() {
    return JsonUtils.toJsonHasNull(this);
  }

  public static String encodeHttpHeaderValue(String name) {
    try {
      if (StringUtils.isNotBlank(name)) {
        name = URLEncoder.encode(name, "UTF-8");
      }
    } catch (Exception e) {
      log.warn("UserInfoVo.encodeHttpHeaderValue异常{}", name);
    }
    return name;
  }

  public static String decodeHttpHeaderValue(String name) {
    try {
      if (StringUtils.isNotBlank(name)) {
        name = URLDecoder.decode(name, "UTF-8");
      }
    } catch (Exception e) {
      log.warn("UserInfoVo.decodeHttpHeaderValue异常{}", name);
    }
    return name;
  }

  @Override
  public void encodeHttpHeaderValue() {
    this.setName(encodeHttpHeaderValue(this.getName()));
  }

  @Override
  public void decodeHttpHeaderValue() {
    this.setName(decodeHttpHeaderValue(this.getName()));
  }

}

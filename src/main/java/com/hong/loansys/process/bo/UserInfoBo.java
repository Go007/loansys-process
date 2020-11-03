package com.hong.loansys.process.bo;

import lombok.Data;

import java.util.List;

@Data
public class UserInfoBo {

  private String id;
  private String userName;
  private String name;
  private String email;
  private String cellphone;
  private List<String> userGroups;
  private List<String> roles;
  private List<String> permissions;
  private List<String> projectIds;

}

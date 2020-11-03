package com.hong.loansys.process.controller;

import com.hong.loansys.process.bo.UserInfoBo;
import com.hong.loansys.process.request.UserInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

@Slf4j
public class BaseController {

  protected UserInfoBo buildUserInfoBo(UserInfoVo userInfoVo) {
    UserInfoBo userInfoBo = new UserInfoBo();
    if (userInfoVo != null) {
      BeanUtils.copyProperties(userInfoVo, userInfoBo);
    }
    return userInfoBo;
  }

}

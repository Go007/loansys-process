package com.hong.loansys.process.controller;

import com.hong.loansys.process.bo.ProcessDefinitionBo;
import com.hong.loansys.process.bo.UserInfoBo;
import com.hong.loansys.process.common.ApiResult;
import com.hong.loansys.process.config.filter.TransRequestHeader;
import com.hong.loansys.process.request.UserInfoVo;
import com.hong.loansys.process.service.ProcessService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @Description:
 * @Author wanghong
 * @Date 2020/11/3 16:07
 * @Version V1.0
 **/
@Slf4j
@RestController
@RequestMapping("/process")
public class ProcessController extends BaseController {

    @Autowired
    private ProcessService processService;

    @ApiOperation(value = "动态部署流程定义")
    @RequestMapping(value = "/act/manager/deploy", method = RequestMethod.POST)
    public ApiResult dynamicDeployment(
            @TransRequestHeader(value = "user-info", required = false) UserInfoVo userInfoVo,
            @RequestParam MultipartFile file)
            throws IOException {
        log.info(
                "dynamicDeployment userInfoVo: {},originalFilename: {}", userInfoVo,
                file.getOriginalFilename());
        processService.dynamicDeployment(file.getOriginalFilename(), file.getInputStream());
        return ApiResult.success();
    }

    @ApiOperation(value = "所有流程定义")
    @RequestMapping(value = "/act/manager/all-process-definitions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ApiResult findAllProcessDefinitions(
            @TransRequestHeader(value = "user-info", required = false) UserInfoVo userInfoVo) {
        log.info("findAllProcessDefinitions userInfoVo: {}", userInfoVo);
        UserInfoBo userInfoBo = buildUserInfoBo(userInfoVo);
        List<ProcessDefinitionBo> allProcessDefinitions = processService
                .findAllProcessDefinitions(userInfoBo);
        return ApiResult.success(allProcessDefinitions);
    }

}

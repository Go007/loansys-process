package com.hong.loansys.process.controller;

import com.hong.loansys.process.bo.ActRuJobBo;
import com.hong.loansys.process.bo.CustomJobBo;
import com.hong.loansys.process.bo.ProcessDefinitionBo;
import com.hong.loansys.process.bo.UserInfoBo;
import com.hong.loansys.process.common.ApiResult;
import com.hong.loansys.process.config.filter.TransRequestHeader;
import com.hong.loansys.process.request.UserInfoVo;
import com.hong.loansys.process.service.ProcessService;
import com.hong.loansys.process.vo.QueryActRuJobVo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
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

    @ApiOperation(value = "业务目前所有待执行任务")
    @RequestMapping(value = "/act/manager/run-jobs", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ApiResult findAllJob(
            @TransRequestHeader(value = "user-info", required = false) UserInfoVo userInfoVo,
            @RequestBody QueryActRuJobVo queryActRuJobVo) throws Exception {
        log.info("findAllJob userInfoVo: {},queryActRuJobVo: {}", userInfoVo, queryActRuJobVo);
        UserInfoBo userInfoBo = buildUserInfoBo(userInfoVo);
        ActRuJobBo actRuJobBo = buildActRuJobBo(queryActRuJobVo);
        List<CustomJobBo> allActRuJobBos = processService.findAllJob(userInfoBo, actRuJobBo);
        return ApiResult.success(allActRuJobBos);
    }

    public ActRuJobBo buildActRuJobBo(QueryActRuJobVo queryActRuJobVo) {
        ActRuJobBo actRuJobBo = new ActRuJobBo();
        BeanUtils.copyProperties(queryActRuJobVo, actRuJobBo);
        return actRuJobBo;
    }

}

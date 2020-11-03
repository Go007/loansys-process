package com.hong.loansys.process.config.filter;

import com.hong.loansys.process.common.RequestHeaderParamVo;
import com.hong.loansys.process.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
public class TransRequestHeaderParamResolver implements HandlerMethodArgumentResolver {

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.hasParameterAnnotation(TransRequestHeader.class);
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
    HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
    TransRequestHeader transRequestHeader = parameter
        .getParameterAnnotation(TransRequestHeader.class);
    boolean required = transRequestHeader.required();
    Object result = null;
    String name = transRequestHeader.value();
    if (StringUtils.isNotBlank(name)) {
      String jsonBody = request.getHeader(name);
      if (StringUtils.isNotBlank(jsonBody)) {
        Class<?> declaringClass = parameter.getParameterType();
        result = JsonUtils.toObj(jsonBody, declaringClass);
        if (result instanceof RequestHeaderParamVo) {
          ((RequestHeaderParamVo) result).decodeHttpHeaderValue();
        }
      }
      if (required && result == null) {
        throw new IllegalArgumentException(name + "is null");
      }
    }
    return result;
  }
}

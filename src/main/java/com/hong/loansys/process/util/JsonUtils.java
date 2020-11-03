package com.hong.loansys.process.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @Description:
 * @Author wanghong
 * @Date 2020/11/3 16:25
 * @Version V1.0
 **/
@Slf4j
public class JsonUtils {

    private static final String EMPTY_JSON = "{}";
    private static final String EMPTY_JSONS = "[]";

    private static final ObjectMapper MAPPER;

    private static final ObjectMapper MAPPER_HAS_NULL;

    static {
        MAPPER = new ObjectMapper();
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        MAPPER.setSerializationInclusion(Include.NON_NULL);

        MAPPER_HAS_NULL = new ObjectMapper();
        MAPPER_HAS_NULL.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        MAPPER_HAS_NULL.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    /**
     * java实体对象转换成json字符串
     * 默认对象中出现值为null时 隐藏json中的key.
     */
    public static String toJson(Object obj) {
        return JsonUtils.toJson(obj, MAPPER);
    }

    /**
     * java实体对象转换成json字符串.
     */
    private static String toJson(Object obj, ObjectMapper mapper) {
        if (obj == null) {
            return StringUtils.EMPTY;
        }

        try {
            StringWriter writer = new StringWriter();
            mapper.writeValue(writer, obj);
            return writer.toString();
        } catch (IOException e) {
            log.error("JsonUtils transformation obj to string has error!", e);
        }
        return StringUtils.EMPTY;
    }

    /**
     * java实体对象转换成json字符串
     * 默认对象中出现值为null时 显示json中的key,其值为null.
     */
    public static String toJsonHasNull(Object obj) {
        return JsonUtils.toJson(obj, MAPPER_HAS_NULL);
    }

    /**
     * json字符串转换为对应的java实体对象.
     */
    public static <T> T toObj(String jsonStr, Class<T> objClass) {
        if (isEmpty(jsonStr)) {
            return newInstance(objClass);
        }

        try {
            return MAPPER.readValue(jsonStr, objClass);
        } catch (IOException e) {
            log.error("JsonUtils transformation string to obj has Eroor!", e);
        }
        return newInstance(objClass);
    }

    private static <T> T newInstance(Class<T> objClass) {
        try {
            return objClass.newInstance();
        } catch (Exception e) {
            log.error("failed to create new instance", e);
            throw new RuntimeException("cannot create new instance", e);
        }
    }

    public static <T> List<T> toObjList(String jsonStr, Class<T> objClass) {
        if (isEmpty(jsonStr)) {
            return Collections.emptyList();
        }

        try {
            return MAPPER.readValue(jsonStr, getCollectionType(ArrayList.class, objClass));
        } catch (IOException e) {
            log.error("JsonUtils transformation string to objList has Eroor!", e);
        }
        return Collections.emptyList();
    }

    public static boolean isEmpty(String content) {
        return StringUtils.isBlank(content) || EMPTY_JSON.equals(content) || EMPTY_JSONS
                .equals(content);
    }

    public static boolean isNotEmpty(String content) {
        return !isEmpty(content);
    }


    private static JavaType getCollectionType(Class<?> paramClass, Class<?>... elementClasses) {
        return MAPPER.getTypeFactory().constructParametricType(paramClass, elementClasses);
    }

    public static Map<String, Object> jsonToMap(String jsonStr) {
        Map<String, Object> tempMap = null;
        try {
            tempMap = MAPPER.readValue(jsonStr, new TypeReference<Map<String, Object>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tempMap;
    }
}


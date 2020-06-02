package com.lazydsr.base.demo.basedemo.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class JsonUtil {
    private static final Logger log = LogManager.getLogger();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static String toJson(Object obj) {
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("toJson error=", e);
        }
        return "";
    }

    public static <T> T toBean(String str, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(str, clazz);
        } catch (JsonProcessingException e) {
            log.error("toBean error=", e);
        }
        return null;
    }

    public static <T> T toBean(String str, TypeReference<T> type) {
        try {
            return (T)OBJECT_MAPPER.readValue(str, type);
        } catch (JsonProcessingException e) {
            log.error("toBean error=", e);
        }
        return null;
    }

    public Map toMap(String str) throws IOException {
        return OBJECT_MAPPER.readValue(str, Map.class);
    }

    /**
     * 比较两个json字符串，并返回第一个json中的差异键值对
     *
     * @param json1
     *            json字符串
     * @param json2
     *            json字符串
     * @return map
     */
    public Map<Object, Object> compare(String json1, String json2) {
        Map m1 = null;
        Map m2 = null;
        try {
            m1 = toMap(json1);
            m2 = toMap(json2);
        } catch (IOException e) {
            log.warn("compare e=", e);
            return Collections.emptyMap();
        }

        Map<Object, Object> result = new HashMap<>(16);
        Map<String, Object> finalM = m2;
        m1.forEach((key, value) -> {
            if (finalM.containsKey(key) && value.equals(finalM.get(key))) {
                return;
            }
            result.put(key, value);
        });
        return result;
    }

    /**
     * 判断是否是一个JSON空对象
     * 
     * @param jsonStr
     * @return
     */
    public Boolean isEmptyJson(String jsonStr) {
        if (StringUtils.isNotBlank(jsonStr)) {
            try {
                JsonNode jsonNode = OBJECT_MAPPER.readTree(jsonStr);
                return jsonNode.size() > 0 ? Boolean.FALSE : Boolean.TRUE;
            } catch (IOException e) {
                log.error("readTree error,the jsonStr is [{}]", jsonStr);
            }
        }

        return Boolean.TRUE;
    }
}

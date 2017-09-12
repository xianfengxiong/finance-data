package cn.wanru.fund.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Objects;

/**
 * @author xxf
 * @since 2017/9/12
 */
public class JsonUtil {

    private static final ObjectMapper object_mapper = new ObjectMapper();

    private JsonUtil() {

    }

    public static String toJson(Object value) {
        Objects.requireNonNull(value);
        try {
            return object_mapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromJson(String json,Class<T> clazz) {
        try {
            return object_mapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

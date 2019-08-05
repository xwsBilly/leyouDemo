package com.leyou.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JsonUtils {
    public static final ObjectMapper mapper = new ObjectMapper();

    public static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    /**
     * json序列化
     *
     * @param obj
     * @return
     */
    public static String toString(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj.getClass() == String.class) {
            return (String) obj;
        }
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.error("json序列化出错：" + obj, e);
            return null;
        }
    }

    /**
     * json反序列化
     *
     * @param json
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> T toBean(String json, Class<T> tClass) {
        try {
            return mapper.readValue(json, tClass);
        } catch (IOException e) {
            logger.error("json解析出错：" + json, e);
            return null;
        }
    }

    /**
     * json反序列化
     *
     * @param json
     * @param eClass
     * @param <E>
     * @return
     */
    public static <E> List<E> toList(String json, Class<E> eClass) {
        try {
            return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, eClass));
        } catch (IOException e) {
            logger.error("json解析出错：" + json, e);
            return null;
        }
    }

    /**
     * json反序列化
     *
     * @param json
     * @param kClass
     * @param vClass
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> Map<K, V> toMap(String json, Class<K> kClass, Class<V> vClass) {
        try {
            return mapper.readValue(json, mapper.getTypeFactory().constructMapType(Map.class, kClass, vClass));
        } catch (IOException e) {
            logger.error("json解析出错：" + json, e);
            return null;
        }
    }

    public static <T> T nativeRead(String json, TypeReference<T> typeReference) {
        try {
            return mapper.readValue(json, typeReference);
        } catch (IOException e) {
            logger.error("json解析出错：" + json, e);
            return null;
        }
    }

    /*
    // 测试使用jsonUtils
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class User{
        String name;
        Integer age;
    }

    public static void main(String[] args) {
//        User user = new User("jack",21);
        // toString
//        String json=toString(user);
//        System.out.println("json = " + json);

        // 反序列化
//        String json=toString(user);
//        User user1 = toBean(json, User.class);
//        System.out.println("user1 = " + user1);

        // toList
//        String json="[20,-10,1,2]";
        // 需要传这个集合中元素的类型
//        List<Integer> list = toList(json, Integer.class);
//        System.out.println("list = " + list);

        // toMap
//        String json="{\"name\": \"jack\",\"age\": \"21\"}";
//        Map<String, String> map = toMap(json, String.class, String.class);
//        System.out.println("map = " + map);

        // 处理复杂的json
        String json="[{\"name\": \"jack\",\"age\": \"21\"},{\"name\": \"rose\",\"age\": \"18\"}]";
        // TypeReference是一个类型引用，可以在对象中写泛型
        List<Map<String, String>> maps = nativeRead(json, new TypeReference<List<Map<String, String>>>() {
        });
        if (maps != null) {
            for (Map<String, String> map : maps) {
                System.out.println("map = " + map);
            }
        }
    }*/
}

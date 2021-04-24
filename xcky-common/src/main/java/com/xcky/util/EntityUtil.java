package com.xcky.util;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 实体工具类
 *
 * @author lbchen
 */
public class EntityUtil {
    
    /**
     * 将对象直接的属性直接转化成Map的对象
     *
     * @param obj 需要转化成Map的对象
     * @return 基本属性的属性名称和值的映射
     */
    public static Map<String, Object> entityToMap(Object obj) {
        Map<String, Object> map = new HashMap<>(16);
        if (obj == null) {
            return map;
        }
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                map.put(field.getName(), field.get(obj));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return map;
    }
    
    /**
     * 将对象直接的属性直接转化成Map的对象
     *
     * @param obj 需要转化成Map的对象
     * @return 基本属性的属性名称和值的映射
     */
    public static Map<String, String> entityToStringMap(Object obj) {
        Map<String, String> map = new HashMap<>(16);
        if (obj == null) {
            return map;
        }
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                Object val = field.get(obj);
                if(null != val) {
                    String fieldName = field.getName();
                    if(field.isAnnotationPresent(JsonProperty.class)) {
                        fieldName = field.getAnnotation(JsonProperty.class).value();
                    }
                    if(field.isAnnotationPresent(JSONField.class)) {
                        fieldName = field.getAnnotation(JSONField.class).name();
                    }
                    map.put(fieldName, ""+val);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return map;
    }
}

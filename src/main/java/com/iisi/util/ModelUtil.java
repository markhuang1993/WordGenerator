package com.iisi.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public final class ModelUtil {
    private static Map<Class, Field[]> fieldCache = new HashMap<>();

    private ModelUtil() {
        throw new AssertionError();
    }

    public static Map<String, Object> getFieldsMap(Object model) throws IllegalAccessException {
        Map<String, Object> result = new LinkedHashMap<>();
        Class<?> cls = model.getClass();
        Field[] fields = fieldCache.get(cls);
        if (fields == null) {
            fields = model.getClass().getDeclaredFields();
        }
        for (Field field : fields) {
            field.setAccessible(true);
            result.put(field.getName(), field.get(model));
        }
        fieldCache.put(cls, fields);
        return result;
    }
}

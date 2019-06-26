package com.iisi.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;

public final class ModelUtil {
    private static Map<Class, Field[]> fieldCache = new HashMap<>();

    private ModelUtil() {
        throw new AssertionError();
    }

    public static Map<String, Object> getFieldsMap(Object model) throws IllegalAccessException {
        return getFieldsMap(model, o -> true);
    }

    public static Map<String, Object> getFieldsMap(Object model, Predicate<Object> filter) throws IllegalAccessException {
        Map<String, Object> result = new LinkedHashMap<>();
        Class<?> cls = model.getClass();
        Field[] fields = fieldCache.get(cls);
        if (fields == null) {
            fields = model.getClass().getDeclaredFields();
        }
        for (Field field : fields) {
            field.setAccessible(true);
            Object val = field.get(model);
            if (val == null) {
                continue;
            }
            if (filter == null || filter.test(val)) {
                result.put(field.getName(), val);
            }
        }
        fieldCache.put(cls, fields);
        return result;
    }
}

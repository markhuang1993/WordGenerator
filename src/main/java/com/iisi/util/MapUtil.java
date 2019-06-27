package com.iisi.util;

import java.util.List;
import java.util.Map;

public final class MapUtil {
    private MapUtil() {
        throw new AssertionError();
    }

    @SuppressWarnings("unchecked")
    public static <T> T getMapValueByPath(Map m, String path) {

        String[] keys = path.split("\\.");

        Object temp = m.get(keys[0]);
        StringBuilder nowPath = new StringBuilder();

        for (int i = 1; i < keys.length; i++) {
            String key = keys[i];
            nowPath.append(key).append(".");
            if (temp instanceof Map) {
                String arrRegx = "^(.*?)\\[(\\d+)]$";
                if (key.matches(arrRegx)) {
                    key = key.replaceAll(arrRegx, "$1,$2");
                    String[] sp = key.split(",");
                    temp = ((Map) temp).get(sp[0]);
                    temp = ((List) temp).get(Integer.parseInt(sp[1]));
                } else {
                    temp = ((Map) temp).get(key);
                }
            } else {
                throw new IllegalArgumentException(String.format("Path:%s not exist", nowPath.toString()));
            }
        }

        return ((T) temp);
    }

}
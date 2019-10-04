package com.iisi.util;

import java.util.HashMap;
import java.util.Map;

public final class XmlUtil {

    private static final Map<String, String> SPECIAL_STRING_MAP;

    static {
        Map<String, String> specialStringMap = new HashMap<>();
        specialStringMap.put("'", "&apos;");
        specialStringMap.put("\"", "&quot;");
        specialStringMap.put("<", "&lt;");
        specialStringMap.put(">", "&gt;");
        specialStringMap.put("&", "&amp;");
        SPECIAL_STRING_MAP = specialStringMap;
    }

    private XmlUtil() {
        throw new AssertionError();
    }

    public static String specialStringToXmlFormat(String str) {
        StringBuilder sb = new StringBuilder();
        for (char c : str.toCharArray()) {
            String s = String.valueOf(c);
            String s1 = SPECIAL_STRING_MAP.get(s);
            if (s1 != null) {
                sb.append(s1);
            } else {
                sb.append(s);
            }
        }
        return sb.toString();
    }

}

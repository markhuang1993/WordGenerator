package com.iisi.util;

import java.io.File;
import java.net.URL;

public final class ResourceUtil {
    private ResourceUtil() {
        throw new AssertionError();
    }

    public static File getClassPathResource(String resourcePath) {
        URL resource = ResourceUtil.class.getClassLoader().getResource(resourcePath);
        if (resource == null) return null;
        File file = new File(resource.getFile());
        return file.exists() ? file : null;
    }
}

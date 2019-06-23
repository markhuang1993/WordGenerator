package com.iisi.util;

import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public final class FileUtil {
    private FileUtil() {
        throw new AssertionError();
    }

    public static String toBase64Encoding(File file) throws IOException {
        return new BASE64Encoder().encode(Files.readAllBytes(file.toPath()));
    }

    public static List<File> getAllFilesInDirectory(File dir) {
        if (dir == null || !dir.isDirectory()) return new ArrayList<>();
        List<File> result = new ArrayList<>();
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    result.add(file);
                }else if(file.isDirectory()){
                    result.addAll(getAllFilesInDirectory(file));
                }
            }
        }
        return result;
    }

}

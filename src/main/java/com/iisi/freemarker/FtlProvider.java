package com.iisi.freemarker;

import com.iisi.util.FileUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FtlProvider {

    private static final Configuration CONFIGURATION;
    private Map<String, Template> templateCacheMap = new HashMap<>();

    static {
        CONFIGURATION = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        CONFIGURATION.setDefaultEncoding("utf-8");
    }

    public FtlProvider(File templateBaseDir) {
        List<File> files = FileUtil.getAllFilesInDirectory(templateBaseDir);
        List<File> ftlFiles = files
                .stream()
                .filter(f -> f.getName().matches(".*?\\.ftl"))
                .collect(Collectors.toList());

        for (File ftlFile : ftlFiles) {
            try {
                String relativePath = ftlFile.getAbsolutePath()
                        .replace(templateBaseDir.getAbsolutePath(), "")
                        .replace("\\", "/")
                        .replaceAll("^/(.*)$","$1");
                String templateName = relativePath.replace("/", "_");
                templateCacheMap.put(relativePath,
                        new Template(templateName, new String(Files.readAllBytes(ftlFile.toPath())), CONFIGURATION));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Template getFreeMarkerTemplate(String templatePath) {
        String newTemplatePath = templatePath.replace("\\", "/").replaceAll("^/(.*)$","$1");
        return templateCacheMap.get(newTemplatePath);
    }

}

package com.iisi.changeform;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.iisi.changeform.model.yml.global.GlobalYmlParseResult;
import com.iisi.changeform.model.yml.local.LocalYmlParseResult;
import com.iisi.util.MapUtil;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class ChangeFormYmlParser {
    private YAMLMapper yamlMapper = new YAMLMapper();

    private ChangeFormYmlParser() {
    }

    private static class Nested {
        private static final ChangeFormYmlParser CHANGE_FORM_YML_PARSER = new ChangeFormYmlParser();
    }

    public static ChangeFormYmlParser getInstance() {
        return ChangeFormYmlParser.Nested.CHANGE_FORM_YML_PARSER;
    }

    GlobalYmlParseResult parseGlobalYml(File ymlFile) throws IOException {

        Map map = yamlMapper.readValue(ymlFile, Map.class);
        String signatureImgPath = MapUtil.getMapValueByPath(map, "form.image.signature.path");

        File signatureImgDir = new File(signatureImgPath);
        if (!signatureImgDir.exists()) {
            System.out.println(String.format("[Warn] programmerImgDir:%s not exist", signatureImgDir.getAbsolutePath()));
        }

        String citiProjectRelativePathPrefix = MapUtil.getMapValueByPath(map, "citi.project.relativePathPrefix");

        return new GlobalYmlParseResult(signatureImgDir, citiProjectRelativePathPrefix);
    }


    LocalYmlParseResult parsLocalYml(File ymlFile) throws IOException {
        Map map = yamlMapper.readValue(ymlFile, Map.class);
        String name = MapUtil.getMapValueByPath(map, "project.name");
        String lacrNo = MapUtil.getMapValueByPath(map, "project.lacrNo");
        String systemApplication = MapUtil.getMapValueByPath(map, "project.systemApplication");
        return new LocalYmlParseResult(name, lacrNo, systemApplication);
    }


}


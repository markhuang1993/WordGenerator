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
        Map<String, String> imagePathMap = MapUtil.getMapValueByPath(map, "form.image.path");

        String programmerImgPath = imagePathMap.get("programmer");
        File programmerImgDir = new File(programmerImgPath);
        if (!programmerImgDir.exists()) {
            System.out.println(String.format("[Warn] programmerImgDir:%s not exist", programmerImgDir.getAbsolutePath()));
        }

        String supervisorImgPath = imagePathMap.get("supervisor");
        File supervisorImgDir = new File(supervisorImgPath);
        if (!supervisorImgDir.exists()) {
            System.out.println(String.format("[Warn] programmerImgDir:%s not exist", supervisorImgDir.getAbsolutePath()));
        }

        String vendorQmImgPath = imagePathMap.get("vendorQm");
        File vendorQmImgDir = new File(vendorQmImgPath);
        if (!vendorQmImgDir.exists()) {
            System.out.println(String.format("[Warn] programmerImgDir:%s not exist", vendorQmImgDir.getAbsolutePath()));
        }

        String citiProjectRelativePathPrefix = MapUtil.getMapValueByPath(map, "citi.project.relativePathPrefix");

        return new GlobalYmlParseResult(programmerImgDir, supervisorImgDir, vendorQmImgDir, citiProjectRelativePathPrefix);
    }


    LocalYmlParseResult parsLocalYml(File ymlFile) throws IOException {
        Map map = yamlMapper.readValue(ymlFile, Map.class);
        String name = MapUtil.getMapValueByPath(map, "project.name");
        String lacrNo = MapUtil.getMapValueByPath(map, "project.lacrNo");
        String systemApplication = MapUtil.getMapValueByPath(map, "project.systemApplication");
        return new LocalYmlParseResult(name, lacrNo, systemApplication);
    }


}


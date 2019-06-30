package com.iisi.parser.form;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.iisi.parser.form.model.yml.global.GlobalYmlParseResult;
import com.iisi.parser.form.model.yml.local.LocalYmlParseResult;
import com.iisi.util.MapUtil;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class FormYmlParser {
    private YAMLMapper yamlMapper = new YAMLMapper();

    private FormYmlParser() {
    }

    private static class Nested {
        private static final FormYmlParser CHANGE_FORM_YML_PARSER = new FormYmlParser();
    }

    public static FormYmlParser getInstance() {
        return FormYmlParser.Nested.CHANGE_FORM_YML_PARSER;
    }

    public GlobalYmlParseResult parseGlobalYml(File ymlFile) throws IOException {

        Map map = yamlMapper.readValue(ymlFile, Map.class);
        String signatureImgPath = MapUtil.getMapValueByPath(map, "form.image.signature.path");

        File signatureImgDir = new File(signatureImgPath);
        if (!signatureImgDir.exists()) {
            System.out.println(String.format("[Warn] programmerImgDir:%s not exist", signatureImgDir.getAbsolutePath()));
        }

        String citiProjectRelativePathPrefix = MapUtil.getMapValueByPath(map, "citi.project.relativePathPrefix");

        return new GlobalYmlParseResult(signatureImgDir, citiProjectRelativePathPrefix);
    }


    public LocalYmlParseResult parsLocalYml(File ymlFile) throws IOException {
        Map map = yamlMapper.readValue(ymlFile, Map.class);
        String name = MapUtil.getMapValueByPath(map, "project.name");
        String lacrNo = MapUtil.getMapValueByPath(map, "project.lacrNo");
        String systemApplication = MapUtil.getMapValueByPath(map, "project.systemApplication");
        String systemId = MapUtil.getMapValueByPath(map, "project.systemId");
        String warName = MapUtil.getMapValueByPath(map, "project.warName");
        String owner = MapUtil.getMapValueByPath(map, "project.owner");
        String supervisor = MapUtil.getMapValueByPath(map, "project.supervisor");
        String vendorQm = MapUtil.getMapValueByPath(map, "project.vendorQm");
        return new LocalYmlParseResult(
                name,
                lacrNo,
                systemApplication,
                systemId,
                warName,
                owner,
                supervisor,
                vendorQm
        );
    }


}


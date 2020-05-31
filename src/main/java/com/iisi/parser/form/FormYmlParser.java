package com.iisi.parser.form;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.iisi.generator.model.changeform.Action;
import com.iisi.parser.form.model.yml.global.GlobalYmlParseResult;
import com.iisi.parser.form.model.yml.local.LocalYmlParseResult;
import com.iisi.util.MapUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

    public GlobalYmlParseResult parseGlobalYml(File ymlFile, boolean isPat) throws IOException {

        Map<String, Object> map = yamlMapper.readValue(ymlFile, new TypeReference<Map<String, Object>>(){});
        String signatureImgPath = MapUtil.getMapValueByPath(map, "form.image.signature.path");

        File signatureImgDir = new File(signatureImgPath);
        if (!signatureImgDir.exists()) {
            System.out.println(String.format("[Warn] programmerImgDir:%s not exist", signatureImgDir.getAbsolutePath()));
        }

        String citiProjectRelativePathPrefix = MapUtil.getMapValueByPath(map, "citi.project.relativePathPrefix");

        List<Action> actions = getActionsFromYmlMap(map, isPat, false);

        return new GlobalYmlParseResult(signatureImgDir, citiProjectRelativePathPrefix, actions);
    }

    private List<Action> getActionsFromYmlMap(Map<String, Object> map, boolean isPat, boolean ignoreNotFound) {
        List<Action> actions = new ArrayList<>();
        try {
            String actionPath = isPat ? "form.pat.actions" : "form.uat.actions";
            Map<String, List<String>> actionMap = MapUtil.getMapValueByPath(map, actionPath);
            for (List<String> actionLines : actionMap.values()) {
                Action action = new Action();
                for (String line : actionLines) {
                    action.addLine(line);
                }
                actions.add(action);
            }
        } catch (Exception e) {
            if (e instanceof IllegalArgumentException) {
                if (!ignoreNotFound) e.printStackTrace();
            } else {
                e.printStackTrace();
            }
        }
        return actions;
    }


    public LocalYmlParseResult parsLocalYml(File ymlFile, boolean isPat) throws IOException {
        Map<String, Object> map = yamlMapper.readValue(ymlFile, new TypeReference<Map<String, Object>>(){});
        String name = MapUtil.getMapValueByPath(map, "project.name");
        String systemApplication = MapUtil.getMapValueByPath(map, "project.systemApplication");
        String systemId = MapUtil.getMapValueByPath(map, "project.systemId");
        String warName = MapUtil.getMapValueByPath(map, "project.warName");
        String contextName = MapUtil.getMapValueByPath(map, "project.contextName");
        String owner = MapUtil.getMapValueByPath(map, "project.owner");
        String supervisor = MapUtil.getMapValueByPath(map, "project.supervisor");
        String vendorQm = MapUtil.getMapValueByPath(map, "project.vendorQm");
        List<Action> actions = getActionsFromYmlMap(map, isPat, true);

        return new LocalYmlParseResult(
                name,
                systemApplication,
                systemId,
                contextName,
                warName,
                owner,
                supervisor,
                vendorQm,
                actions,
                map
        );
    }


}


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
    private final YAMLMapper yamlMapper = new YAMLMapper();

    private FormYmlParser() {
    }

    private static class Nested {
        private static final FormYmlParser CHANGE_FORM_YML_PARSER = new FormYmlParser();
    }

    public static FormYmlParser getInstance() {
        return FormYmlParser.Nested.CHANGE_FORM_YML_PARSER;
    }

    public GlobalYmlParseResult parseGlobalYml(File ymlFile) throws IOException {

        final Map<String, Object> map = yamlMapper.readValue(ymlFile, new TypeReference<Map<String, Object>>() {
        });
        final String signatureImgPath = MapUtil.getMapValueByPath(map, "form.image.signature.path");

        final File signatureImgDir = new File(signatureImgPath);
        if (!signatureImgDir.exists()) {
            System.out.println(String.format("[Warn] programmerImgDir:%s not exist", signatureImgDir.getAbsolutePath()));
        }

        final String citiProjectRelativePathPrefix = MapUtil.getMapValueByPath(map, "citi.project.relativePathPrefix");

        final List<Action> uatActions = getActionsFromYmlMap(map, "uat", false);
        final List<Action> patActions = getActionsFromYmlMap(map, "pat", false);
        final List<Action> sqlActions = getActionsFromYmlMap(map, "sql", false);

        return new GlobalYmlParseResult(signatureImgDir, citiProjectRelativePathPrefix, uatActions, patActions, sqlActions);
    }

    private List<Action> getActionsFromYmlMap(Map<String, Object> map, String type, boolean ignoreNotFound) {
        final List<Action> actions = new ArrayList<>();
        try {
            String actionPath = String.format("form.%s.actions", type);
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


    public LocalYmlParseResult parsLocalYml(File ymlFile) throws IOException {
        final Map<String, Object> map = yamlMapper.readValue(ymlFile, new TypeReference<Map<String, Object>>() {
        });
        final String name = MapUtil.getMapValueByPath(map, "project.name");
        final String systemApplication = MapUtil.getMapValueByPath(map, "project.systemApplication");
        final String systemId = MapUtil.getMapValueByPath(map, "project.systemId");
        final String warName = MapUtil.getMapValueByPath(map, "project.warName");
        final String contextName = MapUtil.getMapValueByPath(map, "project.contextName");
        final String owner = MapUtil.getMapValueByPath(map, "project.owner");
        final String supervisor = MapUtil.getMapValueByPath(map, "project.supervisor");
        final String vendorQm = MapUtil.getMapValueByPath(map, "project.vendorQm");
        final List<Action> uatActions = getActionsFromYmlMap(map, "uat", true);
        final List<Action> patActions = getActionsFromYmlMap(map, "pat", true);
        final List<Action> sqlActions = getActionsFromYmlMap(map, "sql", true);

        return new LocalYmlParseResult(
                name,
                systemApplication,
                systemId,
                contextName,
                warName,
                owner,
                supervisor,
                vendorQm,
                uatActions,
                patActions,
                sqlActions,
                map
        );
    }


}


package com.iisi.changeform;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.iisi.changeform.model.ArgumentParseResult;
import com.iisi.changeform.model.ChangeFormArgument;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class ChangeFormMain {
    public static void main(String[] args) throws IOException {
        ArgumentParseResult argumentParseResult = ChangeFormArgumentParser.getInstance().parseArguments(args);
        if (!argumentParseResult.isParseSuccess()) {
            throw new IllegalArgumentException(argumentParseResult.getErrorMessage());
        }

        ChangeFormArgument changeFormArgument = argumentParseResult.getChangeFormArgument();

        YAMLMapper yamlMapper = new YAMLMapper();
        File globalConfigYmlFile = changeFormArgument.getGlobalConfigYmlFile();
        yamlMapper.readValue(globalConfigYmlFile, Map.class);


    }
}


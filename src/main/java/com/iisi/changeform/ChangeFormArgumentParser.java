package com.iisi.changeform;

import com.iisi.changeform.model.ArgumentParseResult;
import com.iisi.changeform.model.ChangeFormArgument;

import java.io.File;

public class ChangeFormArgumentParser {
    private ChangeFormArgumentParser() {
        throw new AssertionError();
    }

    private static class Nested{
        private static final ChangeFormArgumentParser CHANGE_FORM_ARGUMENT_PARSER = new ChangeFormArgumentParser();
    }

    public static ChangeFormArgumentParser getInstance() {
        return Nested.CHANGE_FORM_ARGUMENT_PARSER;
    }

    public ArgumentParseResult parseArguments(String[] args) {
        StringBuilder sb = new StringBuilder();
        ChangeFormArgument changeFormArgument = new ChangeFormArgument();

        if (args == null) {
            return new ArgumentParseResult("Args is null!");
        }

        String diffTxtPath = args[0];
        File diffTxtFile = new File(diffTxtPath);
        if (!diffTxtFile.exists()) {
            sb.append("Diff txt file not found at:").append(diffTxtFile.getAbsolutePath()).append(System.lineSeparator());
        } else {
            changeFormArgument.setDiffTxtFile(diffTxtFile);
        }

        int len = args.length;
        if (len <= 1) {
            sb.append("Args globalConfigYmlPath not found").append(System.lineSeparator());
            return new ArgumentParseResult(sb.toString());
        }

        String globalConfigYmlPath = args[1];
        File globalConfigYmlFile = new File(globalConfigYmlPath);
        if (!globalConfigYmlFile.exists()) {
            sb.append("Global config yml file not found at:").append(diffTxtFile.getAbsolutePath()).append(System.lineSeparator());
        } else {
            changeFormArgument.setGlobalConfigYmlFile(globalConfigYmlFile);
        }


        if (len <= 2) {
            sb.append("Args localConfigYmlFile not found").append(System.lineSeparator());
            return new ArgumentParseResult(sb.toString());
        }

        String localConfigYmlPath = args[2];
        File localConfigYmlFile = new File(localConfigYmlPath);
        if (!globalConfigYmlFile.exists()) {
            sb.append("Local config yml file not found at:").append(diffTxtFile.getAbsolutePath()).append(System.lineSeparator());
        } else {
            changeFormArgument.setLocalConfigYmlFile(localConfigYmlFile);
        }

        if (len <= 3) {
            sb.append("Args jenkinsJobExecutor not found").append(System.lineSeparator());
            return new ArgumentParseResult(sb.toString());
        }

        changeFormArgument.setJenkinsJobExecutor(args[3]);
        return new ArgumentParseResult(changeFormArgument, true, sb.toString());
    }
}

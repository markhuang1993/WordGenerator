package com.iisi.parser.form;

import com.iisi.parser.form.model.argument.ArgumentParseResult;
import com.iisi.parser.form.model.argument.FormArgument;

import java.io.File;

public class FormArgumentParser {
    private FormArgumentParser() {
    }

    private static class Nested {
        private static final FormArgumentParser CHANGE_FORM_ARGUMENT_PARSER = new FormArgumentParser();
    }

    public static FormArgumentParser getInstance() {
        return Nested.CHANGE_FORM_ARGUMENT_PARSER;
    }

    public ArgumentParseResult parseArguments(String[] args) {
        StringBuilder sb = new StringBuilder();
        FormArgument formArgument = new FormArgument();

        if (args == null) {
            return new ArgumentParseResult("Args is null!");
        }

        int len = args.length;
        if (len <= 0) {
            sb.append("Args diffTxtPath not found").append(System.lineSeparator());
            return new ArgumentParseResult(sb.toString());
        }

        String diffTxtPath = args[0];
        File diffTxtFile = new File(diffTxtPath);
        if (!diffTxtFile.exists()) {
            sb.append("Diff txt file not found at:").append(diffTxtFile.getAbsolutePath()).append(System.lineSeparator());
        } else {
            formArgument.setDiffTxtFile(diffTxtFile);
        }

        if (len <= 1) {
            sb.append("Args globalConfigYmlPath not found").append(System.lineSeparator());
            return new ArgumentParseResult(sb.toString());
        }

        String globalConfigYmlPath = args[1];
        File globalConfigYmlFile = new File(globalConfigYmlPath);
        if (!globalConfigYmlFile.exists()) {
            sb.append("Global config yml file not found at:").append(diffTxtFile.getAbsolutePath()).append(System.lineSeparator());
        } else {
            formArgument.setGlobalConfigYmlFile(globalConfigYmlFile);
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
            formArgument.setLocalConfigYmlFile(localConfigYmlFile);
        }

        if (len <= 3) {
            sb.append("Args jenkinsJobExecutor not found").append(System.lineSeparator());
            return new ArgumentParseResult(sb.toString());
        }

        formArgument.setJenkinsJobExecutor(args[3]);

        if (len <= 4) {
            sb.append("Args dest dircectory not found").append(System.lineSeparator());
            return new ArgumentParseResult(sb.toString());
        }
        File destDir = new File(args[4]);
        destDir.mkdirs();
        formArgument.setDestDir(destDir);

        return new ArgumentParseResult(formArgument, true, sb.toString());
    }
}

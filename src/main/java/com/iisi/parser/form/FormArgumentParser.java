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
        final StringBuilder sb = new StringBuilder();
        final FormArgument formArgument = new FormArgument();

        if (args == null) {
            return new ArgumentParseResult("Args is null!");
        }

        final int len = args.length;
        if (len == 0) {
            sb.append("Args diffTxtPath not found").append(System.lineSeparator());
            return new ArgumentParseResult(sb.toString());
        }

        final String diffTxtPath = args[0];
        final File diffTxtFile = new File(diffTxtPath);
        if (!diffTxtFile.exists()) {
            sb.append("Diff txt file not found at:").append(diffTxtFile.getAbsolutePath()).append(System.lineSeparator());
        } else {
            formArgument.setDiffTxtFile(diffTxtFile);
        }

        if (len == 1) {
            sb.append("Args globalConfigYmlPath not found").append(System.lineSeparator());
            return new ArgumentParseResult(sb.toString());
        }

        final String globalConfigYmlPath = args[1];
        final File globalConfigYmlFile = new File(globalConfigYmlPath);
        if (!globalConfigYmlFile.exists()) {
            sb.append("Global config yml file not found at:").append(diffTxtFile.getAbsolutePath()).append(System.lineSeparator());
        } else {
            formArgument.setGlobalConfigYmlFile(globalConfigYmlFile);
        }


        if (len == 2) {
            sb.append("Args localConfigYmlFile not found").append(System.lineSeparator());
            return new ArgumentParseResult(sb.toString());
        }

        final String localConfigYmlPath = args[2];
        final File localConfigYmlFile = new File(localConfigYmlPath);
        if (!globalConfigYmlFile.exists()) {
            sb.append("Local config yml file not found at:").append(diffTxtFile.getAbsolutePath()).append(System.lineSeparator());
        } else {
            formArgument.setLocalConfigYmlFile(localConfigYmlFile);
        }

        if (len == 3) {
            sb.append("Args jenkinsJobExecutor not found").append(System.lineSeparator());
            return new ArgumentParseResult(sb.toString());
        }

        formArgument.setJenkinsJobExecutor(args[3]);

        if (len == 4) {
            sb.append("Args dest directory not found").append(System.lineSeparator());
            return new ArgumentParseResult(sb.toString());
        }
        final File destDir = new File(args[4]);

        if (!destDir.exists() && !destDir.mkdirs()) {
            throw new RuntimeException("can't mkdirs:" + destDir);
        }

        formArgument.setDestDir(destDir);

        return new ArgumentParseResult(formArgument, true, sb.toString());
    }
}

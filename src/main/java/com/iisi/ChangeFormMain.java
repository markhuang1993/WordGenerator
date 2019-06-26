package com.iisi;

import java.io.File;

public class ChangeFormMain {
    public static void main(String[] args) {
        ParseResult parseResult = parseArguments(args);
        if (!parseResult.isParseSuccess) {
            throw new IllegalArgumentException(parseResult.errorMessage);
        }

        ChangeFormArgument changeFormArgument = parseResult.changeFormArgument;






    }


    private static ParseResult parseArguments(String[] args) {
        StringBuilder sb = new StringBuilder();
        ChangeFormArgument changeFormArgument = new ChangeFormArgument();

        if (args == null) {
            return new ParseResult("Args is null!");
        }

        String diffTxtPath = args[0];
        File diffTxtFile = new File(diffTxtPath);
        if (!diffTxtFile.exists()) {
            sb.append("Diff txt file not found at:").append(diffTxtFile.getAbsolutePath()).append(System.lineSeparator());
        } else {
            changeFormArgument.diffTxtFile = diffTxtFile;
        }

        int len = args.length;
        if (len <= 1) {
            sb.append("Args globalConfigYmlPath not found").append(System.lineSeparator());
            return new ParseResult(sb.toString());
        }

        String globalConfigYmlPath = args[1];
        File globalConfigYmlFile = new File(globalConfigYmlPath);
        if (!globalConfigYmlFile.exists()) {
            sb.append("Global config yml file not found at:").append(diffTxtFile.getAbsolutePath()).append(System.lineSeparator());
        } else {
            changeFormArgument.globalConfigYmlFile = globalConfigYmlFile;
        }


        if (len <= 2) {
            sb.append("Args localConfigYmlFile not found").append(System.lineSeparator());
            return new ParseResult(sb.toString());
        }

        String localConfigYmlPath = args[2];
        File localConfigYmlFile = new File(localConfigYmlPath);
        if (!globalConfigYmlFile.exists()) {
            sb.append("Local config yml file not found at:").append(diffTxtFile.getAbsolutePath()).append(System.lineSeparator());
        } else {
            changeFormArgument.localConfigYmlFile = localConfigYmlFile;
        }

        if (len <= 3) {
            sb.append("Args jenkinsJobExecutor not found").append(System.lineSeparator());
            return new ParseResult(sb.toString());
        }

        changeFormArgument.jenkinsJobExecutor = args[3];
        return new ParseResult(changeFormArgument, true, sb.toString());
    }

    private static class ParseResult {
        private ChangeFormArgument changeFormArgument;
        private boolean isParseSuccess;
        private String errorMessage;

        private ParseResult(String errorMessage) {
            this.isParseSuccess = false;
            this.errorMessage = errorMessage;
        }

        private ParseResult(ChangeFormArgument changeFormArgument, boolean isParseSuccess, String errorMessage) {
            this.errorMessage = errorMessage;
            this.isParseSuccess = isParseSuccess;
            this.changeFormArgument = changeFormArgument;
        }
    }

    private static class ChangeFormArgument {
        private File diffTxtFile;
        private File globalConfigYmlFile;
        private File localConfigYmlFile;
        private String jenkinsJobExecutor;
    }
}

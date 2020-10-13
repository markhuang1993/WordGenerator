package com.iisi.parser.form.model.argument;

public class ArgumentParseResult {
    private FormArgument formArgument;
    private final boolean isParseSuccess;
    private final String errorMessage;

    public ArgumentParseResult(String errorMessage) {
        this.isParseSuccess = false;
        this.errorMessage = errorMessage;
    }

    public ArgumentParseResult(FormArgument formArgument, boolean isParseSuccess, String errorMessage) {
        this.errorMessage = errorMessage;
        this.isParseSuccess = isParseSuccess;
        this.formArgument = formArgument;
    }

    public FormArgument getFormArgument() {
        return formArgument;
    }

    public boolean isParseSuccess() {
        return isParseSuccess;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}

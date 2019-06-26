package com.iisi.changeform.model;

public class ArgumentParseResult {
    private ChangeFormArgument changeFormArgument;
    private boolean isParseSuccess;
    private String errorMessage;

    public ArgumentParseResult(String errorMessage) {
        this.isParseSuccess = false;
        this.errorMessage = errorMessage;
    }

    public ArgumentParseResult(ChangeFormArgument changeFormArgument, boolean isParseSuccess, String errorMessage) {
        this.errorMessage = errorMessage;
        this.isParseSuccess = isParseSuccess;
        this.changeFormArgument = changeFormArgument;
    }

    public ChangeFormArgument getChangeFormArgument() {
        return changeFormArgument;
    }

    public boolean isParseSuccess() {
        return isParseSuccess;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}

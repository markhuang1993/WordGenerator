package com.iisi.constants;

public enum  CheckboxString {
    CHECKED("&#9632;"), UNCHECKED("&#9633;");

    private String checkboxStr;

    CheckboxString(String checkboxStr){
        this.checkboxStr = checkboxStr;
    }

    public String val() {
        return checkboxStr;
    }
}

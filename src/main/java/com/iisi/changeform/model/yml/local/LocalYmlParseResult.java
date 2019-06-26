package com.iisi.changeform.model.yml.local;

import java.io.File;

public  class LocalYmlParseResult {
    private String name;
    private String lacrNo;
    private String systemApplication;

    public LocalYmlParseResult(String name, String lacrNo, String systemApplication) {
        this.name = name;
        this.lacrNo = lacrNo;
        this.systemApplication = systemApplication;
    }

    public String getName() {
        return name;
    }

    public String getLacrNo() {
        return lacrNo;
    }

    public String getSystemApplication() {
        return systemApplication;
    }

    @Override
    public String toString() {
        return "LocalYmlParseResult{" +
                "name='" + name + '\'' +
                ", lacrNo='" + lacrNo + '\'' +
                ", systemApplication='" + systemApplication + '\'' +
                '}';
    }
}

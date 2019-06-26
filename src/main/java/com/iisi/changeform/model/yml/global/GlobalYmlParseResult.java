package com.iisi.changeform.model.yml.global;

import java.io.File;

public  class GlobalYmlParseResult {
    private File programmerImageDir;
    private File supervisorImageDir;
    private File vendorQmImageDir;
    private String citiProjectRelativePathPrefix;

    public GlobalYmlParseResult(File programmerImageDir, File supervisorImageDir, File vendorQmImageDir, String citiProjectRelativePathPrefix) {
        this.programmerImageDir = programmerImageDir;
        this.supervisorImageDir = supervisorImageDir;
        this.vendorQmImageDir = vendorQmImageDir;
        this.citiProjectRelativePathPrefix = citiProjectRelativePathPrefix;
    }

    public File getProgrammerImageDir() {
        return programmerImageDir;
    }

    public File getSupervisorImageDir() {
        return supervisorImageDir;
    }

    public File getVendorQmImageDir() {
        return vendorQmImageDir;
    }

    public String getCitiProjectRelativePathPrefix() {
        return citiProjectRelativePathPrefix;
    }

    @Override
    public String toString() {
        return "GlobalYmlParseResult{" +
                "programmerImageDir=" + programmerImageDir +
                ", supervisorImageDir=" + supervisorImageDir +
                ", vendorQmImageDir=" + vendorQmImageDir +
                ", citiProjectRelativePathPrefix='" + citiProjectRelativePathPrefix + '\'' +
                '}';
    }
}

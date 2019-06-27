package com.iisi.changeform.model.yml.local;

public  class LocalYmlParseResult {
    private String name;
    private String lacrNo;
    private String systemApplication;
    private String systemId;
    private String warName;

    public LocalYmlParseResult(String name, String lacrNo, String systemApplication, String systemId, String warName) {
        this.name = name;
        this.lacrNo = lacrNo;
        this.systemApplication = systemApplication;
        this.systemId = systemId;
        this.warName = warName;
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

    public String getSystemId() {
        return systemId;
    }

    public String getWarName() {
        return warName;
    }

    @Override
    public String toString() {
        return "LocalYmlParseResult{" +
                "name='" + name + '\'' +
                ", lacrNo='" + lacrNo + '\'' +
                ", systemApplication='" + systemApplication + '\'' +
                ", systemId='" + systemId + '\'' +
                ", warName='" + warName + '\'' +
                '}';
    }
}

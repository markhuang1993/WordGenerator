package com.iisi.changeform.model.yml.local;

public  class LocalYmlParseResult {
    private String projectName;
    private String lacrNo;
    private String systemApplication;
    private String systemId;
    private String warName;

    public LocalYmlParseResult(String projectName, String lacrNo, String systemApplication, String systemId, String warName) {
        this.projectName = projectName;
        this.lacrNo = lacrNo;
        this.systemApplication = systemApplication;
        this.systemId = systemId;
        this.warName = warName;
    }

    public String getProjectName() {
        return projectName;
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
                "projectName='" + projectName + '\'' +
                ", lacrNo='" + lacrNo + '\'' +
                ", systemApplication='" + systemApplication + '\'' +
                ", systemId='" + systemId + '\'' +
                ", warName='" + warName + '\'' +
                '}';
    }
}

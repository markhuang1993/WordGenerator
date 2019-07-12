package com.iisi.parser.form.model.yml.local;

public  class LocalYmlParseResult {
    private String projectName;
    private String systemApplication;
    private String systemId;
    private String warName;
    private String owner;
    private String supervisor;
    private String vendorQm;

    public LocalYmlParseResult(String projectName, String systemApplication, String systemId, String warName, String owner, String supervisor, String vendorQm) {
        this.projectName = projectName;
        this.systemApplication = systemApplication;
        this.systemId = systemId;
        this.warName = warName;
        this.owner = owner;
        this.supervisor = supervisor;
        this.vendorQm = vendorQm;
    }

    public String getProjectName() {
        return projectName;
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

    public String getOwner() {
        return owner;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public String getvendorQm() {
        return vendorQm;
    }

    @Override
    public String toString() {
        return "LocalYmlParseResult{" +
                "projectName='" + projectName + '\'' +
                ", systemApplication='" + systemApplication + '\'' +
                ", systemId='" + systemId + '\'' +
                ", warName='" + warName + '\'' +
                ", owner='" + owner + '\'' +
                ", supervisor='" + supervisor + '\'' +
                ", vendorQm='" + vendorQm + '\'' +
                '}';
    }
}

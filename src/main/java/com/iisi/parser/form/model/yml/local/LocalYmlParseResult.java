package com.iisi.parser.form.model.yml.local;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public  class LocalYmlParseResult {
    private String projectName;
    private String systemApplication;
    private String systemId;
    private String contextName;
    private String warName;
    private String owner;
    private String supervisor;
    private String vendorQm;
    private Map<String, Object> originMap;

    public LocalYmlParseResult(String projectName, String systemApplication, String systemId, String contextName, String warName, String owner, String supervisor, String vendorQm,Map<String, Object> originMap) {
        this.projectName = projectName;
        this.systemApplication = systemApplication;
        this.systemId = systemId;
        this.contextName = contextName;
        this.warName = warName;
        this.owner = owner;
        this.supervisor = supervisor;
        this.vendorQm = vendorQm;
        this.originMap = originMap;
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

    public Map getOriginMap() {
        return new LinkedHashMap<>(originMap);
    }

    public void setOriginMap(Map<String, Object> originMap) {
        this.originMap = originMap;
    }

    public void setContextName(String contextName) {
        this.contextName = contextName;
    }

    public String getContextName() {
        return contextName;
    }

    @Override
    public String toString() {
        return "LocalYmlParseResult{" +
                "projectName='" + projectName + '\'' +
                ", systemApplication='" + systemApplication + '\'' +
                ", systemId='" + systemId + '\'' +
                ", contextName='" + contextName + '\'' +
                ", warName='" + warName + '\'' +
                ", owner='" + owner + '\'' +
                ", supervisor='" + supervisor + '\'' +
                ", vendorQm='" + vendorQm + '\'' +
                '}';
    }
}

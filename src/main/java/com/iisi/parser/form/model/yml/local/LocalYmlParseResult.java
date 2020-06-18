package com.iisi.parser.form.model.yml.local;

import com.iisi.generator.model.changeform.Action;

import java.util.*;

public class LocalYmlParseResult {
    private String projectName;
    private String systemApplication;
    private String systemId;
    private String contextName;
    private String warName;
    private String owner;
    private String supervisor;
    private String vendorQm;
    private List<Action> uatActions;
    private List<Action> patActions;
    private List<Action> sqlActions;
    private Map<String, Object> originMap;

    public LocalYmlParseResult(final String projectName, final String systemApplication, final String systemId,
                               final String contextName, final String warName, final String owner, final String supervisor,
                               final String vendorQm, final List<Action> uatActions, final List<Action> patActions,
                               final List<Action> sqlActions, final Map<String, Object> originMap) {
        this.projectName = projectName;
        this.systemApplication = systemApplication;
        this.systemId = systemId;
        this.contextName = contextName;
        this.warName = warName;
        this.owner = owner;
        this.supervisor = supervisor;
        this.vendorQm = vendorQm;
        this.uatActions = uatActions;
        this.patActions = patActions;
        this.sqlActions = sqlActions;
        this.originMap = originMap;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(final String projectName) {
        this.projectName = projectName;
    }

    public String getSystemApplication() {
        return systemApplication;
    }

    public void setSystemApplication(final String systemApplication) {
        this.systemApplication = systemApplication;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(final String systemId) {
        this.systemId = systemId;
    }

    public String getContextName() {
        return contextName;
    }

    public void setContextName(final String contextName) {
        this.contextName = contextName;
    }

    public String getWarName() {
        return warName;
    }

    public void setWarName(final String warName) {
        this.warName = warName;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(final String owner) {
        this.owner = owner;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(final String supervisor) {
        this.supervisor = supervisor;
    }

    public String getVendorQm() {
        return vendorQm;
    }

    public void setVendorQm(final String vendorQm) {
        this.vendorQm = vendorQm;
    }

    public List<Action> getUatActions() {
        return uatActions;
    }

    public void setUatActions(final List<Action> uatActions) {
        this.uatActions = uatActions;
    }

    public List<Action> getPatActions() {
        return patActions;
    }

    public void setPatActions(final List<Action> patActions) {
        this.patActions = patActions;
    }

    public List<Action> getSqlActions() {
        return sqlActions;
    }

    public void setSqlActions(final List<Action> sqlActions) {
        this.sqlActions = sqlActions;
    }

    public Map<String, Object> getOriginMap() {
        return originMap;
    }

    public void setOriginMap(final Map<String, Object> originMap) {
        this.originMap = originMap;
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
                ", uatActions=" + uatActions +
                ", patActions=" + patActions +
                ", sqlActions=" + sqlActions +
                ", originMap=" + originMap +
                '}';
    }
}

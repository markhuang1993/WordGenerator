package com.iisi.parser.form.model.yml.global;

import com.iisi.generator.model.changeform.Action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GlobalYmlParseResult {
    private File signatureImgDir;
    private String citiProjectRelativePathPrefix;
    private List<Action> uatActions;
    private List<Action> patActions;
    private List<Action> sqlActions;

    public GlobalYmlParseResult(final File signatureImgDir, final String citiProjectRelativePathPrefix,
                                final List<Action> uatActions, final List<Action> patActions, final List<Action> sqlActions) {
        this.signatureImgDir = signatureImgDir;
        this.citiProjectRelativePathPrefix = citiProjectRelativePathPrefix;
        this.uatActions = uatActions;
        this.patActions = patActions;
        this.sqlActions = sqlActions;
    }

    public File getSignatureImgDir() {
        return signatureImgDir;
    }

    public void setSignatureImgDir(File signatureImgDir) {
        this.signatureImgDir = signatureImgDir;
    }

    public String getCitiProjectRelativePathPrefix() {
        return citiProjectRelativePathPrefix;
    }

    public void setCitiProjectRelativePathPrefix(String citiProjectRelativePathPrefix) {
        this.citiProjectRelativePathPrefix = citiProjectRelativePathPrefix;
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

    @Override
    public String toString() {
        return "GlobalYmlParseResult{" +
                "signatureImgDir=" + signatureImgDir +
                ", citiProjectRelativePathPrefix='" + citiProjectRelativePathPrefix + '\'' +
                ", uatActions=" + uatActions +
                ", patActions=" + patActions +
                ", sqlActions=" + sqlActions +
                '}';
    }
}

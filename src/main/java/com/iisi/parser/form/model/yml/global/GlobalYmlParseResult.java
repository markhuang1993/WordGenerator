package com.iisi.parser.form.model.yml.global;

import com.iisi.generator.model.changeform.Action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GlobalYmlParseResult {
    private File signatureImgDir;
    private String citiProjectRelativePathPrefix;
    private List<Action> actions;

    public GlobalYmlParseResult(File signatureImgDir, String citiProjectRelativePathPrefix, List<Action> actions) {
        this.signatureImgDir = signatureImgDir;
        this.citiProjectRelativePathPrefix = citiProjectRelativePathPrefix;
        this.actions = new ArrayList<>(actions);
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

    public void setActions(List<Action> actions) {
        this.actions = new ArrayList<>(actions);
    }

    public List<Action> getActions() {
        return actions;
    }

    @Override
    public String toString() {
        return "GlobalYmlParseResult{" +
                "signatureImgDir=" + signatureImgDir +
                ", citiProjectRelativePathPrefix='" + citiProjectRelativePathPrefix + '\'' +
                '}';
    }
}

package com.iisi.changeform.model.yml.global;

import java.io.File;

public  class GlobalYmlParseResult {
    private File signatureImgDir;
    private String citiProjectRelativePathPrefix;

    public GlobalYmlParseResult(File signatureImgDir, String citiProjectRelativePathPrefix) {
        this.signatureImgDir = signatureImgDir;
        this.citiProjectRelativePathPrefix = citiProjectRelativePathPrefix;
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

    @Override
    public String toString() {
        return "GlobalYmlParseResult{" +
                "signatureImgDir=" + signatureImgDir +
                ", citiProjectRelativePathPrefix='" + citiProjectRelativePathPrefix + '\'' +
                '}';
    }
}

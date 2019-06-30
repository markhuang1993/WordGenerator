package com.iisi.parser.form.model.argument;

import java.io.File;

public class FormArgument {
    private File diffTxtFile;
    private File globalConfigYmlFile;
    private File localConfigYmlFile;
    private File destDir;
    private String jenkinsJobExecutor;

    public File getDiffTxtFile() {
        return diffTxtFile;
    }

    public void setDiffTxtFile(File diffTxtFile) {
        this.diffTxtFile = diffTxtFile;
    }

    public File getGlobalConfigYmlFile() {
        return globalConfigYmlFile;
    }

    public void setGlobalConfigYmlFile(File globalConfigYmlFile) {
        this.globalConfigYmlFile = globalConfigYmlFile;
    }

    public File getLocalConfigYmlFile() {
        return localConfigYmlFile;
    }

    public void setLocalConfigYmlFile(File localConfigYmlFile) {
        this.localConfigYmlFile = localConfigYmlFile;
    }

    public String getJenkinsJobExecutor() {
        return jenkinsJobExecutor;
    }

    public void setJenkinsJobExecutor(String jenkinsJobExecutor) {
        this.jenkinsJobExecutor = jenkinsJobExecutor;
    }

    public void setDestDir(File destDir) {
        this.destDir = destDir;
    }

    public File getDestDir() {
        return destDir;
    }
}

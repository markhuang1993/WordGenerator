package com.iisi.changeform.model.argument;

import java.io.File;

public class ChangeFormArgument {
    private File diffTxtFile;
    private File globalConfigYmlFile;
    private File localConfigYmlFile;
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
}

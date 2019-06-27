package com.iisi.parser;

import com.iisi.constants.DiffStatus;

public class DiffDetail {
    private DiffStatus status;
    private String filePath;

    public DiffStatus getStatus() {
        return status;
    }

    public void setStatus(DiffStatus status) {
        this.status = status;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}

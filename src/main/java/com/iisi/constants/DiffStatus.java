package com.iisi.constants;

public enum DiffStatus {
    A("N"), M("O"), R("R"), D("D"), UNKNOWN("?");

    private String fileStat;

    DiffStatus(String fileStat) {
        this.fileStat = fileStat;
    }

    public String getFileStat() {
        return fileStat;
    }
}

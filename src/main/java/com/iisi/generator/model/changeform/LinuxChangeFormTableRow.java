package com.iisi.generator.model.changeform;

public class LinuxChangeFormTableRow extends ChangeFormTableRow {
    private String no;
    private String newOld;
    private String checkIn;
    private String programFileName;
    private String programDescription;
    private String fromDir;
    private String toDir;

    public LinuxChangeFormTableRow() {
    }

    public LinuxChangeFormTableRow(final String no, final String newOld, final String checkIn, final String programFileName, final String programDescription, final String fromDir, final String toDir) {
        this.no = no;
        this.newOld = newOld;
        this.checkIn = checkIn;
        this.programFileName = programFileName;
        this.programDescription = programDescription;
        this.fromDir = fromDir;
        this.toDir = toDir;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getNewOld() {
        return newOld;
    }

    public void setNewOld(String newOld) {
        this.newOld = newOld;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getProgramFileName() {
        return programFileName;
    }

    public void setProgramFileName(String programFileName) {
        this.programFileName = programFileName;
    }

    public String getProgramDescription() {
        return programDescription;
    }

    public void setProgramDescription(String programDescription) {
        this.programDescription = programDescription;
    }

    public String getFromDir() {
        return fromDir;
    }

    public void setFromDir(final String fromDir) {
        this.fromDir = fromDir;
    }

    public String getToDir() {
        return toDir;
    }

    public void setToDir(final String toDir) {
        this.toDir = toDir;
    }
}

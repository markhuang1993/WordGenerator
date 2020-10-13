package com.iisi.generator.model.changeform;

public class WindowsChangeFormTableRow extends ChangeFormTableRow {
    private String no;
    private String fileStat;
    private String systemID;
    private String checkIn;
    private String programFileName;
    private String programExecutionName;
    private String programDescription;

    public WindowsChangeFormTableRow() {
    }

    public WindowsChangeFormTableRow(String no, String fileStat, String systemID, String checkIn, String programFileName, String programExecutionName, String programDescription) {
        this.no = no;
        this.fileStat = fileStat;
        this.systemID = systemID;
        this.checkIn = checkIn;
        this.programFileName = programFileName;
        this.programExecutionName = programExecutionName;
        this.programDescription = programDescription;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getFileStat() {
        return fileStat;
    }

    public void setFileStat(String fileStat) {
        this.fileStat = fileStat;
    }

    public String getSystemID() {
        return systemID;
    }

    public void setSystemID(String systemID) {
        this.systemID = systemID;
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

    public String getProgramExecutionName() {
        return programExecutionName;
    }

    public void setProgramExecutionName(String programExecutionName) {
        this.programExecutionName = programExecutionName;
    }

    public String getProgramDescription() {
        return programDescription;
    }

    public void setProgramDescription(String programDescription) {
        this.programDescription = programDescription;
    }
}

package com.iisi.generator.model.changeform;

public class WindowsChangeFormTableRow extends ChangeFormTableRow {
    private String no;
    private String newOld;
    private String systemID;
    private String checkIn;
    private String programFileName;
    private String programExecutionName;
    private String programDescription;

    public WindowsChangeFormTableRow() {
    }

    public WindowsChangeFormTableRow(String no, String newOld, String systemID, String checkIn, String programFileName, String programExecutionName, String programDescription) {
        this.no = no;
        this.newOld = newOld;
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

    public String getNewOld() {
        return newOld;
    }

    public void setNewOld(String newOld) {
        this.newOld = newOld;
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

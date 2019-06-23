package com.iisi.generator;

public class TableColumn {
    private String no;
    private String systemID;
    private String programFileName;
    private String programExecutionName;
    private String ProgramDescription;

    public TableColumn(String no, String systemID, String programFileName, String programExecutionName, String programDescription) {
        this.no = no;
        this.systemID = systemID;
        this.programFileName = programFileName;
        this.programExecutionName = programExecutionName;
        ProgramDescription = programDescription;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getSystemID() {
        return systemID;
    }

    public void setSystemID(String systemID) {
        this.systemID = systemID;
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
        return ProgramDescription;
    }

    public void setProgramDescription(String programDescription) {
        ProgramDescription = programDescription;
    }
}

package com.iisi.generator.model.checkoutform;

import com.iisi.generator.model.FormTableRow;

public class CheckoutFormTableRow implements FormTableRow {
    private String no;
    private String systemID;
    private String programFileName;
    private String programExecutionName;
    private String programDescription;

    public CheckoutFormTableRow() {
    }

    public CheckoutFormTableRow(String no, String systemID, String programFileName, String programExecutionName, String programDescription) {
        this.no = no;
        this.systemID = systemID;
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
        return programDescription;
    }

    public void setProgramDescription(String programDescription) {
        this.programDescription = programDescription;
    }
}

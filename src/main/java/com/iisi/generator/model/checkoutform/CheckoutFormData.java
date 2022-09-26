package com.iisi.generator.model.checkoutform;

import com.iisi.generator.model.FormData;

import java.io.File;

public class CheckoutFormData implements FormData {
    private String lacrNo;
    private String systemApplication;
    private String submitDate;
    private String lacrCoordinator;
    private String librarian;
    private String processDate;
    private File programmerB64Png;
    private File supervisorB64Png;
    private CheckoutFormTable javaAppTable;

    public String getLacrNo() {
        return lacrNo;
    }

    public void setLacrNo(String lacrNo) {
        this.lacrNo = lacrNo;
    }

    public String getSystemApplication() {
        return systemApplication;
    }

    public void setSystemApplication(String systemApplication) {
        this.systemApplication = systemApplication;
    }

    public String getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(String submitDate) {
        this.submitDate = submitDate;
    }

    public String getLacrCoordinator() {
        return lacrCoordinator;
    }

    public void setLacrCoordinator(String lacrCoordinator) {
        this.lacrCoordinator = lacrCoordinator;
    }

    public String getLibrarian() {
        return librarian;
    }

    public void setLibrarian(String librarian) {
        this.librarian = librarian;
    }

    public String getProcessDate() {
        return processDate;
    }

    public void setProcessDate(String processDate) {
        this.processDate = processDate;
    }

    public File getProgrammerB64Png() {
        return programmerB64Png;
    }

    public void setProgrammerB64Png(File programmerB64Png) {
        this.programmerB64Png = programmerB64Png;
    }

    public File getSupervisorB64Png() {
        return supervisorB64Png;
    }

    public void setSupervisorB64Png(File supervisorB64Png) {
        this.supervisorB64Png = supervisorB64Png;
    }

    public CheckoutFormTable getJavaAppTable() {
        return javaAppTable;
    }

    public void setJavaAppTable(CheckoutFormTable javaAppTable) {
        this.javaAppTable = javaAppTable;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Builder() {
        }

        private final CheckoutFormData checkoutFormData = new CheckoutFormData();

        public Builder setLacrNo(String lacrNo) {
            this.checkoutFormData.lacrNo = lacrNo;
            return this;
        }

        public Builder setSystemApplication(String systemApplication) {
            this.checkoutFormData.systemApplication = systemApplication;
            return this;
        }

        public Builder setSubmitDate(String submitDate) {
            this.checkoutFormData.submitDate = submitDate;
            return this;
        }

        public Builder setLacrCoordinator(String lacrCoordinator) {
            this.checkoutFormData.lacrCoordinator = lacrCoordinator;
            return this;
        }

        public Builder setLibrarian(String librarian) {
            this.checkoutFormData.librarian = librarian;
            return this;
        }

        public Builder setProcessDate(String processDate) {
            this.checkoutFormData.processDate = processDate;
            return this;
        }

        public Builder setProgrammerB64Png(File programmerB64Png) {
            this.checkoutFormData.programmerB64Png = programmerB64Png;
            return this;
        }

        public Builder setSupervisorB64Png(File supervisorB64Png) {
            this.checkoutFormData.supervisorB64Png = supervisorB64Png;
            return this;
        }

        public Builder setJavaAppTable(CheckoutFormTable javaAppTable) {
            this.checkoutFormData.javaAppTable = javaAppTable;
            return this;
        }

        public CheckoutFormData build(){
            return this.checkoutFormData;
        }
    }
}

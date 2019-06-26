package com.iisi.generator.model.changeform;

import com.iisi.generator.model.FormData;
import com.iisi.generator.model.checkoutform.CheckoutFormTable;

import java.io.File;

public class ChangeFormData implements FormData {
    private String promoteToUat;
    private String promoteToProduction;
    private String lacrNo;
    private String systemApplication;
    private String submitDate;
    private String lacrCoordinator;
    private String librarian;
    private String processDate;
    private String action1;
    private String action2;
    private String action3;
    private File programmerB64Png;
    private File supervisorB64Png;
    private File vendorQmB64Png;
    private ChangeFormTable javaAppTable;

    public String getPromoteToUat() {
        return promoteToUat;
    }

    public void setPromoteToUat(String promoteToUat) {
        this.promoteToUat = promoteToUat;
    }

    public String getPromoteToProduction() {
        return promoteToProduction;
    }

    public void setPromoteToProduction(String promoteToProduction) {
        this.promoteToProduction = promoteToProduction;
    }

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

    public String getAction1() {
        return action1;
    }

    public void setAction1(String action1) {
        this.action1 = action1;
    }

    public String getAction2() {
        return action2;
    }

    public void setAction2(String action2) {
        this.action2 = action2;
    }

    public String getAction3() {
        return action3;
    }

    public void setAction3(String action3) {
        this.action3 = action3;
    }

    public File getVendorQmB64Png() {
        return vendorQmB64Png;
    }

    public void setVendorQmB64Png(File vendorQmB64Png) {
        this.vendorQmB64Png = vendorQmB64Png;
    }

    public void setJavaAppTable(ChangeFormTable javaAppTable) {
        this.javaAppTable = javaAppTable;
    }

    public ChangeFormTable getJavaAppTable() {
        return javaAppTable;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Builder() {
        }

        private ChangeFormData checkoutFormData = new ChangeFormData();

        public Builder setPromoteToUat(String promoteToUat) {
            this.checkoutFormData.promoteToUat = promoteToUat;
            return this;
        }

        public Builder setPromoteToProduction(String promoteToProduction) {
            this.checkoutFormData.promoteToProduction = promoteToProduction;
            return this;
        }

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

        public Builder setAction1(String action1) {
            this.checkoutFormData.action1 = action1;
            return this;
        }

        public Builder setAction2(String action2) {
            this.checkoutFormData.action2 = action2;
            return this;
        }

        public Builder setAction3(String action3) {
            this.checkoutFormData.action3 = action3;
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

        public Builder setVendorQmB64Png(File vendorQmB64Png) {
            this.checkoutFormData.vendorQmB64Png = vendorQmB64Png;
            return this;
        }

        public Builder setJavaAppTable(ChangeFormTable javaAppTable) {
            this.checkoutFormData.javaAppTable = javaAppTable;
            return this;
        }

        public ChangeFormData build() {
            return this.checkoutFormData;
        }
    }
}

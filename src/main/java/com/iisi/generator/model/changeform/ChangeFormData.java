package com.iisi.generator.model.changeform;

import com.iisi.generator.model.FormData;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ChangeFormData implements FormData {
    private boolean isPat;
    private String lacrNo;
    private String systemApplication;
    private String submitDate;
    private String lacrCoordinator;
    private String librarian;
    private String processDate;
    private List<Action> actions = new ArrayList<>();
    private File programmerB64Png;
    private File supervisorB64Png;
    private File vendorQmB64Png;
    private ChangeFormTable windowsJavaAppTable;
    private ChangeFormTable linuxJavaAppTable;

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

    public List<Action> getActions() {
        return new ArrayList<>(actions);
    }

    public void setActions(List<Action> actions) {
        this.actions = new ArrayList<>(actions);
    }

    public void addAction(Action action) {
        this.actions.add(action);
    }

    public File getVendorQmB64Png() {
        return vendorQmB64Png;
    }

    public void setVendorQmB64Png(File vendorQmB64Png) {
        this.vendorQmB64Png = vendorQmB64Png;
    }

    public void setWindowsJavaAppTable(ChangeFormTable windowsJavaAppTable) {
        this.windowsJavaAppTable = windowsJavaAppTable;
    }

    public void setPat(boolean pat) {
        isPat = pat;
    }

    public boolean isPat() {
        return isPat;
    }

    public ChangeFormTable getWindowsJavaAppTable() {
        return windowsJavaAppTable;
    }

    public ChangeFormTable getLinuxJavaAppTable() {
        return linuxJavaAppTable;
    }

    public void setLinuxJavaAppTable(final ChangeFormTable linuxJavaAppTable) {
        this.linuxJavaAppTable = linuxJavaAppTable;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Builder() {
        }

        private final ChangeFormData checkoutFormData = new ChangeFormData();

        public Builder setPat(boolean pat) {
            this.checkoutFormData.isPat = pat;
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

        public Builder setActions(List<Action> actions) {
            this.checkoutFormData.actions = new ArrayList<>(actions);
            return this;
        }

        public Builder addAction(Action action) {
            this.checkoutFormData.actions.add(action);
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

        public Builder setWindowsJavaAppTable(ChangeFormTable windowsJavaAppTable) {
            this.checkoutFormData.windowsJavaAppTable = windowsJavaAppTable;
            return this;
        }

        public Builder setLinuxJavaAppTable(final ChangeFormTable linuxJavaAppTable) {
            this.checkoutFormData.linuxJavaAppTable = linuxJavaAppTable;
            return this;
        }

        public ChangeFormData build() {
            return this.checkoutFormData;
        }
    }
}

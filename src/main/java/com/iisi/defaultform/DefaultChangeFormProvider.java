package com.iisi.defaultform;

import com.iisi.generator.model.changeform.ChangeFormTable;
import com.iisi.generator.model.changeform.ChangeFormTableRow;
import com.iisi.generator.model.changeform.LinuxChangeFormTableRow;
import com.iisi.generator.model.changeform.WindowsChangeFormTableRow;

import java.util.ArrayList;

public class DefaultChangeFormProvider {
    public ChangeFormTable defaultWindowsJavaTable(int rows) {
        ArrayList<ChangeFormTableRow> tableRows = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            tableRows.add(new WindowsChangeFormTableRow(
                    "", "", "", "", "",
                    "", ""));
        }

        return new ChangeFormTable(tableRows);
    }

    public ChangeFormTable defaultLinuxJavaTable(int rows) {
        ArrayList<ChangeFormTableRow> tableRows = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            tableRows.add(new LinuxChangeFormTableRow(
                    "", "", "", "", "",
                    "", ""));
        }

        return new ChangeFormTable(tableRows);
    }
}

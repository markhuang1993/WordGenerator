package com.iisi.defaultform;

import com.iisi.generator.model.changeform.ChangeFormTable;
import com.iisi.generator.model.changeform.ChangeFormTableRow;
import com.iisi.generator.model.changeform.LinuxChangeFormTableRow;
import com.iisi.generator.model.changeform.WindowsChangeFormTableRow;

import java.util.ArrayList;
import java.util.List;

public class DefaultChangeFormProvider {
    public ChangeFormTable windowsJavaTable(int rows) {
        final List<ChangeFormTableRow> tableRows = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            tableRows.add(new WindowsChangeFormTableRow(
                    "", "", "", "", "",
                    "", ""));
        }

        return new ChangeFormTable(tableRows);
    }

    public ChangeFormTable linuxJavaTable(int rows) {
        final List<ChangeFormTableRow> tableRows = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            tableRows.add(new LinuxChangeFormTableRow(
                    "", "", "", "", "",
                    "", ""));
        }

        return new ChangeFormTable(tableRows);
    }
}

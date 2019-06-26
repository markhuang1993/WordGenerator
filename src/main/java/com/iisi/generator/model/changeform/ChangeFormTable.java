package com.iisi.generator.model.changeform;

import com.iisi.generator.model.FormTable;
import com.iisi.generator.model.checkoutform.CheckoutFormTableRow;

import java.util.List;

public class ChangeFormTable implements FormTable {
    private List<ChangeFormTableRow> tableRows;

    public ChangeFormTable(List<ChangeFormTableRow> tableRows) {
        this.tableRows = tableRows;
    }

    @Override
    public List<ChangeFormTableRow> getTableRows() {
        return tableRows;
    }

    public void setTableRows(List<ChangeFormTableRow> tableRows) {
        this.tableRows = tableRows;
    }
}

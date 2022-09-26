package com.iisi.generator.model.checkoutform;

import com.iisi.generator.model.FormTable;

import java.util.List;

public class CheckoutFormTable implements FormTable {
    private List<CheckoutFormTableRow> tableRows;

    public CheckoutFormTable(List<CheckoutFormTableRow> tableRows) {
        this.tableRows = tableRows;
    }

    @Override
    public List<CheckoutFormTableRow> getTableRows() {
        return tableRows;
    }

    public void setTableRows(List<CheckoutFormTableRow> tableRows) {
        this.tableRows = tableRows;
    }
}

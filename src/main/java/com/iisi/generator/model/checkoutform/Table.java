package com.iisi.generator.model.checkoutform;

import java.util.List;

public class Table {
    private List<TableRow> tableRows;

    public Table(List<TableRow> tableRows) {
        this.tableRows = tableRows;
    }

    public List<TableRow> getTableRows() {
        return tableRows;
    }

    public void setTableRows(List<TableRow> tableRows) {
        this.tableRows = tableRows;
    }
}

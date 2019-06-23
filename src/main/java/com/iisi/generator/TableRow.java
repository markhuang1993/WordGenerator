package com.iisi.generator;

import java.util.ArrayList;
import java.util.List;

public class TableRow {
    private List<TableColumn> tableColumns = new ArrayList<>();

    public TableRow(List<TableColumn> tableColumns) {
        this.tableColumns = tableColumns;
    }

    public List<TableColumn> getTableColumns() {
        return tableColumns;
    }

    public void setTableColumns(List<TableColumn> tableColumns) {
        this.tableColumns = tableColumns;
    }
}

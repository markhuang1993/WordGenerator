package com.iisi.generator.model.checkoutform;

import com.iisi.generator.model.FormTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CheckoutFormTable implements FormTable {
    private List<CheckoutFormTableRow> tableRows;
    private static final List<String> TITLES = Arrays.asList("No", "System ID", "Program/File Name", "Program Execution Name", "Program Description");

    public CheckoutFormTable(List<CheckoutFormTableRow> tableRows) {
        this.tableRows = tableRows;
    }

    @Override
    public List<CheckoutFormTableRow> getTableRows() {
        return tableRows;
    }

    @Override
    public List<String> getTableHeadTitles() {
        return new ArrayList<>(TITLES);
    }

    public void setTableRows(List<CheckoutFormTableRow> tableRows) {
        this.tableRows = tableRows;
    }
}

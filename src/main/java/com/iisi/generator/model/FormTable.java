package com.iisi.generator.model;

import java.util.List;

public interface FormTable {
    List<? extends FormTableRow> getTableRows();
    List<String> getTableHeadTitles();
}

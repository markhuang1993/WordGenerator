package com.iisi.generator;

import com.iisi.freemarker.FreemarkerUtil;
import com.iisi.freemarker.FtlProvider;
import com.iisi.generator.model.FormData;
import com.iisi.generator.model.FormTable;
import com.iisi.generator.model.FormTableRow;
import com.iisi.util.FileUtil;
import com.iisi.util.ModelUtil;
import com.iisi.util.ResourceUtil;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

abstract class AbstractFormGenerator<T extends FormData> implements FormGenerator<T> {
    static final FtlProvider ftlProvider;

    static {
        final File templateDir = ResourceUtil.getClassPathResource("template");
        if (templateDir == null) {
            throw new RuntimeException("template directory not found in resources folder");
        }
        ftlProvider = new FtlProvider(templateDir);
    }

    Map<String, String> injectFormDataInMap(T formData) throws IllegalAccessException, IOException {
        final Map<String, String> dataMap = new HashMap<>();

        final Map<String, Object> formStrData = ModelUtil.getFieldsMap(formData, o -> o != null && o.getClass().equals(String.class));
        formStrData.forEach((k, v) -> dataMap.put(k, String.valueOf(v)));

        final Map<String, Object> formImgData = ModelUtil.getFieldsMap(formData, o -> o != null && o.getClass().equals(File.class));
        for (Map.Entry<String, Object> entry : formImgData.entrySet()) {
            dataMap.put(entry.getKey(), FileUtil.toBase64Encoding((File) entry.getValue()));
        }
        return dataMap;
    }

    String createTable(FormTable table, String tableTemplateBasePath) throws IOException, TemplateException, IllegalAccessException {
        if (table == null) {
            return "";
        }
        final Template t = ftlProvider.getFreeMarkerTemplate(tableTemplateBasePath + "/table.ftl");
        final StringBuilder sb = new StringBuilder();
        sb.append(createTableHeadRow(tableTemplateBasePath));
        if (table.getTableRows() == null) {
            return sb.toString();
        }
        for (FormTableRow tableRow : table.getTableRows()) {
            sb.append(createTableDefaultRow(tableRow, tableTemplateBasePath));
        }

        final Map<String, String> dataMap = new HashMap<>();
        dataMap.put("tableRows", sb.toString());

        final StringWriter stringWriter = new StringWriter();
        FreemarkerUtil.processTemplate(t, dataMap, stringWriter);
        return stringWriter.toString();
    }

    private String createTableRow(String tableColumns, Template rowTemplate) throws IOException, TemplateException {
        final Map<String, String> dataMap = new HashMap<>();
        dataMap.put("randomId", getRandomParaId());
        dataMap.put("tableColumns", tableColumns);

        final StringWriter stringWriter = new StringWriter();
        FreemarkerUtil.processTemplate(rowTemplate, dataMap, stringWriter);
        return stringWriter.toString();
    }

    private String createTableDefaultRow(FormTableRow tableRow, String tableTemplateBasePath) throws IOException, TemplateException, IllegalAccessException {
        final Template t = ftlProvider.getFreeMarkerTemplate(tableTemplateBasePath + "/table-row.ftl");
        final String tableColumns = createTableColumns(tableRow, tableTemplateBasePath);
        return createTableRow(tableColumns, t);
    }

    private String createTableHeadRow(String tableTemplateBasePath) throws IOException, TemplateException {
        final Template t = ftlProvider.getFreeMarkerTemplate(tableTemplateBasePath + "/table-row.ftl");
        final String tableColumns = createTableHeadColumns(tableTemplateBasePath);
        return createTableRow(tableColumns, t);
    }

    private String createTableHeadColumns(String tableTemplateBasePath) throws IOException, TemplateException {
        final Template t = ftlProvider.getFreeMarkerTemplate(tableTemplateBasePath + "/table-head-column.ftl");
        return processTemplateToString(new HashMap<>(), t);
    }

    private String createTableColumns(FormTableRow tableRow, String tableTemplateBasePath) throws IOException, TemplateException, IllegalAccessException {
        final Template t = ftlProvider.getFreeMarkerTemplate(tableTemplateBasePath + "/table-column.ftl");
        final Map<String, Object> fieldsMap = ModelUtil.getFieldsMap(tableRow);
        final Map<String, String> dataMap = new HashMap<>();
        fieldsMap.forEach((k, v) -> dataMap.put(k, String.valueOf(v)));
        return processTemplateToString(dataMap, t);
    }

    private String processTemplateToString(Map<String, String> dataMap, Template template) throws IOException, TemplateException {
        dataMap.put("randomId", getRandomParaId());
        final StringWriter stringWriter = new StringWriter();
        FreemarkerUtil.processTemplate(template, dataMap, stringWriter);
        return stringWriter.toString();
    }

    private String getRandomParaId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}

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
import java.util.List;
import java.util.Map;
import java.util.UUID;

abstract class AbstractFormGenerator<T extends FormData> implements FormGenerator<T> {
    static FtlProvider ftlProvider;

    static {
        File templateDir = ResourceUtil.getClassPathResource("template");
        if (templateDir == null) {
            throw new RuntimeException("template directory not found in resources folder");
        }
        ftlProvider = new FtlProvider(templateDir);
    }

    Map<String, String> injectFormDataInMap(T formData) throws IllegalAccessException, IOException {
        Map<String, String> dataMap = new HashMap<>();

        Map<String, Object> formStrData = ModelUtil.getFieldsMap(formData, o -> o.getClass().equals(String.class));
        formStrData.forEach((k, v) -> dataMap.put(k, String.valueOf(v)));

        Map<String, Object> formImgData = ModelUtil.getFieldsMap(formData, o -> o.getClass().equals(File.class));
        for (Map.Entry<String, Object> entry : formImgData.entrySet()) {
            dataMap.put(entry.getKey(), FileUtil.toBase64Encoding((File) entry.getValue()));
        }
        return dataMap;
    }

    String createTable(FormTable table, String tableTemplateBasePath) throws IOException, TemplateException, IllegalAccessException {
        Template t = ftlProvider.getFreeMarkerTemplate(tableTemplateBasePath + "/table.ftl");
        StringBuilder sb = new StringBuilder();
        sb.append(createTableHeadRow(table.getTableHeadTitles(), tableTemplateBasePath));
        for (FormTableRow tableRow : table.getTableRows()) {
            sb.append(createTableDefaultRow(tableRow, tableTemplateBasePath));
        }

        HashMap<String, String> dataMap = new HashMap<>();
        dataMap.put("tableRows", sb.toString());

        StringWriter stringWriter = new StringWriter();
        FreemarkerUtil.processTemplate(t, dataMap, stringWriter);
        return stringWriter.toString();
    }

    private String createTableRow(String tableColumns, Template rowTemplate) throws IOException, TemplateException {
        HashMap<String, String> dataMap = new HashMap<>();
        dataMap.put("randomId", getRandomParaId());
        dataMap.put("tableColumns", tableColumns);

        StringWriter stringWriter = new StringWriter();
        FreemarkerUtil.processTemplate(rowTemplate, dataMap, stringWriter);
        return stringWriter.toString();
    }

    private String createTableDefaultRow(FormTableRow tableRow, String tableTemplateBasePath) throws IOException, TemplateException, IllegalAccessException {
        Template t = ftlProvider.getFreeMarkerTemplate(tableTemplateBasePath + "/table-row.ftl");
        String tableColumns = createTableColumns(tableRow, tableTemplateBasePath);
        return createTableRow(tableColumns, t);
    }

    private String createTableHeadRow(List<String> titles, String tableTemplateBasePath) throws IOException, TemplateException {
        Template t = ftlProvider.getFreeMarkerTemplate(tableTemplateBasePath + "/table-row.ftl");
        String tableColumns = createTableHeadColumns(titles, tableTemplateBasePath);
        return createTableRow(tableColumns, t);
    }

    private String createTableHeadColumns(List<String> titles, String tableTemplateBasePath) throws IOException, TemplateException {
        Template t = ftlProvider.getFreeMarkerTemplate(tableTemplateBasePath + "/table-head-column.ftl");
        StringBuilder sb = new StringBuilder();
        for (String title : titles) {
            sb.append(processColumnTemplate(title, t));
        }
        return sb.toString();
    }

    private String createTableColumns(FormTableRow tableRow, String tableTemplateBasePath) throws IOException, TemplateException, IllegalAccessException {
        Template t = ftlProvider.getFreeMarkerTemplate(tableTemplateBasePath + "/table-column.ftl");
        StringBuilder sb = new StringBuilder();

        Map<String, Object> fieldsMap = ModelUtil.getFieldsMap(tableRow);
        for (Object value : fieldsMap.values()) {
            sb.append(processColumnTemplate(String.valueOf(value), t));
        }

        return sb.toString();
    }

    private String processColumnTemplate(String columnValue, Template columnTemplate) throws IOException, TemplateException {
        HashMap<String, String> dataMap = new HashMap<>();
        dataMap.put("randomId", getRandomParaId());
        dataMap.put("columnValue", String.valueOf(columnValue));
        StringWriter stringWriter = new StringWriter();
        FreemarkerUtil.processTemplate(columnTemplate, dataMap, stringWriter);
        return stringWriter.toString();
    }

    private String getRandomParaId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}

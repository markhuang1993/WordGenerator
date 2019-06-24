package com.iisi.generator;

import com.iisi.freemarker.FreemarkerUtil;
import com.iisi.generator.model.checkoutform.CheckoutFormData;
import com.iisi.generator.model.checkoutform.Table;
import com.iisi.generator.model.checkoutform.TableRow;
import com.iisi.util.FileUtil;
import com.iisi.util.ModelUtil;
import com.iisi.util.ResourceUtil;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class CheckoutFormGenerator extends AbstractFormGenerator<CheckoutFormData> {

    public static void main(String[] args) throws IOException, TemplateException, IllegalAccessException {
        CheckoutFormGenerator checkoutFormGenerator = new CheckoutFormGenerator();
        CheckoutFormData formData = CheckoutFormData.builder()
                .setLacrNo("37037")
                .setSystemApplication("sapp")
                .setSystemApplication("2019-06-22")
                .setSubmitDate("asdwq")
                .setLacrCoordinator("77777")
                .setLibrarian("shxt")
                .setProcessDate("2019-06-23")
                .setProgrammerB64Png(ResourceUtil.getClassPathResource("image/mark.png"))
                .setSupervisorB64Png(ResourceUtil.getClassPathResource("image/huang.png"))
                .setJavaAppTable(checkoutFormGenerator.tableData())
                .build();
        checkoutFormGenerator.createForm(formData);
    }

    private Table tableData() {
        ArrayList<TableRow> tableRows = new ArrayList<>();
        String s = Arrays.stream(new String[50]).map(x -> "q").collect(Collectors.joining(""));
        String s1 = Arrays.stream(new String[50]).map(x -> "w").collect(Collectors.joining(""));
        String s2 = Arrays.stream(new String[50]).map(x -> "e").collect(Collectors.joining(""));
        String s3 = Arrays.stream(new String[50]).map(x -> "t").collect(Collectors.joining(""));
        tableRows.add(new TableRow(s, s1, s2, s3, "床前明月光\r\n疑似地上霜\r\n舉頭望明月\r\n低頭思故鄉"));
        for (int i = 0; i < 160; i++) {
            tableRows.add(new TableRow(
                    String.valueOf(i),
                    String.valueOf(i + 160),
                    String.valueOf(i + 320),
                    String.valueOf(i + 480),
                    String.valueOf(i + 640)
            ));
        }

        return new Table(tableRows);
    }

    private String createJavaAppTable(Table table) throws IOException, TemplateException, IllegalAccessException {
        Template t = ftlProvider.getFreeMarkerTemplate("word/table/visualStudioOrJava/table.ftl");
        StringBuilder sb = new StringBuilder();
        sb.append(createTableRow(null, true));
        for (TableRow tableRow : table.getTableRows()) {
            sb.append(createTableRow(tableRow, false));
        }

        HashMap<String, String> dataMap = new HashMap<>();
        dataMap.put("tableRows", sb.toString());

        StringWriter stringWriter = new StringWriter();
        FreemarkerUtil.processTemplate(t, dataMap, stringWriter);
        return stringWriter.toString();
    }

    private String createTableRow(TableRow tableRow, boolean isHead) throws IOException, TemplateException, IllegalAccessException {
        Template t = ftlProvider.getFreeMarkerTemplate("word/table/visualStudioOrJava/table-row.ftl");
        String tableColumns = isHead ? createTableHeadColumns() : createTableColumns(tableRow);

        HashMap<String, String> dataMap = new HashMap<>();
        dataMap.put("randomId", getRandomParaId());
        dataMap.put("tableColumns", tableColumns);

        StringWriter stringWriter = new StringWriter();
        FreemarkerUtil.processTemplate(t, dataMap, stringWriter);
        return stringWriter.toString();
    }

    private String createTableHeadColumns() throws IOException, TemplateException {
        Template t = ftlProvider.getFreeMarkerTemplate("word/table/visualStudioOrJava/table-head-column.ftl");
        String[] titles = new String[]{"No", "System ID", "Program/File Name", "Program Execution Name", "Program Description"};
        StringBuilder sb = new StringBuilder();
        for (String title : titles) {
            sb.append(processColumnTemplate(title, t));
        }
        return sb.toString();
    }

    private String createTableColumns(TableRow tableRow) throws IOException, TemplateException, IllegalAccessException {
        Template t = ftlProvider.getFreeMarkerTemplate("word/table/visualStudioOrJava/table-column.ftl");
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

    public File createForm(CheckoutFormData checkoutFormData) throws IOException, TemplateException, IllegalAccessException {
        File documentFile = new File("checkoutForm.doc");

        CheckoutFormGenerator checkoutFormGenerator = new CheckoutFormGenerator();
        HashMap<String, String> dataMap = new HashMap<>();
        dataMap.put("lacrNo", checkoutFormData.getLacrNo());
        dataMap.put("systemApplication", checkoutFormData.getSystemApplication());
        dataMap.put("submitDate", checkoutFormData.getSubmitDate());
        dataMap.put("lacrCoordinator", checkoutFormData.getLacrCoordinator());
        dataMap.put("librarian", checkoutFormData.getLibrarian());
        dataMap.put("processDate", checkoutFormData.getProcessDate());

        dataMap.put("programmerB64Img", FileUtil.toBase64Encoding(checkoutFormData.getProgrammerB64Png()));
        dataMap.put("supervisorB64Img", FileUtil.toBase64Encoding(checkoutFormData.getSupervisorB64Png()));

        Table table = checkoutFormData.getJavaAppTable();
        String javaAppTable = checkoutFormGenerator.createJavaAppTable(table);
        dataMap.put("javaCheckoutTable", javaAppTable);

        Template t = ftlProvider.getFreeMarkerTemplate("word/checkoutForm.ftl");

        try (Writer w = new OutputStreamWriter(new FileOutputStream(documentFile), StandardCharsets.UTF_8)){
            t.process(dataMap, w);
            w.flush();
        }

        System.out.println("doc is created at:" + documentFile.getAbsolutePath());
        return documentFile;
    }

    private String getRandomParaId() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}


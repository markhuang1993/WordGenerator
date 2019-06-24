package com.iisi.generator;

import com.iisi.freemarker.FreemarkerUtil;
import com.iisi.freemarker.FtlProvider;
import com.iisi.generator.model.Table;
import com.iisi.generator.model.TableRow;
import com.iisi.util.FileUtil;
import com.iisi.util.ModelUtil;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class CheckoutFormGenerator {

    private static FtlProvider ftlProvider;

    static {
        URL resource = CheckoutFormGenerator.class.getClassLoader().getResource("template/word/checkoutForm.ftl");
        if (resource == null) {
            throw new RuntimeException("template/word/checkoutForm.ftl not found in resources folder");
        }
        String checkoutFormFtl = resource.getFile();
        ftlProvider = new FtlProvider(new File(checkoutFormFtl).getParentFile());
    }

    public static void main(String[] args) throws IOException, TemplateException, IllegalAccessException {
        CheckoutFormGenerator checkoutFormGenerator = new CheckoutFormGenerator();
        HashMap<String, String> dataMap = new HashMap<>();
        dataMap.put("lacrNo", "37037");
        dataMap.put("systemApplication", "sapp");
        dataMap.put("submitDate", "2019-06-22");
        dataMap.put("lacrCoordinator", "asdwq");
        dataMap.put("librarian", "shxt");
        dataMap.put("processDate", "2019-06-23");

        String pgStr = FileUtil.toBase64Encoding(new File("C:\\Users\\markh\\IdeaProjects\\WordGenerator\\src\\main\\resources\\image\\mark.png"));
        String spvStr = FileUtil.toBase64Encoding(new File("C:\\Users\\markh\\IdeaProjects\\WordGenerator\\src\\main\\resources\\image\\huang.png"));
        dataMap.put("programmerB64Img", pgStr);
        dataMap.put("supervisorB64Img", spvStr);

        Table table = checkoutFormGenerator.tableData();
        String javaAppTable = checkoutFormGenerator.createJavaAppTable(table);
        dataMap.put("javaCheckoutTable", javaAppTable);

        File documentFile = checkoutFormGenerator.createDocument(dataMap);
        System.out.println("doc is created at:" + documentFile.getAbsolutePath());
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
        Template t = ftlProvider.getFreeMarkerTemplate("table/visualStudioOrJava/table.ftl");
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
        Template t = ftlProvider.getFreeMarkerTemplate("table/visualStudioOrJava/table-row.ftl");
        String tableColumns = isHead ? createTableHeadColumns() : createTableColumns(tableRow);

        HashMap<String, String> dataMap = new HashMap<>();
        dataMap.put("randomId", getRandomParaId());
        dataMap.put("tableColumns", tableColumns);

        StringWriter stringWriter = new StringWriter();
        FreemarkerUtil.processTemplate(t, dataMap, stringWriter);
        return stringWriter.toString();
    }

    private String createTableHeadColumns() throws IOException, TemplateException {
        Template t = ftlProvider.getFreeMarkerTemplate("table/visualStudioOrJava/table-head-column.ftl");
        String[] titles = new String[]{"No", "System ID", "Program/File Name", "Program Execution Name", "Program Description"};
        StringBuilder sb = new StringBuilder();
        for (String title : titles) {
            sb.append(processColumnTemplate(title, t));
        }
        return sb.toString();
    }

    private String createTableColumns(TableRow tableRow) throws IOException, TemplateException, IllegalAccessException {
        Template t = ftlProvider.getFreeMarkerTemplate("table/visualStudioOrJava/table-column.ftl");
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

    public File createDocument(Map<?, ?> dataMap) {
        File f = new File("checkoutForm.doc");
        Template t = ftlProvider.getFreeMarkerTemplate("checkoutForm.ftl");
        try {
            Writer w = new OutputStreamWriter(new FileOutputStream(f), StandardCharsets.UTF_8);
            t.process(dataMap, w);
            w.flush();
            w.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        return f;
    }

    private String getRandomParaId() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}


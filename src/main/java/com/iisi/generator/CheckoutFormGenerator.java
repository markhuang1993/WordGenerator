package com.iisi.generator;

import com.iisi.generator.model.checkoutform.CheckoutFormData;
import com.iisi.generator.model.checkoutform.CheckoutFormTable;
import com.iisi.generator.model.checkoutform.CheckoutFormTableRow;
import com.iisi.util.FileUtil;
import com.iisi.util.ResourceUtil;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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

    private CheckoutFormTable tableData() {
        ArrayList<CheckoutFormTableRow> tableRows = new ArrayList<>();
        String s = Arrays.stream(new String[50]).map(x -> "q").collect(Collectors.joining(""));
        String s1 = Arrays.stream(new String[50]).map(x -> "w").collect(Collectors.joining(""));
        String s2 = Arrays.stream(new String[50]).map(x -> "e").collect(Collectors.joining(""));
        String s3 = Arrays.stream(new String[50]).map(x -> "t").collect(Collectors.joining(""));
        tableRows.add(new CheckoutFormTableRow(s, s1, s2, s3, "床前明月光\r\n疑似地上霜\r\n舉頭望明月\r\n低頭思故鄉"));
        for (int i = 0; i < 160; i++) {
            tableRows.add(new CheckoutFormTableRow(
                    String.valueOf(i),
                    String.valueOf(i + 160),
                    String.valueOf(i + 320),
                    String.valueOf(i + 480),
                    String.valueOf(i + 640)
            ));
        }

        return new CheckoutFormTable(tableRows);
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

        CheckoutFormTable table = checkoutFormData.getJavaAppTable();
        String javaAppTable = checkoutFormGenerator.createTable(table, "word/table/visualStudioOrJava");
        dataMap.put("javaCheckoutTable", javaAppTable);

        Template t = ftlProvider.getFreeMarkerTemplate("word/checkoutForm.ftl");

        try (Writer w = new OutputStreamWriter(new FileOutputStream(documentFile), StandardCharsets.UTF_8)){
            t.process(dataMap, w);
            w.flush();
        }

        System.out.println("doc is created at:" + documentFile.getAbsolutePath());
        return documentFile;
    }

}


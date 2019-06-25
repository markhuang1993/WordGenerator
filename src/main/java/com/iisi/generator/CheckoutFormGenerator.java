package com.iisi.generator;

import com.iisi.generator.model.checkoutform.CheckoutFormData;
import com.iisi.generator.model.checkoutform.CheckoutFormTable;
import com.iisi.util.FileUtil;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class CheckoutFormGenerator extends AbstractFormGenerator<CheckoutFormData> {

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


package com.iisi.generator;

import com.iisi.freemarker.FreemarkerUtil;
import com.iisi.generator.model.checkoutform.CheckoutFormData;
import com.iisi.generator.model.checkoutform.CheckoutFormTable;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class CheckoutFormGenerator extends AbstractFormGenerator<CheckoutFormData> {

    @SuppressWarnings("Duplicates")
    public File processFormTemplate(CheckoutFormData checkoutFormData, File destDir) throws IOException, TemplateException, IllegalAccessException {
        File documentFile = new File(destDir,"checkoutForm.doc");
        Map<String, String> dataMap = new HashMap<>(injectFormDataInMap(checkoutFormData));

        CheckoutFormTable table = checkoutFormData.getJavaAppTable();
        String javaAppTable = this.createTable(table, "word/table/checkoutform/java");
        dataMap.put("javaCheckoutTable", javaAppTable);

        Template t = ftlProvider.getFreeMarkerTemplate("word/checkoutForm.ftl");
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(documentFile), StandardCharsets.UTF_8);
        FreemarkerUtil.processTemplate(t, dataMap, writer);

        System.out.println("checkout form doc is created at:" + documentFile.getAbsolutePath());
        return documentFile;
    }

}


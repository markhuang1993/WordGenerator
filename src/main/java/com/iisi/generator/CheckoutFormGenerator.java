package com.iisi.generator;

import com.iisi.freemarker.FreemarkerUtil;
import com.iisi.generator.model.checkoutform.CheckoutFormData;
import com.iisi.generator.model.checkoutform.CheckoutFormTable;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class CheckoutFormGenerator extends AbstractFormGenerator<CheckoutFormData> {

    @SuppressWarnings("Duplicates")
    public File processFormTemplate(CheckoutFormData checkoutFormData, File destDir, String formName) throws IOException, TemplateException, IllegalAccessException {
        final File documentFile = new File(destDir, formName);
        final Map<String, String> dataMap = new HashMap<>(injectFormDataInMap(checkoutFormData));

        final CheckoutFormTable table = checkoutFormData.getJavaAppTable();
        final String javaAppTable = this.createTable(table, "word/table/checkoutform/java");
        dataMap.put("javaCheckoutTable", javaAppTable);

        final Template t = ftlProvider.getFreeMarkerTemplate("word/checkoutForm.ftl");
        final OutputStreamWriter writer = new OutputStreamWriter(Files.newOutputStream(documentFile.toPath()), StandardCharsets.UTF_8);
        FreemarkerUtil.processTemplate(t, dataMap, writer);

        return documentFile;
    }

}


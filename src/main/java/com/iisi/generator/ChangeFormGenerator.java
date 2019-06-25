package com.iisi.generator;

import com.iisi.freemarker.FreemarkerUtil;
import com.iisi.generator.model.changeform.ChangeFormData;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ChangeFormGenerator extends AbstractFormGenerator<ChangeFormData> {

    @SuppressWarnings("Duplicates")
    public File processFormTemplate(ChangeFormData changeFormData) throws IOException, TemplateException, IllegalAccessException {
        File documentFile = new File("changeForm.doc");
        Map<String, String> dataMap = new HashMap<>(injectFormDataInMap(changeFormData));

//        CheckoutFormTable table = changeFormData.getJavaAppTable();
//        String javaAppTable = this.createTable(table, "word/table/visualStudioOrJava");
//        dataMap.put("javaCheckoutTable", javaAppTable);

        Template t = ftlProvider.getFreeMarkerTemplate("word/changeForm.ftl");
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(documentFile), StandardCharsets.UTF_8);
        FreemarkerUtil.processTemplate(t, dataMap, writer);

        System.out.println("doc is created at:" + documentFile.getAbsolutePath());
        return documentFile;
    }
}

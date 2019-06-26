package com.iisi.generator;

import com.iisi.freemarker.FreemarkerUtil;
import com.iisi.generator.model.changeform.ChangeFormData;
import com.iisi.generator.model.changeform.ChangeFormTable;
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

        ChangeFormTable table = changeFormData.getJavaAppTable();
        String javaAppTable = this.createTable(table, "word/table/changeform/java");
        dataMap.put("javaChangeTable", javaAppTable);

        Template t = ftlProvider.getFreeMarkerTemplate("word/changeForm.ftl");
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(documentFile), StandardCharsets.UTF_8);
        FreemarkerUtil.processTemplate(t, dataMap, writer);

        System.out.println("doc is created at:" + documentFile.getAbsolutePath());
        return documentFile;
    }
}

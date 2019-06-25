package com.iisi.generator;

import com.iisi.generator.model.changeform.ChangeFormData;
import com.iisi.util.FileUtil;
import com.iisi.util.ModelUtil;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ChangeFormGenerator extends AbstractFormGenerator<ChangeFormData> {

    @SuppressWarnings("Duplicates")
    public File createForm(ChangeFormData changeFormData) throws IOException, TemplateException, IllegalAccessException {
        File documentFile = new File("changeForm.doc");

        HashMap<String, String> dataMap = new HashMap<>();

        Map<String, Object> formStrData = ModelUtil.getFieldsMap(changeFormData, o -> o.getClass().equals(String.class));
        formStrData.forEach((k, v) -> dataMap.put(k, String.valueOf(v)));

        Map<String, Object> formImgData = ModelUtil.getFieldsMap(changeFormData, o -> o.getClass().equals(File.class));
        for (Map.Entry<String, Object> entry : formImgData.entrySet()) {
            dataMap.put(entry.getKey(), FileUtil.toBase64Encoding((File) entry.getValue()));
        }

//        CheckoutFormTable table = changeFormData.getJavaAppTable();
//        String javaAppTable = this.createTable(table, "word/table/visualStudioOrJava");
//        dataMap.put("javaCheckoutTable", javaAppTable);

        Template t = ftlProvider.getFreeMarkerTemplate("word/changeForm.ftl");

        try (Writer w = new OutputStreamWriter(new FileOutputStream(documentFile), StandardCharsets.UTF_8)) {
            t.process(dataMap, w);
            w.flush();
        }

        System.out.println("doc is created at:" + documentFile.getAbsolutePath());
        return documentFile;
    }
}

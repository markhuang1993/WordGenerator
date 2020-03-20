package com.iisi.generator;

import com.iisi.constants.CheckboxString;
import com.iisi.freemarker.FreemarkerUtil;
import com.iisi.generator.model.changeform.Action;
import com.iisi.generator.model.changeform.ChangeFormData;
import com.iisi.generator.model.changeform.ChangeFormTable;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ChangeFormGenerator extends AbstractFormGenerator<ChangeFormData> {

    @SuppressWarnings("Duplicates")
    public File processFormTemplate(ChangeFormData changeFormData, File destDir) throws IOException, TemplateException, IllegalAccessException {
        File documentFile = new File(destDir, "changeForm.doc");
        Map<String, String> dataMap = new HashMap<>(injectFormDataInMap(changeFormData));

        Map<String, String> envData = processDeployEnv(changeFormData.isPat());
        dataMap.putAll(envData);

        String actionRows = processActionsTable(changeFormData.getActions());
        dataMap.put("actionRows", actionRows);

        ChangeFormTable table = changeFormData.getJavaAppTable();
        String javaAppTable = this.createTable(table, "word/table/changeform/java");
        dataMap.put("javaChangeTable", javaAppTable);

        Template t = ftlProvider.getFreeMarkerTemplate("word/changeForm.ftl");
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(documentFile), StandardCharsets.UTF_8);
        FreemarkerUtil.processTemplate(t, dataMap, writer);

        System.out.println("change form doc is created at:" + documentFile.getAbsolutePath());
        return documentFile;
    }

    private Map<String, String> processDeployEnv(boolean isPat) throws IOException, TemplateException {
        Map<String, String> result = new HashMap<>();
        result.put("promoteToUat", isPat ? CheckboxString.UNCHECKED.val() : CheckboxString.CHECKED.val());
        result.put("promoteToProduction", isPat ? CheckboxString.CHECKED.val() : CheckboxString.UNCHECKED.val());
        if (isPat) {
            Template sourceReviewGuidTemplate = ftlProvider.getFreeMarkerTemplate("word/table/changeform/source_code_review_guide.ftl");
            String  sourceReviewGuidStr = FreemarkerUtil.processTemplateToString(sourceReviewGuidTemplate, Collections.emptyMap());
            result.put("sourceCodeReviewGuid", sourceReviewGuidStr);
        }
        return result;
    }

    private String processActionsTable(List<Action> actions) throws IOException, TemplateException {
        Template rowTemplate = ftlProvider.getFreeMarkerTemplate("word/table/changeform/action/row.ftl");
        Template columnParagraphTemplate = ftlProvider.getFreeMarkerTemplate("word/table/changeform/action/column-paragraph.ftl");
        StringBuilder rows = new StringBuilder();
        int index = 0;
        for (Action action : actions) {
            index++;
            StringBuilder paragraphs = new StringBuilder();
            for (String line : action.getLines()) {
                String paragraph = FreemarkerUtil.processTemplateToString(columnParagraphTemplate, Collections.singletonMap("line", line));
                paragraphs.append(paragraph);
            }

            Map<String, String> m = new HashMap<>();
            m.put("index", String.valueOf(index));
            m.put("paragraphs", paragraphs.toString());
            String row = FreemarkerUtil.processTemplateToString(rowTemplate, m);
            rows.append(row);
        }
        return rows.toString();
    }
}

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

    public File processFormTemplate(ChangeFormData changeFormData, File destDir, String formName) throws IOException, TemplateException, IllegalAccessException {
        final File documentFile = new File(destDir, formName);
        final Map<String, String> dataMap = new HashMap<>(injectFormDataInMap(changeFormData));

        final Map<String, String> envData = processDeployEnv(changeFormData.isPat());
        dataMap.putAll(envData);

        final String actionRows = processActionsTable(changeFormData.getActions());
        dataMap.put("actionRows", actionRows);

        final ChangeFormTable windowsJavaTable = changeFormData.getWindowsJavaAppTable();
        final String windowsJavaTableStr = this.createTable(windowsJavaTable, "word/table/changeform/windows_java");
        dataMap.put("windowsJavaChangeTable", windowsJavaTableStr);

        final ChangeFormTable linuxJavaTable = changeFormData.getLinuxJavaAppTable();
        final String linuxJavaTableStr = this.createTable(linuxJavaTable, "word/table/changeform/linux_java");
        dataMap.put("linuxJavaChangeTable", linuxJavaTableStr);

        final Template t = ftlProvider.getFreeMarkerTemplate("word/changeForm.ftl");
        final OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(documentFile), StandardCharsets.UTF_8);
        FreemarkerUtil.processTemplate(t, dataMap, writer);

        return documentFile;
    }

    private Map<String, String> processDeployEnv(boolean isPat) throws IOException, TemplateException {
        Map<String, String> result = new HashMap<>();
        result.put("promoteToUat", isPat ? CheckboxString.UNCHECKED.val() : CheckboxString.CHECKED.val());
        result.put("promoteToProduction", isPat ? CheckboxString.CHECKED.val() : CheckboxString.UNCHECKED.val());
        if (isPat) {
            Template sourceReviewGuidTemplate = ftlProvider.getFreeMarkerTemplate("word/table/changeform/source_code_review_guide.ftl");
            String sourceReviewGuidStr = FreemarkerUtil.processTemplateToString(sourceReviewGuidTemplate, Collections.emptyMap());
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

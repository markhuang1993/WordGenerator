package com.iisi.freemarker;

import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

public class FreemarkerUtil {
    public static void processTemplate(Template template, Object dataModel, Writer out) throws IOException, TemplateException {
        try {
            template.process(dataModel, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.flush();
            out.close();
        }
    }

    public static String processTemplateToString(Template template, Object dataModel) throws IOException, TemplateException {
        StringWriter stringWriter = new StringWriter();
        processTemplate(template, dataModel, stringWriter);
        return stringWriter.toString();
    }
}

package com.iisi.freemarker;

import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.Writer;

public class FreemarkerUtil {
    public static void processTemplate(Template template, Object dataModel, Writer out) throws IOException, TemplateException {
        try {
            template.process(dataModel, out);
        } finally {
            out.flush();
            out.close();
        }
    }
}

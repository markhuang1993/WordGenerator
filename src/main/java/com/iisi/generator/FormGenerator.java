package com.iisi.generator;

import com.iisi.generator.model.FormData;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;

public interface FormGenerator<T extends FormData> {
    File processFormTemplate(T formData, File destDir) throws IOException, TemplateException, IllegalAccessException;
}

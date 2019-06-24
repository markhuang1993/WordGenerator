package com.iisi.generator;

import com.iisi.generator.model.FormData;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;

public interface FormGenerator<T extends FormData> {
    File createForm(T formData) throws IOException, TemplateException, IllegalAccessException;
}

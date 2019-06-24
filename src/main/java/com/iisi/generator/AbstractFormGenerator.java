package com.iisi.generator;

import com.iisi.freemarker.FtlProvider;
import com.iisi.generator.model.FormData;
import com.iisi.util.ResourceUtil;

import java.io.File;

abstract class AbstractFormGenerator<T extends FormData> implements FormGenerator<T> {
    static FtlProvider ftlProvider;

    static {
        File templateDir = ResourceUtil.getClassPathResource("template");
        if (templateDir == null) {
            throw new RuntimeException("template directory not found in resources folder");
        }
        ftlProvider = new FtlProvider(templateDir);
    }
}

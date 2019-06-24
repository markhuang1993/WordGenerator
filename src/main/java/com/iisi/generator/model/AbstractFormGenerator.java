package com.iisi.generator.model;

import com.iisi.freemarker.FtlProvider;
import com.iisi.generator.FormGenerator;
import com.iisi.util.ResourceUtil;

import java.io.File;

public abstract class AbstractFormGenerator implements FormGenerator {
    protected static FtlProvider ftlProvider;

    static {
        File templateDir = ResourceUtil.getClassPathResource("template");
        if (templateDir == null) {
            throw new RuntimeException("template directory not found in resources folder");
        }
        ftlProvider = new FtlProvider(templateDir);
    }
}

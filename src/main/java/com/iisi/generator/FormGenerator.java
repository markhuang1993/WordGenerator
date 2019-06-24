package com.iisi.generator;

import java.io.File;
import java.util.Map;

public interface FormGenerator {
    File createForm(Map<?, ?> dataMap);
}

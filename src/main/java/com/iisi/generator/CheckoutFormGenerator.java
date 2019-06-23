package com.iisi.generator;

import com.iisi.freemarker.FtlProvider;
import com.iisi.util.FileUtil;
import freemarker.template.Template;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class CheckoutFormGenerator {

    private static FtlProvider ftlProvider;

    static {
        URL resource = CheckoutFormGenerator.class.getClassLoader().getResource("template/word/checkoutForm.ftl");
        if (resource == null) {
            throw new RuntimeException("template/word/checkoutForm.ftl not found in resources folder");
        }
        String checkoutFormFtl = resource.getFile();
        ftlProvider = new FtlProvider(new File(checkoutFormFtl).getParentFile());
    }

    public static void main(String[] args) throws IOException {
        HashMap<String, String> dataMap = new HashMap<>();
        dataMap.put("lacrNo", "37037");
        dataMap.put("systemApplication", "sapp");
        dataMap.put("submitDate", "2019-06-22");
        dataMap.put("lacrCoordinator", "asdwq");
        dataMap.put("librarian", "shxt");
        dataMap.put("processDate", "2019-06-23");
        String pgStr = FileUtil.toBase64Encoding(new File("C:\\Users\\markh\\Desktop\\mark.png"));
        String spvStr = FileUtil.toBase64Encoding(new File("C:\\Users\\markh\\Desktop\\huang.png"));
        dataMap.put("programmerB64Img", pgStr);
        dataMap.put("supervisorB64Img", spvStr);
        CheckoutFormGenerator checkoutFormGenerator = new CheckoutFormGenerator();
        checkoutFormGenerator.createDocument(dataMap, "resume");
    }

    private String createJavaAppTable(TableRow tableRow) {
        Template t = ftlProvider.getFreeMarkerTemplate("table/table.ftl");
        return "";
    }

    private String createTableRow() {
        return "";
    }

    private String createTableHeadColumn() {
        return "";
    }

    private String createTableColumn(TableColumn tableColumn) {
        return "";
    }

    public File createDocument(Map<?, ?> dataMap, String type) {
        File f = new File("checkoutForm.doc");
        Template t = ftlProvider.getFreeMarkerTemplate("checkoutForm.ftl");
        try {
            Writer w = new OutputStreamWriter(new FileOutputStream(f), StandardCharsets.UTF_8);
            t.process(dataMap, w);
            w.flush();
            w.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
        return f;
    }
}


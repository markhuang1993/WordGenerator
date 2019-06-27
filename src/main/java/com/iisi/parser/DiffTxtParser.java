package com.iisi.parser;

import com.iisi.constants.DiffStatus;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class DiffTxtParser {

    private DiffTxtParser() {
    }

    private static class Nested {
        private static final DiffTxtParser DIFF_TXT_PARSER = new DiffTxtParser();
    }

    public static DiffTxtParser getInstance() {
        return Nested.DIFF_TXT_PARSER;
    }

    public List<DiffDetail> parseDiffTxt(File diffTxtFile) throws IOException {
        byte[] bytes = Files.readAllBytes(diffTxtFile.toPath());
        String diffTxt = new String(bytes);
        String[] sp = diffTxt.split("-{5,}");
        if (sp.length < 2) return new ArrayList<>();

        List<DiffDetail> diffDetails = new ArrayList<>();
        String[] diffDetailLines = sp[1].split("\n");
        for (String diffDetailLine : diffDetailLines) {
            DiffDetail diffDetail = new DiffDetail();
            if (diffDetailLine == null || "".equals(diffDetailLine)) continue;
            String[] detail = diffDetailLine.split("\\|");
            int len = detail.length;
            if (len > 0) {
                String d0 = detail[0].trim();
                if ("Added".equals(d0)) {
                    diffDetail.setStatus(DiffStatus.A);
                } else if ("Modified".equals(d0)) {
                    diffDetail.setStatus(DiffStatus.A);
                }
            }

            if (len > 1) {
                diffDetail.setFilePath(detail[1].trim());
            }
            diffDetails.add(diffDetail);
        }

        return diffDetails;
    }

}


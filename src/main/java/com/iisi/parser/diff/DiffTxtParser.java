package com.iisi.parser.diff;

import com.iisi.constants.DiffStatus;
import com.iisi.util.XmlUtil;

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
        final byte[] bytes = Files.readAllBytes(diffTxtFile.toPath());
        final String diffTxt = new String(bytes);
        final String[] sp = diffTxt.split("-{5,}");
        if (sp.length < 2) return new ArrayList<>();

        final List<DiffDetail> diffDetails = new ArrayList<>();
        final String[] diffDetailLines = sp[1].split("\r?\n");
        for (String diffDetailLine : diffDetailLines) {
            final DiffDetail diffDetail = new DiffDetail();
            if (diffDetailLine == null || "".equals(diffDetailLine)) continue;
            final String[] detail = diffDetailLine.split("\\|");
            int len = detail.length;
            if (len > 0) {
                final String d0 = detail[0].trim();
                switch (d0) {
                    case "Added":
                        diffDetail.setStatus(DiffStatus.A);
                        break;
                    case "Modified":
                        diffDetail.setStatus(DiffStatus.M);
                        break;
                    case "Deleted":
                        diffDetail.setStatus(DiffStatus.D);
                        break;
                    default:
                        diffDetail.setStatus(DiffStatus.UNKNOWN);
                        break;
                }
            }

            if (len > 1) {
                diffDetail.setFilePath(XmlUtil.specialStringToXmlFormat(detail[1].trim()));
            }
            diffDetails.add(diffDetail);
        }

        return diffDetails;
    }

}


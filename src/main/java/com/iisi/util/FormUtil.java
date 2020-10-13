package com.iisi.util;

import com.iisi.generator.model.changeform.Action;
import com.iisi.parser.diff.DiffDetail;
import com.iisi.parser.form.model.yml.global.GlobalYmlParseResult;
import com.iisi.parser.form.model.yml.local.LocalYmlParseResult;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FormUtil {
    public static List<Action> processFormActionValues(List<Action> actions, LocalYmlParseResult localYmlParseResult) {
        Map<String, Object> m = new HashMap<>();
        m.put("lcfg", localYmlParseResult.getOriginMap());
        return actions.stream().map(action -> {
            Action newAction = new Action();
            List<String> lines = action.getLines();
            for (String line : lines) {
                String newLine = replaceStr(line, m);
                if (!"".equals(newLine)) {
                    newAction.addLine(newLine);
                }
            }
            return newAction;
        }).collect(Collectors.toList());
    }

    public static String replaceStr(String str, Map<String , Object> m) {
        String prefix = "{{";
        String suffix = "}}";

        StringBuilder sb = new StringBuilder();

        int pIdx;
        int sIdx;
        while ((pIdx = str.indexOf(prefix)) != -1 && (sIdx = str.indexOf(suffix)) != -1 && pIdx < sIdx) {
            String key = str.substring(pIdx + 2, sIdx);

            boolean ignoreNotExist = false;
            if (key.charAt(key.length() - 1) == '!') {
                ignoreNotExist = true;
                key = key.substring(0, key.length() - 1);
            }

            String val;
            try {
                val = MapUtil.getMapValueByPath(m, key, ignoreNotExist);
                val = val == null ? "" : val;
            } catch (Exception e) {
                val = e.getMessage();
                e.printStackTrace();
            }
            sb.append(str, 0, pIdx).append(val);
            str = str.substring(sIdx + 2);
        }
        sb.append(str);
        return sb.toString();
    }

    public static String getDiffFileName(DiffDetail diffDetail) {
        File diffFile = new File(diffDetail.getFilePath());
        return diffFile.getName();
    }

    public static String genProgramDescription(GlobalYmlParseResult globalYmlParseResult, LocalYmlParseResult localYmlParseResult, DiffDetail diffDetail) {
        return globalYmlParseResult.getCitiProjectRelativePathPrefix() + "/"
                + localYmlParseResult.getProjectName() + "/"
                + diffDetail.getFilePath().replace(getDiffFileName(diffDetail), "").replaceAll("(.+)?/", "$1");
    }
}

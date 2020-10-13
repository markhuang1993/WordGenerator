package com.iisi;

import com.iisi.constants.DiffStatus;
import com.iisi.creator.ChangeFormCreator;
import com.iisi.creator.CheckoutFormCreator;
import com.iisi.parser.diff.DiffDetail;
import com.iisi.parser.diff.DiffTxtParser;
import com.iisi.parser.form.FormArgumentParser;
import com.iisi.parser.form.FormYmlParser;
import com.iisi.parser.form.model.argument.ArgumentParseResult;
import com.iisi.parser.form.model.argument.FormArgument;
import com.iisi.parser.form.model.yml.global.GlobalYmlParseResult;
import com.iisi.parser.form.model.yml.local.LocalYmlParseResult;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException, TemplateException, IllegalAccessException {
        final ArgumentParseResult argumentParseResult = FormArgumentParser.getInstance().parseArguments(args);
        if (!argumentParseResult.isParseSuccess()) {
            throw new IllegalArgumentException(argumentParseResult.getErrorMessage());
        }

        final FormArgument formArgument = argumentParseResult.getFormArgument();
        final File diffTxtFile = formArgument.getDiffTxtFile();
        final boolean isPat = diffTxtFile.getName().contains("Pat");

        final FormYmlParser ymlParser = FormYmlParser.getInstance();
        final GlobalYmlParseResult globalYmlParseResult = ymlParser.parseGlobalYml(formArgument.getGlobalConfigYmlFile());
        System.out.println(globalYmlParseResult);

        final LocalYmlParseResult localYmlParseResult = ymlParser.parsLocalYml(formArgument.getLocalConfigYmlFile());
        System.out.println(localYmlParseResult);

        final List<DiffDetail> diffDetails = DiffTxtParser.getInstance().parseDiffTxt(diffTxtFile);

        final String jenkinsJobExecutor = formArgument.getJenkinsJobExecutor();
        final File destDir = formArgument.getDestDir();

        if (!isPat) {
            new CheckoutFormCreator(globalYmlParseResult, localYmlParseResult, diffDetails).create(jenkinsJobExecutor, destDir);
        }

        final Map<String, List<DiffDetail>> diffDetailMap = categoryDiffDetails(diffDetails);

        final ChangeFormCreator changeFormCreator = new ChangeFormCreator(isPat, jenkinsJobExecutor, destDir, globalYmlParseResult, localYmlParseResult);
        changeFormCreator.create(diffDetailMap.get("default"), true, "changeForm.doc");
        changeFormCreator.create(diffDetailMap.get("sql"), true, "changeForm-SQL.doc");
    }

    private static Map<String, List<DiffDetail>> categoryDiffDetails(List<DiffDetail> diffDetails) {
        return diffDetails.stream().filter(diffDetail -> { // only need status A,M,D
                DiffStatus status = diffDetail.getStatus();
                return DiffStatus.A.equals(status) || DiffStatus.M.equals(status) || DiffStatus.D.equals(status);
            }).reduce(new HashMap<>(), (hm, diffDetail) -> {
                if (diffDetail.getFilePath().endsWith(".sql")) {
                    List<DiffDetail> l = hm.getOrDefault("sql", new ArrayList<>());
                    l.add(diffDetail);
                    hm.put("sql", l);
                } else {
                    List<DiffDetail> l = hm.getOrDefault("default", new ArrayList<>());
                    l.add(diffDetail);
                    hm.put("default", l);
                }
                return hm;
            }, (hm1, hm2) -> hm1);
    }
}


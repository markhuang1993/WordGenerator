package com.iisi.changeform;

import com.iisi.changeform.model.argument.ArgumentParseResult;
import com.iisi.changeform.model.argument.ChangeFormArgument;
import com.iisi.changeform.model.yml.global.GlobalYmlParseResult;
import com.iisi.changeform.model.yml.local.LocalYmlParseResult;
import com.iisi.constants.CheckboxString;
import com.iisi.generator.ChangeFormGenerator;
import com.iisi.generator.model.changeform.ChangeFormData;
import com.iisi.generator.model.changeform.ChangeFormTable;
import com.iisi.generator.model.changeform.ChangeFormTableRow;
import com.iisi.util.ResourceUtil;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

public class ChangeFormMain {
    public static void main(String[] args) throws IOException, TemplateException, IllegalAccessException {
        ArgumentParseResult argumentParseResult = ChangeFormArgumentParser.getInstance().parseArguments(args);
        if (!argumentParseResult.isParseSuccess()) {
            throw new IllegalArgumentException(argumentParseResult.getErrorMessage());
        }

        ChangeFormArgument changeFormArgument = argumentParseResult.getChangeFormArgument();

        ChangeFormYmlParser ymlParser = ChangeFormYmlParser.getInstance();
        GlobalYmlParseResult globalYmlParseResult = ymlParser.parseGlobalYml(changeFormArgument.getGlobalConfigYmlFile());
        System.out.println(globalYmlParseResult);

        LocalYmlParseResult localYmlParseResult = ymlParser.parsLocalYml(changeFormArgument.getLocalConfigYmlFile());
        System.out.println(localYmlParseResult);
        createChangeForm(changeFormArgument, globalYmlParseResult, localYmlParseResult);
    }

    private static void createChangeForm(
            ChangeFormArgument changeFormArgument,
            GlobalYmlParseResult globalYmlParseResult,
            LocalYmlParseResult localYmlParseResult) throws IllegalAccessException, TemplateException, IOException {
        ChangeFormGenerator changeFormGenerator = new ChangeFormGenerator();
        ChangeFormData formData = ChangeFormData.builder()
                .setPromoteToUat(CheckboxString.CHECKED.val())
                .setPromoteToProduction(CheckboxString.UNCHECKED.val())
                .setLacrNo(localYmlParseResult.getLacrNo())
                .setSystemApplication(localYmlParseResult.getSystemApplication())
                .setSubmitDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()))
                .setAction1("V01")
                .setProgrammerB64Png(ResourceUtil.getClassPathResource("image/mark.png"))
                .setSupervisorB64Png(ResourceUtil.getClassPathResource("image/huang.png"))
                .setVendorQmB64Png(ResourceUtil.getClassPathResource("image/handsome.png"))
                .setJavaAppTable(tableData())
                .build();

        changeFormGenerator.processFormTemplate(formData);
    }


    private static ChangeFormTable tableData() {
        ArrayList<ChangeFormTableRow> tableRows = new ArrayList<>();
        String s = Arrays.stream(new String[20]).map(x -> "q").collect(Collectors.joining(""));
        String s1 = Arrays.stream(new String[20]).map(x -> "w").collect(Collectors.joining(""));
        String s2 = Arrays.stream(new String[20]).map(x -> "e").collect(Collectors.joining(""));
        String s3 = Arrays.stream(new String[20]).map(x -> "t").collect(Collectors.joining(""));
        tableRows.add(new ChangeFormTableRow(s, s1, s2, s3, "asd", "qwe", "床前明月光\r\n疑似地上霜\r\n舉頭望明月\r\n低頭思故鄉"));
        for (int i = 0; i < 160; i++) {
            tableRows.add(new ChangeFormTableRow(
                    String.valueOf(i),
                    String.valueOf(i + 160),
                    String.valueOf(i + 320),
                    String.valueOf(i + 480),
                    String.valueOf(i + 640),
                    String.valueOf(i + 800),
                    String.valueOf(i + 960)
            ));
        }

        return new ChangeFormTable(tableRows);
    }


}


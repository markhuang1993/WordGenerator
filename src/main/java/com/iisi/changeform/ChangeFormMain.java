package com.iisi.changeform;

import com.iisi.changeform.model.argument.ArgumentParseResult;
import com.iisi.changeform.model.argument.ChangeFormArgument;
import com.iisi.changeform.model.yml.global.GlobalYmlParseResult;
import com.iisi.changeform.model.yml.local.LocalYmlParseResult;
import com.iisi.constants.CheckboxString;
import com.iisi.constants.DiffStatus;
import com.iisi.generator.ChangeFormGenerator;
import com.iisi.generator.model.changeform.ChangeFormData;
import com.iisi.generator.model.changeform.ChangeFormTable;
import com.iisi.generator.model.changeform.ChangeFormTableRow;
import com.iisi.parser.DiffDetail;
import com.iisi.parser.DiffTxtParser;
import com.iisi.util.ResourceUtil;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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

        List<DiffDetail> diffDetails = DiffTxtParser.getInstance().parseDiffTxt(changeFormArgument.getDiffTxtFile());

        createChangeForm(changeFormArgument, globalYmlParseResult, localYmlParseResult, diffDetails);
    }

    private static void createChangeForm(
            ChangeFormArgument changeFormArgument,
            GlobalYmlParseResult globalYmlParseResult,
            LocalYmlParseResult localYmlParseResult,
            List<DiffDetail> diffDetails) throws IllegalAccessException, TemplateException, IOException {
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
                .setJavaAppTable(javaTableData(globalYmlParseResult, localYmlParseResult, diffDetails))
                .build();

        changeFormGenerator.processFormTemplate(formData);
    }


    private static ChangeFormTable javaTableData(
            GlobalYmlParseResult globalYmlParseResult,
            LocalYmlParseResult localYmlParseResult,
            List<DiffDetail> diffDetails) {
        ArrayList<ChangeFormTableRow> tableRows = new ArrayList<>();

        AtomicInteger no = new AtomicInteger(1);
        diffDetails.stream()
                .filter(diffDetail -> {
                    DiffStatus status = diffDetail.getStatus();
                    return DiffStatus.A.equals(status) || DiffStatus.M.equals(status);
                })
                .forEach(diffDetail -> {
                    ChangeFormTableRow changeFormTableRow = new ChangeFormTableRow();
                    DiffStatus status = diffDetail.getStatus();
                    changeFormTableRow.setNewOld(status.equals(DiffStatus.A) ? "N" : "O");
                    changeFormTableRow.setNo(String.valueOf(no.getAndAdd(1)));
                    changeFormTableRow.setSystemID(localYmlParseResult.getSystemId());
                    changeFormTableRow.setProgramFileName(
                            globalYmlParseResult.getCitiProjectRelativePathPrefix() + "/"
                                    + localYmlParseResult.getProjectName() + "/"
                                    + diffDetail.getFilePath()
                    );
                    changeFormTableRow.setCheckIn("Y");
                    tableRows.add(changeFormTableRow);
                });
        ChangeFormTableRow changeFormTableRow = new ChangeFormTableRow();
        changeFormTableRow.setProgramExecutionName(localYmlParseResult.getWarName());
        tableRows.add(changeFormTableRow);
        return new ChangeFormTable(tableRows);
    }


}


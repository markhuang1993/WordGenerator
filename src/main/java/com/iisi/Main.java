package com.iisi;

import com.iisi.constants.CheckboxString;
import com.iisi.constants.DiffStatus;
import com.iisi.generator.ChangeFormGenerator;
import com.iisi.generator.CheckoutFormGenerator;
import com.iisi.generator.model.changeform.Action;
import com.iisi.generator.model.changeform.ChangeFormData;
import com.iisi.generator.model.changeform.ChangeFormTable;
import com.iisi.generator.model.changeform.ChangeFormTableRow;
import com.iisi.generator.model.checkoutform.CheckoutFormData;
import com.iisi.generator.model.checkoutform.CheckoutFormTable;
import com.iisi.generator.model.checkoutform.CheckoutFormTableRow;
import com.iisi.parser.diff.DiffDetail;
import com.iisi.parser.diff.DiffTxtParser;
import com.iisi.parser.form.FormArgumentParser;
import com.iisi.parser.form.FormYmlParser;
import com.iisi.parser.form.model.argument.ArgumentParseResult;
import com.iisi.parser.form.model.argument.FormArgument;
import com.iisi.parser.form.model.yml.global.GlobalYmlParseResult;
import com.iisi.parser.form.model.yml.local.LocalYmlParseResult;
import com.iisi.util.ResourceUtil;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) throws IOException, TemplateException, IllegalAccessException {
        ArgumentParseResult argumentParseResult = FormArgumentParser.getInstance().parseArguments(args);
        if (!argumentParseResult.isParseSuccess()) {
            throw new IllegalArgumentException(argumentParseResult.getErrorMessage());
        }

        FormArgument formArgument = argumentParseResult.getFormArgument();

        FormYmlParser ymlParser = FormYmlParser.getInstance();
        GlobalYmlParseResult globalYmlParseResult = ymlParser.parseGlobalYml(formArgument.getGlobalConfigYmlFile());
        System.out.println(globalYmlParseResult);

        LocalYmlParseResult localYmlParseResult = ymlParser.parsLocalYml(formArgument.getLocalConfigYmlFile());
        System.out.println(localYmlParseResult);

        List<DiffDetail> diffDetails = DiffTxtParser.getInstance().parseDiffTxt(formArgument.getDiffTxtFile());

        String jenkinsJobExecutor = formArgument.getJenkinsJobExecutor();
        File destDir = formArgument.getDestDir();

        createCheckoutForm(jenkinsJobExecutor, destDir, globalYmlParseResult, localYmlParseResult, diffDetails);
        createChangeForm(jenkinsJobExecutor, destDir, globalYmlParseResult, localYmlParseResult, diffDetails);
    }

    private static void createChangeForm(
            String jobExecutor,
            File destDir,
            GlobalYmlParseResult globalYmlParseResult,
            LocalYmlParseResult localYmlParseResult,
            List<DiffDetail> diffDetails) throws IllegalAccessException, TemplateException, IOException {
        File[] signatureImages = ResourceUtil.getSignatureImages(jobExecutor, globalYmlParseResult, localYmlParseResult);
        ChangeFormGenerator changeFormGenerator = new ChangeFormGenerator();
        ChangeFormData formData = ChangeFormData.builder()
                .setPromoteToUat(CheckboxString.CHECKED.val())
                .setPromoteToProduction(CheckboxString.UNCHECKED.val())
                .setLacrNo(System.getProperty("lacrNo"))
                .setSystemApplication(localYmlParseResult.getSystemApplication())
                .setSubmitDate(new SimpleDateFormat("yyyy/MM/dd").format(new Date()))
                .setActions(processFormActionValues(globalYmlParseResult.getActions(), localYmlParseResult))
                .setProgrammerB64Png(signatureImages[0])
                .setSupervisorB64Png(signatureImages[1])
                .setVendorQmB64Png(signatureImages[2])
                .setJavaAppTable(changeFormJavaTable(globalYmlParseResult, localYmlParseResult, diffDetails))
                .build();

        changeFormGenerator.processFormTemplate(formData, destDir);
    }

    private static List<Action> processFormActionValues(List<Action> actions, LocalYmlParseResult localYmlParseResult){
        return actions;
    }

    private static ChangeFormTable changeFormJavaTable(
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
                    String diffFileName = getDiffFileName(diffDetail);
                    changeFormTableRow.setProgramFileName(diffFileName);
                    changeFormTableRow.setProgramDescription(genProgramDescription(globalYmlParseResult, localYmlParseResult, diffDetail));
                    changeFormTableRow.setCheckIn("Y");
                    tableRows.add(changeFormTableRow);
                });
        ChangeFormTableRow changeFormTableRow = new ChangeFormTableRow();
        changeFormTableRow.setProgramExecutionName(localYmlParseResult.getWarName());
        tableRows.add(changeFormTableRow);
        return new ChangeFormTable(tableRows);
    }

    private static void createCheckoutForm(
            String jobExecutor,
            File destDir,
            GlobalYmlParseResult globalYmlParseResult,
            LocalYmlParseResult localYmlParseResult,
            List<DiffDetail> diffDetails) throws IllegalAccessException, TemplateException, IOException {
        File[] signatureImages = ResourceUtil.getSignatureImages(jobExecutor, globalYmlParseResult, localYmlParseResult);
        CheckoutFormGenerator checkoutFormGenerator = new CheckoutFormGenerator();
        CheckoutFormData formData = CheckoutFormData.builder()
                .setLacrNo(System.getProperty("lacrNo"))
                .setSystemApplication(localYmlParseResult.getSystemApplication())
                .setSubmitDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()))
                .setProgrammerB64Png(signatureImages[0])
                .setSupervisorB64Png(signatureImages[1])
                .setJavaAppTable(checkoutFormJavaTable(globalYmlParseResult, localYmlParseResult, diffDetails))
                .build();

        checkoutFormGenerator.processFormTemplate(formData, destDir);
    }

    private static CheckoutFormTable checkoutFormJavaTable(
            GlobalYmlParseResult globalYmlParseResult,
            LocalYmlParseResult localYmlParseResult,
            List<DiffDetail> diffDetails) {
        ArrayList<CheckoutFormTableRow> tableRows = new ArrayList<>();

        AtomicInteger no = new AtomicInteger(1);
        diffDetails.stream()
                .filter(diffDetail -> {
                    DiffStatus status = diffDetail.getStatus();
                    return DiffStatus.M.equals(status);
                })
                .forEach(diffDetail -> {
                    CheckoutFormTableRow checkoutFormTableRow = new CheckoutFormTableRow();
                    checkoutFormTableRow.setNo(String.valueOf(no.getAndAdd(1)));
                    checkoutFormTableRow.setSystemID(localYmlParseResult.getSystemId());
                    String diffFileName = getDiffFileName(diffDetail);
                    checkoutFormTableRow.setProgramFileName(diffFileName);
                    checkoutFormTableRow.setProgramDescription(genProgramDescription(globalYmlParseResult, localYmlParseResult, diffDetail));
                    tableRows.add(checkoutFormTableRow);
                });
        return new CheckoutFormTable(tableRows);
    }

    private static String getDiffFileName(DiffDetail diffDetail) {
        File diffFile = new File(diffDetail.getFilePath());
        return diffFile.getName();
    }

    private static String genProgramDescription(GlobalYmlParseResult globalYmlParseResult, LocalYmlParseResult localYmlParseResult, DiffDetail diffDetail) {
        return globalYmlParseResult.getCitiProjectRelativePathPrefix() + "/"
                + localYmlParseResult.getProjectName() + "/"
                + diffDetail.getFilePath().replace(getDiffFileName(diffDetail), "").replaceAll("(.+)?/", "$1");
    }


}


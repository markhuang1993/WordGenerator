package com.iisi;

import com.iisi.constants.DiffStatus;
import com.iisi.defaultform.DefaultChangeFormProvider;
import com.iisi.generator.ChangeFormGenerator;
import com.iisi.generator.CheckoutFormGenerator;
import com.iisi.generator.model.changeform.*;
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
import com.iisi.util.MapUtil;
import com.iisi.util.ResourceUtil;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException, TemplateException, IllegalAccessException {
        ArgumentParseResult argumentParseResult = FormArgumentParser.getInstance().parseArguments(args);
        if (!argumentParseResult.isParseSuccess()) {
            throw new IllegalArgumentException(argumentParseResult.getErrorMessage());
        }

        FormArgument formArgument = argumentParseResult.getFormArgument();
        File diffTxtFile = formArgument.getDiffTxtFile();
        boolean isPat = diffTxtFile.getName().contains("Pat");

        FormYmlParser ymlParser = FormYmlParser.getInstance();
        GlobalYmlParseResult globalYmlParseResult = ymlParser.parseGlobalYml(formArgument.getGlobalConfigYmlFile(), isPat);
        System.out.println(globalYmlParseResult);

        LocalYmlParseResult localYmlParseResult = ymlParser.parsLocalYml(formArgument.getLocalConfigYmlFile(), isPat);
        System.out.println(localYmlParseResult);

        List<DiffDetail> diffDetails = DiffTxtParser.getInstance().parseDiffTxt(diffTxtFile);

        String jenkinsJobExecutor = formArgument.getJenkinsJobExecutor();
        File destDir = formArgument.getDestDir();

        if (!isPat) {
            createCheckoutForm(jenkinsJobExecutor, destDir, globalYmlParseResult, localYmlParseResult, diffDetails);
        }
        createChangeForm(isPat, jenkinsJobExecutor, destDir, globalYmlParseResult, localYmlParseResult, diffDetails);
        createSqlChangeForm(isPat, jenkinsJobExecutor, destDir, globalYmlParseResult, localYmlParseResult, diffDetails);
    }

    private static void createChangeForm(
            boolean isPat,
            String jobExecutor,
            File destDir,
            GlobalYmlParseResult globalYmlParseResult,
            LocalYmlParseResult localYmlParseResult,
            List<DiffDetail> diffDetails) throws IllegalAccessException, TemplateException, IOException {
        File[] signatureImages = ResourceUtil.getSignatureImages(jobExecutor, globalYmlParseResult, localYmlParseResult);
        ChangeFormGenerator changeFormGenerator = new ChangeFormGenerator();
        List<Action> actions = isPat ? localYmlParseResult.getPatActions() : localYmlParseResult.getUatActions();
        if (actions == null || actions.size() == 0) {
            actions = isPat ? globalYmlParseResult.getPatActions() : globalYmlParseResult.getUatActions();
        }

        diffDetails = diffDetails.stream().filter(diffDetail -> { // only need status A and M
            DiffStatus status = diffDetail.getStatus();
            return DiffStatus.A.equals(status) || DiffStatus.M.equals(status);
        }).filter(diffDetail -> !diffDetail.getFilePath().endsWith(".sql")).collect(Collectors.toList());

        ChangeFormData formData = ChangeFormData.builder()
                .setPat(isPat)
                .setLacrNo(System.getProperty("lacrNo"))
                .setSystemApplication(localYmlParseResult.getSystemApplication())
                .setSubmitDate(new SimpleDateFormat("yyyy/MM/dd").format(new Date()))
                .setActions(processFormActionValues(actions, localYmlParseResult))
                .setProgrammerB64Png(signatureImages[0])
                .setSupervisorB64Png(signatureImages[1])
                .setVendorQmB64Png(signatureImages[2])
                .setWindowsJavaAppTable(new DefaultChangeFormProvider().defaultWindowsJavaTable(6))
                .setLinuxJavaAppTable(linuxChangeFormJavaTable(globalYmlParseResult, localYmlParseResult, diffDetails))
                .build();

        File docFile = changeFormGenerator.processFormTemplate(formData, destDir, "changeForm.doc");
        System.out.println("change form doc is created at:" + docFile.getAbsolutePath());
    }

    private static void createSqlChangeForm(
            boolean isPat,
            String jobExecutor,
            File destDir,
            GlobalYmlParseResult globalYmlParseResult,
            LocalYmlParseResult localYmlParseResult,
            List<DiffDetail> diffDetails) throws IllegalAccessException, TemplateException, IOException {
        File[] signatureImages = ResourceUtil.getSignatureImages(jobExecutor, globalYmlParseResult, localYmlParseResult);
        ChangeFormGenerator changeFormGenerator = new ChangeFormGenerator();
        List<Action> actions = localYmlParseResult.getSqlActions();
        if (actions == null || actions.size() == 0) {
            actions = globalYmlParseResult.getSqlActions();
        }

        diffDetails = diffDetails.stream().filter(diffDetail -> { // only need status A and M
            DiffStatus status = diffDetail.getStatus();
            return DiffStatus.A.equals(status) || DiffStatus.M.equals(status);
        }).filter(diffDetail -> diffDetail.getFilePath().endsWith(".sql")).collect(Collectors.toList());

        if (diffDetails.size() == 0) {
            System.out.println("no sql change, skip create sql change form");
            return;
        }

        ChangeFormData formData = ChangeFormData.builder()
                .setPat(isPat)
                .setLacrNo(System.getProperty("lacrNo"))
                .setSystemApplication(localYmlParseResult.getSystemApplication())
                .setSubmitDate(new SimpleDateFormat("yyyy/MM/dd").format(new Date()))
                .setActions(processFormActionValues(actions, localYmlParseResult))
                .setProgrammerB64Png(signatureImages[0])
                .setSupervisorB64Png(signatureImages[1])
                .setVendorQmB64Png(signatureImages[2])
                .setWindowsJavaAppTable(new DefaultChangeFormProvider().defaultWindowsJavaTable(6))
                .setLinuxJavaAppTable(linuxChangeFormJavaTable(globalYmlParseResult, localYmlParseResult, diffDetails))
                .build();

        File docFile = changeFormGenerator.processFormTemplate(formData, destDir, "changeForm-SQL.doc");
        System.out.println("sql change form doc is created at:" + docFile.getAbsolutePath());
    }

    private static List<Action> processFormActionValues(List<Action> actions, LocalYmlParseResult localYmlParseResult) {
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

    private static String replaceStr(String str, Map<String , Object> m) {
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

    private static ChangeFormTable windowsChangeFormJavaTable(
            boolean isPat,
            GlobalYmlParseResult globalYmlParseResult,
            LocalYmlParseResult localYmlParseResult,
            List<DiffDetail> diffDetails) {

        AtomicInteger no = new AtomicInteger(1);
        List<ChangeFormTableRow> tableRows = diffDetails.stream()
                .map(diffDetail -> {
                    WindowsChangeFormTableRow changeFormTableRow = new WindowsChangeFormTableRow();
                    DiffStatus status = diffDetail.getStatus();
                    changeFormTableRow.setNewOld(status.equals(DiffStatus.A) ? "N" : "O");
                    changeFormTableRow.setNo(String.valueOf(no.getAndAdd(1)));
                    changeFormTableRow.setSystemID(localYmlParseResult.getSystemId());
                    String diffFileName = getDiffFileName(diffDetail);
                    changeFormTableRow.setProgramFileName(diffFileName);
                    changeFormTableRow.setProgramDescription(genProgramDescription(globalYmlParseResult, localYmlParseResult, diffDetail));
                    changeFormTableRow.setCheckIn("Y");
                    return changeFormTableRow;
                }).collect(Collectors.toList());
        WindowsChangeFormTableRow changeFormTableRow = new WindowsChangeFormTableRow();
        changeFormTableRow.setProgramExecutionName(localYmlParseResult.getWarName());
        if (isPat) {
            changeFormTableRow.setProgramDescription("apacctltwap145Node01Cell\\" + localYmlParseResult.getWarName() + ".ear");
        }
        tableRows.add(changeFormTableRow);
        return new ChangeFormTable(tableRows);
    }

    private static ChangeFormTable linuxChangeFormJavaTable(
            GlobalYmlParseResult globalYmlParseResult,
            LocalYmlParseResult localYmlParseResult,
            List<DiffDetail> diffDetails) {

        AtomicInteger no = new AtomicInteger(1);
        List<ChangeFormTableRow> tableRows = diffDetails.stream()
                .map(diffDetail -> {
                    LinuxChangeFormTableRow changeFormTableRow = new LinuxChangeFormTableRow();
                    DiffStatus status = diffDetail.getStatus();
                    changeFormTableRow.setNo(String.valueOf(no.getAndAdd(1)));
                    changeFormTableRow.setNewOld(status.equals(DiffStatus.A) ? "N" : "O");
                    changeFormTableRow.setCheckIn("Y");
                    changeFormTableRow.setProgramDescription("");
                    changeFormTableRow.setProgramFileName(getDiffFileName(diffDetail));
                    changeFormTableRow.setFromDir(genProgramDescription(globalYmlParseResult, localYmlParseResult, diffDetail));
                    return changeFormTableRow;
                }).collect(Collectors.toList());
        return new ChangeFormTable(tableRows);
    }

    private static void createCheckoutForm(
            String jobExecutor,
            File destDir,
            GlobalYmlParseResult globalYmlParseResult,
            LocalYmlParseResult localYmlParseResult,
            List<DiffDetail> diffDetails) throws IllegalAccessException, TemplateException, IOException {
        File[] signatureImages = ResourceUtil.getSignatureImages(jobExecutor, globalYmlParseResult, localYmlParseResult);
        CheckoutFormData formData = CheckoutFormData.builder()
                .setLacrNo(System.getProperty("lacrNo"))
                .setSystemApplication(localYmlParseResult.getSystemApplication())
                .setSubmitDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()))
                .setProgrammerB64Png(signatureImages[0])
                .setSupervisorB64Png(signatureImages[1])
                .setJavaAppTable(checkoutFormJavaTable(globalYmlParseResult, localYmlParseResult, diffDetails))
                .build();

        File docFile = new CheckoutFormGenerator().processFormTemplate(formData, destDir, "checkoutForm.doc");
        System.out.println("checkout form doc is created at:" + docFile.getAbsolutePath());
    }

    private static CheckoutFormTable checkoutFormJavaTable(
            GlobalYmlParseResult globalYmlParseResult,
            LocalYmlParseResult localYmlParseResult,
            List<DiffDetail> diffDetails) {

        AtomicInteger no = new AtomicInteger(1);
        List<CheckoutFormTableRow> tableRows = diffDetails.stream()
                .filter(diffDetail -> {
                    DiffStatus status = diffDetail.getStatus();
                    return DiffStatus.M.equals(status);
                })
                .map(diffDetail -> {
                    CheckoutFormTableRow checkoutFormTableRow = new CheckoutFormTableRow();
                    checkoutFormTableRow.setNo(String.valueOf(no.getAndAdd(1)));
                    checkoutFormTableRow.setSystemID(localYmlParseResult.getSystemId());
                    String diffFileName = getDiffFileName(diffDetail);
                    checkoutFormTableRow.setProgramFileName(diffFileName);
                    checkoutFormTableRow.setProgramDescription(genProgramDescription(globalYmlParseResult, localYmlParseResult, diffDetail));
                    return checkoutFormTableRow;
                }).collect(Collectors.toList());
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


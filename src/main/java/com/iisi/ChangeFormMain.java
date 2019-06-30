package com.iisi;

import com.iisi.parser.form.FormArgumentParser;
import com.iisi.parser.form.FormYmlParser;
import com.iisi.parser.form.model.argument.ArgumentParseResult;
import com.iisi.parser.form.model.argument.FormArgument;
import com.iisi.parser.form.model.yml.global.GlobalYmlParseResult;
import com.iisi.parser.form.model.yml.local.LocalYmlParseResult;
import com.iisi.constants.CheckboxString;
import com.iisi.constants.DiffStatus;
import com.iisi.generator.ChangeFormGenerator;
import com.iisi.generator.model.changeform.ChangeFormData;
import com.iisi.generator.model.changeform.ChangeFormTable;
import com.iisi.generator.model.changeform.ChangeFormTableRow;
import com.iisi.parser.diff.DiffDetail;
import com.iisi.parser.diff.DiffTxtParser;
import com.iisi.util.ResourceUtil;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class ChangeFormMain {
    public static void main(String[] args) {
        try {
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

            createChangeForm(formArgument.getJenkinsJobExecutor(), globalYmlParseResult, localYmlParseResult, diffDetails);
        } catch (Exception e) {
            System.out.println("Something error, changeForm not generated");
            e.printStackTrace();
        }
    }

    private static void createChangeForm(
            String jobExecutor,
            GlobalYmlParseResult globalYmlParseResult,
            LocalYmlParseResult localYmlParseResult,
            List<DiffDetail> diffDetails) throws IllegalAccessException, TemplateException, IOException {
        File[] signatureImages = getSignatureImages(jobExecutor, globalYmlParseResult, localYmlParseResult);
        ChangeFormGenerator changeFormGenerator = new ChangeFormGenerator();
        ChangeFormData formData = ChangeFormData.builder()
                .setPromoteToUat(CheckboxString.CHECKED.val())
                .setPromoteToProduction(CheckboxString.UNCHECKED.val())
                .setLacrNo(localYmlParseResult.getLacrNo())
                .setSystemApplication(localYmlParseResult.getSystemApplication())
                .setSubmitDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()))
                .setAction1("V01")
                .setProgrammerB64Png(signatureImages[0])
                .setSupervisorB64Png(signatureImages[1])
                .setVendorQmB64Png(signatureImages[2])
                .setJavaAppTable(javaTableData(globalYmlParseResult, localYmlParseResult, diffDetails))
                .build();

        changeFormGenerator.processFormTemplate(formData);
    }

    @SuppressWarnings("Duplicates")
    private static File[] getSignatureImages(
            String jobExecutor,
            GlobalYmlParseResult globalYmlParseResult,
            LocalYmlParseResult localYmlParseResult) {

        File signatureImgDir = globalYmlParseResult.getSignatureImgDir();

        File programmerSing = getSignaturePng(new String[]{jobExecutor}, signatureImgDir);
        if (programmerSing == null) {
            String owner = localYmlParseResult.getOwner();
            String[] owners = owner.split(",");
            programmerSing = getSignaturePng(owners, signatureImgDir);
            if (programmerSing == null) {
                programmerSing = ResourceUtil.getClassPathResource("image/unknown.png");
            }
        }

        String supervisor = localYmlParseResult.getSupervisor();
        String[] supervisors = supervisor.split(",");
        File supervisorSing = getSignaturePng(supervisors, signatureImgDir);
        if (supervisorSing == null) {
            supervisorSing = ResourceUtil.getClassPathResource("image/unknown.png");
        }

        String vendorQm = localYmlParseResult.getvendorQm();
        String[] vendorQms = vendorQm.split(",");
        File vendorQmSing = getSignaturePng(vendorQms, signatureImgDir);
        if (vendorQmSing == null) {
            vendorQmSing = ResourceUtil.getClassPathResource("image/unknown.png");
        }

        return new File[]{programmerSing, supervisorSing, vendorQmSing};
    }

    private static File getSignaturePng(String[] names, File signatureImgDir) {
        List<String> nameList = Arrays.stream(names).map(String::trim).collect(Collectors.toList());
        Collections.shuffle(nameList);
        File[] imgs = signatureImgDir.listFiles(f -> f.getName().matches(".*?\\.png$"));
        if (imgs == null) {
            return null;
        }
        for (String name : nameList) {
            for (File img : imgs) {
                if (img.getName().contains(name)) {
                    return img;
                }
            }
        }
        return null;
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
                    File diffFile = new File(diffDetail.getFilePath());
                    String diffFileName = diffFile.getName();
                    changeFormTableRow.setProgramFileName(diffFileName);
                    changeFormTableRow.setProgramDescription(
                            globalYmlParseResult.getCitiProjectRelativePathPrefix() + "/"
                                    + localYmlParseResult.getProjectName() + "/"
                                    + diffDetail.getFilePath().replace(diffFileName, "")
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


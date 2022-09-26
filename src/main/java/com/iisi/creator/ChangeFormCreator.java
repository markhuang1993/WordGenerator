package com.iisi.creator;

import com.iisi.constants.DiffStatus;
import com.iisi.defaultform.DefaultChangeFormProvider;
import com.iisi.generator.ChangeFormGenerator;
import com.iisi.generator.model.changeform.*;
import com.iisi.parser.diff.DiffDetail;
import com.iisi.parser.form.model.yml.global.GlobalYmlParseResult;
import com.iisi.parser.form.model.yml.local.LocalYmlParseResult;
import com.iisi.util.FormUtil;
import com.iisi.util.ResourceUtil;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class ChangeFormCreator {
    private final boolean isPat;
    private final String jobExecutor;
    private final File destDir;
    private final GlobalYmlParseResult globalYmlParseResult;
    private final LocalYmlParseResult localYmlParseResult;

    public ChangeFormCreator(boolean isPat, String jobExecutor, File destDir, GlobalYmlParseResult globalYmlParseResult, LocalYmlParseResult localYmlParseResult) {
        this.isPat = isPat;
        this.jobExecutor = jobExecutor;
        this.destDir = destDir;
        this.globalYmlParseResult = globalYmlParseResult;
        this.localYmlParseResult = localYmlParseResult;
    }

    public void create(List<DiffDetail> diffDetails, boolean isLinuxForm, String formName) throws IllegalAccessException, TemplateException, IOException {
        final ChangeFormGenerator changeFormGenerator = new ChangeFormGenerator();
        List<Action> actions = isPat ? localYmlParseResult.getPatActions() : localYmlParseResult.getUatActions();
        if (actions == null || actions.size() == 0) {
            actions = isPat ? globalYmlParseResult.getPatActions() : globalYmlParseResult.getUatActions();
        }

        final File[] signatureImages = ResourceUtil.getSignatureImages(jobExecutor, globalYmlParseResult, localYmlParseResult);
        final List<Action> actionList = FormUtil.processFormActionValues(actions, localYmlParseResult);
        final ChangeFormData formData = getChangeFormData(isLinuxForm, signatureImages, actionList, diffDetails);

        final File docFile = changeFormGenerator.processFormTemplate(formData, destDir, formName);
        System.out.printf("change form doc is created at:%s%n", docFile.getAbsolutePath());
    }

    private ChangeFormData getChangeFormData(
            boolean isLinuxForm,
            File[] signatureImages,
            List<Action> actionList,
            List<DiffDetail> diffDetails) {

        ChangeFormTable windowsChangeFormTable;
        ChangeFormTable linuxChangeFormTable;
        final DefaultChangeFormProvider changeFormProvider = new DefaultChangeFormProvider();
        if (isLinuxForm) {
            windowsChangeFormTable = changeFormProvider.windowsJavaTable(6);
            linuxChangeFormTable = getLinuxJavaTable(diffDetails);
        } else {
            windowsChangeFormTable = getWindowsJavaTable(diffDetails);
            linuxChangeFormTable = changeFormProvider.linuxJavaTable(6);
        }

        return ChangeFormData.builder()
                .setPat(isPat)
                .setLacrNo(System.getProperty("lacrNo"))
                .setSystemApplication(localYmlParseResult.getSystemApplication())
                .setSubmitDate(new SimpleDateFormat("yyyy/MM/dd").format(new Date()))
                .setActions(actionList)
                .setProgrammerB64Png(signatureImages[0])
                .setSupervisorB64Png(signatureImages[1])
                .setVendorQmB64Png(signatureImages[2])
                .setWindowsJavaAppTable(windowsChangeFormTable)
                .setLinuxJavaAppTable(linuxChangeFormTable)
                .build();
    }


    private ChangeFormTable getWindowsJavaTable(List<DiffDetail> diffDetails) {
        final AtomicInteger no = new AtomicInteger(1);
        final List<ChangeFormTableRow> tableRows = diffDetails.stream()
                .map(diffDetail -> {
                    WindowsChangeFormTableRow changeFormTableRow = new WindowsChangeFormTableRow();
                    DiffStatus status = diffDetail.getStatus();
                    changeFormTableRow.setFileStat(status.getFileStat());
                    changeFormTableRow.setNo(String.valueOf(no.getAndAdd(1)));
                    changeFormTableRow.setSystemID(localYmlParseResult.getSystemId());
                    String diffFileName = FormUtil.getDiffFileName(diffDetail);
                    changeFormTableRow.setProgramFileName(diffFileName);
                    changeFormTableRow.setProgramDescription(FormUtil.genProgramDescription(globalYmlParseResult, localYmlParseResult, diffDetail));
                    changeFormTableRow.setCheckIn("Y");
                    return changeFormTableRow;
                }).collect(Collectors.toList());
        final WindowsChangeFormTableRow changeFormTableRow = new WindowsChangeFormTableRow();
        changeFormTableRow.setProgramExecutionName(localYmlParseResult.getWarName());
        if (isPat) {
            changeFormTableRow.setProgramDescription("apacctltwap145Node01Cell\\" + localYmlParseResult.getWarName() + ".ear");
        }
        tableRows.add(changeFormTableRow);
        return new ChangeFormTable(tableRows);
    }

    private ChangeFormTable getLinuxJavaTable(List<DiffDetail> diffDetails) {
        final AtomicInteger no = new AtomicInteger(1);
        final List<ChangeFormTableRow> tableRows = diffDetails.stream()
                .map(diffDetail -> {
                    LinuxChangeFormTableRow changeFormTableRow = new LinuxChangeFormTableRow();
                    DiffStatus status = diffDetail.getStatus();
                    changeFormTableRow.setNo(String.valueOf(no.getAndAdd(1)));
                    changeFormTableRow.setFileStat(status.getFileStat());
                    changeFormTableRow.setCheckIn("Y");
                    changeFormTableRow.setProgramDescription("");
                    changeFormTableRow.setProgramFileName(FormUtil.getDiffFileName(diffDetail));
                    changeFormTableRow.setToDir(FormUtil.genProgramDescription(globalYmlParseResult, localYmlParseResult, diffDetail));
                    return changeFormTableRow;
                }).collect(Collectors.toList());
        return new ChangeFormTable(tableRows);
    }
}

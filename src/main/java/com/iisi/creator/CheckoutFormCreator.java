package com.iisi.creator;

import com.iisi.constants.DiffStatus;
import com.iisi.generator.CheckoutFormGenerator;
import com.iisi.generator.model.checkoutform.CheckoutFormData;
import com.iisi.generator.model.checkoutform.CheckoutFormTable;
import com.iisi.generator.model.checkoutform.CheckoutFormTableRow;
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

public class CheckoutFormCreator {
    private final GlobalYmlParseResult globalYmlParseResult;
    private final LocalYmlParseResult localYmlParseResult;
    private final List<DiffDetail> diffDetails;

    public CheckoutFormCreator(GlobalYmlParseResult globalYmlParseResult, LocalYmlParseResult localYmlParseResult, List<DiffDetail> diffDetails) {
        this.globalYmlParseResult = globalYmlParseResult;
        this.localYmlParseResult = localYmlParseResult;
        this.diffDetails = diffDetails;
    }

    public void create(String jobExecutor, File destDir) throws IllegalAccessException, TemplateException, IOException {
        final File[] signatureImages = ResourceUtil.getSignatureImages(jobExecutor, globalYmlParseResult, localYmlParseResult);
        final CheckoutFormData formData = CheckoutFormData.builder()
                .setLacrNo(System.getProperty("lacrNo"))
                .setSystemApplication(localYmlParseResult.getSystemApplication())
                .setSubmitDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()))
                .setProgrammerB64Png(signatureImages[0])
                .setSupervisorB64Png(signatureImages[1])
                .setJavaAppTable(getJavaTable())
                .build();

        final File docFile = new CheckoutFormGenerator().processFormTemplate(formData, destDir, "checkoutForm.doc");
        System.out.printf("checkout form doc is created at:%s%n", docFile.getAbsolutePath());
    }

    private CheckoutFormTable getJavaTable() {

        final AtomicInteger no = new AtomicInteger(1);
        final List<CheckoutFormTableRow> tableRows = diffDetails.stream()
                .filter(diffDetail -> {
                    DiffStatus status = diffDetail.getStatus();
                    return DiffStatus.M.equals(status);
                })
                .map(diffDetail -> {
                    CheckoutFormTableRow checkoutFormTableRow = new CheckoutFormTableRow();
                    checkoutFormTableRow.setNo(String.valueOf(no.getAndAdd(1)));
                    checkoutFormTableRow.setSystemID(localYmlParseResult.getSystemId());
                    String diffFileName = FormUtil.getDiffFileName(diffDetail);
                    checkoutFormTableRow.setProgramFileName(diffFileName);
                    checkoutFormTableRow.setProgramDescription(FormUtil.genProgramDescription(globalYmlParseResult, localYmlParseResult, diffDetail));
                    return checkoutFormTableRow;
                }).collect(Collectors.toList());
        return new CheckoutFormTable(tableRows);
    }
}

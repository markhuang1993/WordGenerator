package com.iisi.util;

import com.iisi.parser.form.model.yml.global.GlobalYmlParseResult;
import com.iisi.parser.form.model.yml.local.LocalYmlParseResult;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class ResourceUtil {
    private ResourceUtil() {
        throw new AssertionError();
    }

    public static File getClassPathResource(String resourcePath) {
        URL resource = ResourceUtil.class.getClassLoader().getResource(resourcePath);
        if (resource == null) return null;
        File file = new File(resource.getFile());
        return file.exists() ? file : null;
    }

    @SuppressWarnings("Duplicates")
    public static File[] getSignatureImages(
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
}

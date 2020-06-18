import com.iisi.constants.CheckboxString;
import com.iisi.generator.ChangeFormGenerator;
import com.iisi.generator.model.changeform.Action;
import com.iisi.generator.model.changeform.ChangeFormData;
import com.iisi.generator.model.changeform.ChangeFormTable;
import com.iisi.generator.model.changeform.ChangeFormTableRow;
import com.iisi.util.ResourceUtil;
import freemarker.template.TemplateException;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ChangeFormGeneratorTest {

    @Test
    public void createDocTest() throws IllegalAccessException, TemplateException, IOException {
        ChangeFormGenerator changeFormGenerator = new ChangeFormGenerator();
        List<Action> actionList = new ArrayList<>();
        actionList.add(new Action("yo ho"));
        actionList.add(new Action("i am", "mark"));
        ChangeFormData formData = ChangeFormData.builder()
                .setPat(true)
                .setLacrNo("37037")
                .setSystemApplication("sapp")
                .setSystemApplication("2019-06-22")
                .setSubmitDate("asdwq")
                .setLacrCoordinator("77777")
                .setLibrarian("shxt")
                .setProcessDate("2019-06-23")
                .setActions(actionList)
                .setProgrammerB64Png(ResourceUtil.getClassPathResource("image/mark.png"))
                .setSupervisorB64Png(ResourceUtil.getClassPathResource("image/huang.png"))
                .setVendorQmB64Png(ResourceUtil.getClassPathResource("image/handsome.png"))
                .setJavaAppTable(this.tableData())
                .build();
        changeFormGenerator.processFormTemplate(formData, new File(System.getProperty("user.dir")));
    }

    private ChangeFormTable tableData() {
        ArrayList<ChangeFormTableRow> tableRows = new ArrayList<>();
        String s = Arrays.stream(new String[10]).map(x -> "q").collect(Collectors.joining(""));
        String s1 = Arrays.stream(new String[10]).map(x -> "w").collect(Collectors.joining(""));
        String s2 = Arrays.stream(new String[10]).map(x -> "e").collect(Collectors.joining(""));
        String s3 = Arrays.stream(new String[10]).map(x -> "r").collect(Collectors.joining(""));
        tableRows.add(new ChangeFormTableRow(s, s1, s2, s3, "asd", "qwe", "床前明月光\r\n疑似地上霜\r\n舉頭望明月\r\n低頭思故鄉"));
        for (int i = 0; i < 16; i++) {
            tableRows.add(new ChangeFormTableRow(
                    String.valueOf(i),
                    String.valueOf(i + 16),
                    String.valueOf(i + 32),
                    String.valueOf(i + 48),
                    String.valueOf(i + 64),
                    String.valueOf(i + 80),
                    String.valueOf(i + 96)
            ));
        }

        return new ChangeFormTable(tableRows);
    }
}

import com.iisi.constants.CheckboxString;
import com.iisi.generator.ChangeFormGenerator;
import com.iisi.generator.model.changeform.ChangeFormData;
import com.iisi.generator.model.checkoutform.CheckoutFormTable;
import com.iisi.generator.model.checkoutform.CheckoutFormTableRow;
import com.iisi.util.ResourceUtil;
import freemarker.template.TemplateException;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ChangeFormGeneratorTest {

    @Test
    public void createDocTest() throws IllegalAccessException, TemplateException, IOException {
        ChangeFormGenerator changeFormGenerator = new ChangeFormGenerator();
        ChangeFormData formData = ChangeFormData.builder()
                .setPromoteToUat(CheckboxString.CHECKED.val())
                .setPromoteToProduction(CheckboxString.UNCHECKED.val())
                .setLacrNo("37037")
                .setSystemApplication("sapp")
                .setSystemApplication("2019-06-22")
                .setSubmitDate("asdwq")
                .setLacrCoordinator("77777")
                .setLibrarian("shxt")
                .setProcessDate("2019-06-23")
                .setAction1("Hello every one")
                .setAction2("i am here")
                .setAction3("nice to meet yours")
                .setProgrammerB64Png(ResourceUtil.getClassPathResource("image/mark.png"))
                .setSupervisorB64Png(ResourceUtil.getClassPathResource("image/huang.png"))
                .setVendorQmB64Png(ResourceUtil.getClassPathResource("image/handsome.png"))
                .build();
        changeFormGenerator.processFormTemplate(formData);
    }

    private CheckoutFormTable tableData() {
        ArrayList<CheckoutFormTableRow> tableRows = new ArrayList<>();
        String s = Arrays.stream(new String[50]).map(x -> "q").collect(Collectors.joining(""));
        String s1 = Arrays.stream(new String[50]).map(x -> "w").collect(Collectors.joining(""));
        String s2 = Arrays.stream(new String[50]).map(x -> "e").collect(Collectors.joining(""));
        String s3 = Arrays.stream(new String[50]).map(x -> "t").collect(Collectors.joining(""));
        tableRows.add(new CheckoutFormTableRow(s, s1, s2, s3, "床前明月光\r\n疑似地上霜\r\n舉頭望明月\r\n低頭思故鄉"));
        for (int i = 0; i < 160; i++) {
            tableRows.add(new CheckoutFormTableRow(
                    String.valueOf(i),
                    String.valueOf(i + 160),
                    String.valueOf(i + 320),
                    String.valueOf(i + 480),
                    String.valueOf(i + 640)
            ));
        }

        return new CheckoutFormTable(tableRows);
    }
}

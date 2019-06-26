package com.iisi.changeform;

import com.iisi.changeform.model.argument.ArgumentParseResult;
import com.iisi.changeform.model.argument.ChangeFormArgument;
import com.iisi.changeform.model.yml.global.GlobalYmlParseResult;
import com.iisi.changeform.model.yml.local.LocalYmlParseResult;

import java.io.IOException;

public class ChangeFormMain {
    public static void main(String[] args) throws IOException {
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
        System.out.println(123);
    }
}


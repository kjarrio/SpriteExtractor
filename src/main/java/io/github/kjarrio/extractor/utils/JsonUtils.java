package io.github.kjarrio.extractor.utils;

import com.google.gson.JsonParser;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;

public class JsonUtils {

    public static Boolean isJsonFile(File jsonFile) {
        try {
            return FormatUtils.hasExtension(jsonFile, "json") && isValidJson(FileUtils.readFileToString(jsonFile, "UTF-8"));
        } catch (IOException e) {
            return false;
        }
    }

    public static Boolean isValidJson(String contents) {

        if (contents.isEmpty() || !contents.contains("{") && !contents.contains("[")) {
            return false;
        }

        try {
            JsonParser.parseString(contents);
        } catch (Exception e) {
            return false;
        }

        return true;

    }
}

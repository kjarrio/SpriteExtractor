package io.github.kjarrio.extractor.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import java.io.File;

public class JsonUtils {

    public static Boolean isJsonFile(File jsonFile) {
        return FormatUtils.hasExtension(jsonFile, "json");
    }

    public static Boolean isValidJson(String contents) {

        if (contents.isEmpty()) {
            return false;
        }

        if (!contents.contains("{") && !contents.contains("[")) {
            return false;
        }

        JsonParser parser = new JsonParser();
        Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

        try {
            parser.parse(gson.toJson(parser.parse(contents)));
        } catch (Exception e) {
            return false;
        }

        return true;

    }
}

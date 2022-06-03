package io.github.kjarrio.extractor.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import org.apache.commons.io.FileUtils;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Boolean validateSchema(File jsonFile, String schemaFile) {
        String schemaContents = FSUtils.readResourceFile(schemaFile);
        String jsonContents = FSUtils.readFile(jsonFile);
        return validateSchema(schemaContents, jsonContents);
    }

    public static Boolean isJsonFile(File jsonFile) {
        try {
            return FSUtils.hasExt(jsonFile, "json") && isValidJson(FileUtils.readFileToString(jsonFile, "UTF-8"));
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

    public static List<Integer> getIntArray(JsonArray jsonArray) {
        List<Integer> intArray = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            intArray.add(jsonArray.get(i).getAsInt());
        }
        return intArray;
    }

    public static Boolean validateSchema(String schemaContents, String jsonContents) {
        try  {
            JSONObject rawSchema = new JSONObject(new JSONTokener(schemaContents));
            Schema schema = SchemaLoader.load(rawSchema);
            schema.validate(new JSONObject(jsonContents)); // throws a ValidationException if not valid
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}

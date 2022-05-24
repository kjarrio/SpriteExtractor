package io.github.kjarrio.extractor.parsers;

import io.github.kjarrio.extractor.utils.JsonUtils;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;

public abstract class AbstractParser {

    protected String readFile(File inputFile) throws Exception {

        String contents;

        String absolutePath = inputFile.getAbsolutePath();

        try {
            contents = FileUtils.readFileToString(inputFile, "UTF-8");
        } catch (IOException e) {
            throw new Exception("Error reading file: " + absolutePath);
        }

        if (contents.isEmpty()) {
            throw new Exception("File is empty: " + absolutePath);
        }

        if (JsonUtils.isJsonFile(inputFile) && !JsonUtils.isValidJson(contents)) {
            throw new Exception("Invalid JSON file: " + absolutePath);
        }

        return contents;

    }

}

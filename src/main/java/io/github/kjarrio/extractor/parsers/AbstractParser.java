package io.github.kjarrio.extractor.parsers;

import io.github.kjarrio.extractor.utils.JsonUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
 import java.io.File;
import java.io.IOException;

public abstract class AbstractParser {

    protected File getImageFile(File inputFile) throws Exception {
        String ext = FilenameUtils.getExtension(inputFile.getName());
        File inputImage = new File(inputFile.getParentFile(), inputFile.getName().replace("."+ext, ".png"));
        if (!inputImage.exists()) throw new Exception("Image doesn't exists");
        return inputImage;
    }

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

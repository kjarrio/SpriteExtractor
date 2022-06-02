package io.github.kjarrio.extractor.utils;

import org.apache.commons.io.FileUtils;
import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class FSUtils {

    public static final String MAIN_RESOURCES = "/home/oem/IdeaProjects/SpriteExtractor/src/main/resources/schemas/";
    public static final String TEST_RESOURCES = "/home/oem/IdeaProjects/SpriteExtractor/src/test/resources/";

    public static List<String> readResourceLines(String f) {
        return readLines(new File(MAIN_RESOURCES + f));
    }

    public static List<String> readTestResourceLines(String f) {
        return readLines(new File(TEST_RESOURCES + f));
    }

    public static List<String> readLines(String f) {
        return readLines(new File(f));
    }

    public static List<String> readLines(File f) {

        try {
            return FileUtils.readLines(f, Charset.defaultCharset());
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public static String readResourceFile(String f) {
        return readFile(new File(MAIN_RESOURCES + f));
    }

    public static String readTestResourceFile(String f) {
        return readFile(new File(TEST_RESOURCES + f));
    }

    public static String readFile(String f) {
        return readFile(new File(f));
    }

    public static String readFile(File f) {

        try {
            return FileUtils.readFileToString(f, Charset.defaultCharset());
        } catch (Exception e) {
            return "";
        }
    }

    public static Boolean hasExt(File inputFile, String ext) {
        return inputFile.getName().toLowerCase().endsWith("." + ext);
    }

}

package io.github.kjarrio.extractor.utils;

import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class FormatUtils {

    public static List<String> readAndClean(File inputFile, String comment) {
        List<String> lines;
        try {
            lines = FileUtils.readLines(inputFile, "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return removeEmptyLines(removeComments(lines, comment));
    }

    public static List<String> removeEmptyLines(List<String> lines) {

        List<String> noEmptyLines = new ArrayList<>();

        for (String line : lines) {
            if (line.trim().length() != 0) {
                noEmptyLines.add(line);
            }
        }

        return noEmptyLines;

    }

    public static List<String> removeComments(List<String> lines, String comment) {

        List<String> noComments = new ArrayList<>();

        for (String line : lines) {
            if (!line.trim().startsWith(comment)) {
                noComments.add(line);
            }
        }

        return noComments;

    }

    public static List<List<String>> splitSegments(File file, String sep) {

        List<List<String>> output = new ArrayList<>();
        List<String> lines;

        try {
            lines = FileUtils.readLines(file, Charset.defaultCharset());
        } catch (IOException e) {
            return output;
        }

        List<String> currentSegment = new ArrayList<>();

        for (String line : lines) {

            if (line.equals(sep)) {
                output.add(currentSegment);
                currentSegment = new ArrayList<>();
                continue;
            }

            currentSegment.add(line);

        }

        return output;

    }

}

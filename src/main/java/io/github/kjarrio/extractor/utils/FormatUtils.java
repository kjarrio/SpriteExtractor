package io.github.kjarrio.extractor.utils;

import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class FormatUtils {

    public static List<List<String>> splitLines(File file, String sep) {

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

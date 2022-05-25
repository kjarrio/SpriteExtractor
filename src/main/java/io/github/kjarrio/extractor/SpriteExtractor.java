package io.github.kjarrio.extractor;

import io.github.kjarrio.extractor.parsers.SheetParser;
import io.github.kjarrio.extractor.parsers.json.JsonArrayParser;
import io.github.kjarrio.extractor.parsers.json.JsonHashParser;
import io.github.kjarrio.extractor.parsers.other.Toolkit2dParser;
import io.github.kjarrio.extractor.parsers.other.UnityTexture2dParser;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SpriteExtractor {

    private static final List<SheetParser> parsers = new ArrayList<>();

    static {
        parsers.add(new JsonHashParser());
        parsers.add(new JsonArrayParser());
        parsers.add(new UnityTexture2dParser());
        parsers.add(new Toolkit2dParser());
    }

    public static void extract(String spriteSheetFile, String outputDir) throws Exception {
        extract(new File(spriteSheetFile), new File(outputDir));
    }

    public static void extract(File spriteSheetFile, File outputDir) throws Exception {

        for (SheetParser parser : parsers) {
            if (parser.checkType(spriteSheetFile)) {
                parser.extract(spriteSheetFile, outputDir);
                return;
            }
        }

        throw new Exception("Unable to extract sprite sheet. Unknown format.");

    }

}

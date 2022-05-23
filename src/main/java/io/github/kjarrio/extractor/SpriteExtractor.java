package io.github.kjarrio.extractor;

import io.github.kjarrio.extractor.parsers.JsonHashParser;
import java.io.File;

public class SpriteExtractor {

    public static void extract(File spriteSheetFile, File outputDir) throws Exception {
        JsonHashParser parser = new JsonHashParser(spriteSheetFile);
        parser.extract(outputDir);
    }

}

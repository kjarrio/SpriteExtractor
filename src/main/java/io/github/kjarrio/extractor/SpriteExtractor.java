package io.github.kjarrio.extractor;

import io.github.kjarrio.extractor.parsers.base.SheetParser;
import io.github.kjarrio.extractor.parsers.json.*;
import io.github.kjarrio.extractor.parsers.other.*;
import io.github.kjarrio.extractor.parsers.plist.Cocos2DParser;
import io.github.kjarrio.extractor.parsers.xml.CeGUIOgreParser;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SpriteExtractor {

    private static final List<SheetParser> parsers = new ArrayList<>();

    static {
        parsers.add(new AmethystParser());
        parsers.add(new AppGameKitParser());
        parsers.add(new Blacksmith2DParser());
        parsers.add(new CaatParser());
        parsers.add(new CeGUIOgreParser());
        parsers.add(new Cocos2DParser());
        parsers.add(new BHiveParser());
        parsers.add(new JsonArrayParser());
        parsers.add(new JsonHashParser());
        parsers.add(new LayaAir2Parser());
        parsers.add(new MapBoxParser());
        parsers.add(new MoleculeParser());
        parsers.add(new Toolkit2dParser());
        parsers.add(new UnityTexture2dParser());
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

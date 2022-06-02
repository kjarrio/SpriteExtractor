package io.github.kjarrio.extractor.parsers.plist;

import com.dd.plist.*;
import io.github.kjarrio.extractor.objects.ImageFrame;
import io.github.kjarrio.extractor.pair.ImageFramesPair;
import io.github.kjarrio.extractor.pair.IntPair;
import io.github.kjarrio.extractor.pair.Pair;
import io.github.kjarrio.extractor.parsers.base.AbstractParser;
import io.github.kjarrio.extractor.parsers.base.SheetParser;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Cocos2DParser extends AbstractParser implements SheetParser {

    { this.EXTENSION = "xml"; }

    @Override
    public Boolean checkType(File inputFile) {
        return super.checkType(inputFile);
    }

    @Override
    protected ImageFramesPair parse(File inputFile, File outputFolder) throws Exception {

        List<ImageFrame> frames = new ArrayList<>();

        try {
            NSDictionary rootDict = (NSDictionary) PropertyListParser.parse(inputFile);
            NSDictionary frameDict = (NSDictionary) rootDict.objectForKey("frames");
            NSDictionary metaDict = (NSDictionary) rootDict.objectForKey("metadata");
//
            for (String imageName : frameDict.allKeys()) {

                NSDictionary imageDict = (NSDictionary) frameDict.objectForKey(imageName);

                IntPair spriteOffset = getDictIntPair(imageDict, "spriteOffset");
                IntPair spriteSize = getDictIntPair(imageDict, "spriteSize");
                IntPair spriteSourceSize = getDictIntPair(imageDict, "spriteSourceSize");
                Pair<IntPair> textureRect = getDoubleDictIntPair(imageDict, "textureRect");
                Boolean rotated = getDictBoolean(imageDict, "textureRotated");

                ImageFrame frame = new ImageFrame();
                frame.name = imageName;
                frame.rectX = textureRect.a.a;
                frame.rectY = textureRect.a.a;
                frame.rectW = spriteSize.a;
                frame.rectH = spriteSize.b;
                frame.width = spriteSourceSize.a;
                frame.height = spriteSourceSize.b;
                frame.rotated = rotated;
                frame.offsetX = Math.abs(spriteOffset.a);
                frame.offsetY = Math.abs(spriteOffset.b);
                frame.trimmed = (frame.offsetX > 0 || frame.offsetY > 0);

                frames.add(frame);
            }

        } catch(Exception ex) {
            ex.printStackTrace();
        }


        // Input image
        File inputImage = getImageFile(inputFile);

        return new ImageFramesPair(inputImage, frames);

    }

    private Boolean getDictBoolean(NSDictionary dict, String key) {
        NSObject obj = dict.objectForKey(key);
        if (obj instanceof NSNumber) {
            return ((NSNumber) obj).boolValue();
        }
        return false;
    }

    private IntPair getDictIntPair(NSDictionary dict, String key) {
        String s = ((NSString) dict.objectForKey(key)).getContent();
        s = s.replace("{", "").replace("}", "");
        String[] parts = s.split(",");
        return new IntPair(Integer.valueOf(parts[0]), Integer.valueOf(parts[1]));
    }

    private Pair<IntPair> getDoubleDictIntPair(NSDictionary dict, String key) {
        String s = ((NSString) dict.objectForKey(key)).getContent();
        s = s.replace("{{", "").replace("}}", "");
        String[] pairs = s.split("},\\{");
        String[] pair1 = pairs[0].split(",");
        String[] pair2 = pairs[1].split(",");
        return new Pair<>(
                new IntPair(Integer.valueOf(pair1[0]), Integer.valueOf(pair1[1])),
                new IntPair(Integer.valueOf(pair2[0]), Integer.valueOf(pair2[1]))
        );
    }

}

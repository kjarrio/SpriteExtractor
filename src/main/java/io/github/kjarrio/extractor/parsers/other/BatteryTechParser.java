package io.github.kjarrio.extractor.parsers.other;

import io.github.kjarrio.extractor.objects.ImageFrame;
import io.github.kjarrio.extractor.pair.ImageFramesPair;
import io.github.kjarrio.extractor.pair.IntPair;
import io.github.kjarrio.extractor.parsers.base.AbstractParser;
import io.github.kjarrio.extractor.parsers.base.SheetParser;
import io.github.kjarrio.extractor.utils.FormatUtils;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class BatteryTechParser extends AbstractParser implements SheetParser {

    { this.EXTENSION = "btx"; }

    @Override
    public ImageFramesPair parse(File inputFile, File outputFolder) throws Exception {

        File inputImage = null;

        List<ImageFrame> frames = new ArrayList<>();
        FileUtils.readLines(inputFile, Charset.defaultCharset());

        List<String> lines = FormatUtils.readAndClean(inputFile);

        Integer width = -1;
        Integer height = -1;

        ImageFrame frame = null;

        String[] uvs = new String[]{};

        for (String line : lines) {

            if (line.startsWith("btx")) continue;

            String[] parts = line.split(" = ");
            String key = parts[0];
            String value = parts[1];

            if (key.startsWith("texture")) {

                String textureKey = key.replace("texture.", "").trim();

                switch (textureKey) {
                    case "assetname":
                        inputImage = new File(inputFile.getParentFile(), value);
                        break;
                    case "width":
                        width = Integer.valueOf(value);
                        break;
                    case "height":
                        height = Integer.valueOf(value);
                        break;

                }

            } else if (key.startsWith("image")) {

                String imageKey = key.replace("image.", "");

                switch (imageKey) {

                    case "assetname":
                        if (frame != null) frames.add(frame);
                        frame = new ImageFrame();
                        frame.name = value;
                        break;

                    case "rotated":   frame.rotated = Boolean.valueOf(value);break;
                    case "trimmed":   frame.trimmed = Boolean.valueOf(value);break;

                    case "origsize":
                        IntPair sizePair = new IntPair(value);
                        frame.width = sizePair.a;
                        frame.height = sizePair.b;
                        break;
                    case "trimmedsize":
                        IntPair tSize = new IntPair(value);
                        frame.rectW = tSize.a;
                        frame.rectH = tSize.b;
                        break;
                    case "offset":
                        IntPair pOffset = new IntPair(value);
                        frame.offsetX = pOffset.a;
                        frame.offsetY = pOffset.b;
                        break;
                    case "uvs":
                        uvs = value.split(" ");
                        break;
                }

                if (uvs.length != 0) {
                    if (frame != null && frame.rotated) {
                        // Todo: throw new RuntimeException("Rotated frames are not supported yet");
                        frame.rectY = Math.round(width * Float.parseFloat(uvs[1]));
                        frame.rectX = Math.round(height * Float.parseFloat(uvs[0]));

                    } else {
                        frame.rectX = Math.round(width * Float.parseFloat(uvs[0]));
                        frame.rectY = Math.round(height * Float.parseFloat(uvs[1]));
                    }
                }



            }

        }

        if (frame != null) frames.add(frame);

        return new ImageFramesPair(inputImage, frames);

    }

}

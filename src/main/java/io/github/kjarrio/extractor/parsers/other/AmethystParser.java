package io.github.kjarrio.extractor.parsers.other;

import io.github.kjarrio.extractor.objects.ImageFrame;
import io.github.kjarrio.extractor.pair.ImageFramesPair;
import io.github.kjarrio.extractor.parsers.base.AbstractParser;
import io.github.kjarrio.extractor.parsers.base.SheetParser;
import io.github.kjarrio.extractor.utils.FormatUtils;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class AmethystParser extends AbstractParser implements SheetParser {

    { this.EXTENSION = "ron"; }

    @Override
    protected ImageFramesPair parse(File inputFile, File outputFolder) throws Exception {

        File inputImage = getImageFile(inputFile);

        List<ImageFrame> frames = new ArrayList<>();
        FileUtils.readLines(inputFile, Charset.defaultCharset());

        List<String> lines = FormatUtils.readAndClean(inputFile);
        List<String> spriteLines = new ArrayList<>();
        Boolean sprites = false;

        for (String line : lines) {

            line = line.trim();

            if (line.equals("]")) {
                sprites = false;
                continue;
            }

            if (line.startsWith("sprites: [")) {
                sprites = true;
                continue;
            }

            if (sprites && !line.startsWith("(")) {
                spriteLines.add(line.replace(",", "").trim());
            }

        }

        List<List<String>> segments = FormatUtils.splitSegments(spriteLines, ")");

        for (List<String> segment : segments) {

            ImageFrame frame = new ImageFrame("");

            for (String segmentLine : segment) {

                if (segmentLine.startsWith("//")) {
                    frame.name = FormatUtils.subAfter(segmentLine, ": ");

                } else if (segmentLine.startsWith("x:")) {
                    frame.rectX = Integer.valueOf(FormatUtils.subAfter(segmentLine, "x: "));

                } else if (segmentLine.startsWith("y:")) {
                    frame.rectY = Integer.valueOf(FormatUtils.subAfter(segmentLine, "y: "));

                } else if (segmentLine.startsWith("width:")) {
                    frame.rectW = frame.width = Integer.valueOf(FormatUtils.subAfter(segmentLine, "width: "));

                } else if (segmentLine.startsWith("height:")) {
                    frame.rectH = frame.height = Integer.valueOf(FormatUtils.subAfter(segmentLine, "height: "));

                } else if (segmentLine.startsWith("offsets:")) {

                    String offsets = FormatUtils.subAfter(segmentLine, "offsets: ");
                    offsets = offsets.replace("(", "").replace(")", "").trim();
                    String[] offsetsSplit = offsets.split(" ");
                    Integer offsetX = Math.round(Float.parseFloat(offsetsSplit[0]));
                    Integer offsetY = Math.round(Float.parseFloat(offsetsSplit[1]));

                    if (offsetX != 0 || offsetY != 0) {
                        frame.trimmed = true;
                        frame.offsetX = offsetX;
                        frame.offsetY = offsetY;
                        frame.width += offsetX;
                        frame.height += offsetY;
                    }

                }

                frames.add(frame);

            }

        }

        return new ImageFramesPair(inputImage, frames);

    }

}

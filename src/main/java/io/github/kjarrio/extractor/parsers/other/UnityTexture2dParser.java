package io.github.kjarrio.extractor.parsers.other;

import io.github.kjarrio.extractor.objects.ImageFrame;
import io.github.kjarrio.extractor.pair.ImageFramesPair;
import io.github.kjarrio.extractor.parsers.base.AbstractParser;
import io.github.kjarrio.extractor.parsers.base.SheetParser;
import io.github.kjarrio.extractor.utils.FormatUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UnityTexture2dParser extends AbstractParser implements SheetParser {

    { this.EXTENSION = "tpsheet"; }

    @Override
    public Boolean checkType(File inputFile) {
        if (!super.checkType(inputFile)) return false;
        return !FormatUtils.hasString(inputFile, "{");
    }

    private File inputFile;
    private File inputImage;

    @Override
    protected ImageFramesPair parse(File inputFile, File outputFolder) throws Exception {

        this.inputFile = inputFile;

        List<String> lines = FormatUtils.readAndClean(inputFile, "#");

        final List<ImageFrame> frames = new ArrayList<>();

        lines.forEach(line -> {

            if (line.startsWith(":")) {
                parseMetaLine(line);
            } else {
                frames.add(parseImageLine(line));
            }

        });

        return new ImageFramesPair(inputImage, frames);

    }

    private ImageFrame parseImageLine(String line) {
        String[] parts = line.split(";");
        ImageFrame frame = new ImageFrame();
        frame.name = parts[0];
        frame.rectX = Integer.valueOf(parts[1]);
        frame.rectY = Integer.valueOf(parts[2]);
        frame.rectW = Integer.valueOf(parts[3]);
        frame.rectH = Integer.valueOf(parts[4]);
        return frame;
    }

    private void parseMetaLine(String line) {

        String[] parts = line.substring(1).split("=");

        if (parts.length != 2) return;

        String key = parts[0];
        String value = parts[1];

        if (key.equals("texture")) inputImage = new File(inputFile.getParentFile(), value);

    }

}

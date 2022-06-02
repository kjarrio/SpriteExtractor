package io.github.kjarrio.extractor.parsers.other;

import io.github.kjarrio.extractor.objects.ImageFrame;
import io.github.kjarrio.extractor.pair.ImageFramesPair;
import io.github.kjarrio.extractor.parsers.base.AbstractParser;
import io.github.kjarrio.extractor.parsers.base.SheetParser;
import io.github.kjarrio.extractor.utils.FormatUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class AppGameKitParser extends AbstractParser implements SheetParser {

    { this.EXTENSION = "txt"; }

    @Override
    public Boolean checkType(File inputFile) {
        return super.checkType(inputFile) &&
                FormatUtils.checkStrings(inputFile, ":", "size", ";");
    }

    @Override
    protected ImageFramesPair parse(File inputFile, File outputFolder) throws Exception {

        File inputImage = getImageFile(inputFile);

        List<ImageFrame> frames = new ArrayList<>();
        List<String> lines = FileUtils.readLines(inputFile, Charset.defaultCharset());

        for (String line : lines) {
            if (line.isEmpty()) continue;
            String[] parts = line.split(":");
            ImageFrame frame = new ImageFrame();
            frame.name = FilenameUtils.removeExtension(parts[0]);
            frame.rectX = Integer.valueOf(parts[1]);
            frame.rectY = Integer.valueOf(parts[2]);
            frame.width = frame.rectW = Integer.valueOf(parts[3]);
            frame.height = frame.rectH = Integer.valueOf(parts[4]);
            frames.add(frame);
        }

        return new ImageFramesPair(inputImage, frames);

    }

}

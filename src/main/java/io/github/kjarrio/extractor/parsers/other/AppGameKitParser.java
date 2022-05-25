package io.github.kjarrio.extractor.parsers.other;

import io.github.kjarrio.extractor.objects.ImageFrame;
import io.github.kjarrio.extractor.parsers.AbstractParser;
import io.github.kjarrio.extractor.parsers.SheetParser;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class AppGameKitParser extends AbstractParser implements SheetParser {

    @Override
    public Boolean checkType(File inputFile) {
        return inputFile.getName().toLowerCase().endsWith(".txt");
    }

    @Override
    public void extract(File inputFile, File outputFolder) throws Exception {

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

        extractImages(inputImage, outputFolder, frames);

    }

}

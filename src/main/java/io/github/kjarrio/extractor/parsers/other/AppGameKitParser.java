package io.github.kjarrio.extractor.parsers.other;

import io.github.kjarrio.extractor.objects.CommonImageFrame;
import io.github.kjarrio.extractor.parsers.AbstractParser;
import io.github.kjarrio.extractor.parsers.SheetParser;
import io.github.kjarrio.extractor.utils.ImageUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import java.awt.image.BufferedImage;
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

        List<CommonImageFrame> frames = new ArrayList<>();
        List<String> lines = FileUtils.readLines(inputFile, Charset.defaultCharset());

        for (String line : lines) {
            if (line.isEmpty()) continue;
            String[] parts = line.split(":");
            CommonImageFrame frame = new CommonImageFrame();
            frame.name = FilenameUtils.removeExtension(parts[0]);
            frame.rectX = Integer.valueOf(parts[1]);
            frame.rectY = Integer.valueOf(parts[2]);
            frame.width = frame.rectW = Integer.valueOf(parts[3]);
            frame.height = frame.rectH = Integer.valueOf(parts[4]);
            frames.add(frame);
        }

        BufferedImage inputImg = ImageUtils.read(inputImage);

        if (!outputFolder.exists()) outputFolder.mkdir();

        frames.forEach(frame -> {
            try {
                BufferedImage spriteImg = ImageUtils.rectangle(inputImg, frame.rectX, frame.rectY, frame.rectW, frame.rectH);
                ImageUtils.save(spriteImg, new File(outputFolder, frame.name + ".png"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

    }

}

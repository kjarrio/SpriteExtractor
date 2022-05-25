package io.github.kjarrio.extractor.parsers.other;

import io.github.kjarrio.extractor.objects.CommonImageFrame;
import io.github.kjarrio.extractor.parsers.AbstractParser;
import io.github.kjarrio.extractor.parsers.SheetParser;
import io.github.kjarrio.extractor.utils.FormatUtils;
import io.github.kjarrio.extractor.utils.ImageUtils;
import org.imgscalr.Scalr;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Toolkit2dParser extends AbstractParser implements SheetParser {

    @Override
    public Boolean checkType(File inputFile) {
        return inputFile.getName().toLowerCase().endsWith(".bytes");
    }

    @Override
    public void extract(File inputFile, File outputFolder) throws Exception {

        File inputImage = getImageFile(inputFile);

        List<CommonImageFrame> frames = new ArrayList<>();
        List<List<String>> segments = FormatUtils.splitLines(inputFile, "~");

        for (int i = 1; i < segments.size(); i++) {

            List<String> segment = segments.get(i);

            String name = "";
            String size = "";
            String offset = "";
            String rotation = "";

            for (String line : segment) {

                if (line.startsWith("n ")) {
                    name = line.substring(2);
                } else if (line.startsWith("s ")) {
                    size = line.substring(2);
                } else if (line.startsWith("o ")) {
                    offset = line.substring(2);
                } else if (line.startsWith("r ")) {
                    rotation = line.substring(2);
                }

            }

            if (name.isEmpty() || size.isEmpty()) continue;

            String[] sizes = size.split(" ");

            CommonImageFrame frame = new CommonImageFrame();
            frame.name = name;
            frame.rectX = Integer.valueOf(sizes[0]);
            frame.rectY = Integer.valueOf(sizes[1]);
            frame.rectW = Integer.valueOf(sizes[2]);
            frame.rectH = Integer.valueOf(sizes[3]);

            if (!offset.isEmpty()) {

                frame.trimmed = true;

                String[] offsets = offset.split(" ");

                frame.offsetX = Integer.valueOf(offsets[0]);
                frame.offsetY = Integer.valueOf(offsets[1]);
                frame.width = Integer.valueOf(offsets[2]);
                frame.height = Integer.valueOf(offsets[3]);

            } else {

                frame.trimmed = false;
                frame.width = frame.rectW;
                frame.height = frame.rectH;
            }

            if (!rotation.isEmpty()) {
                frame.rotated = true;
            }

            frames.add(frame);

        }

        BufferedImage inputImg = ImageUtils.read(inputImage);

        if (!outputFolder.exists()) outputFolder.mkdir();

        frames.forEach(frame -> {
            try {

                BufferedImage spriteImg = ImageUtils.rectangle(inputImg, frame.rectX, frame.rectY, frame.rectW, frame.rectH);

                // Rotate
                if (frame.rotated)
                    spriteImg = ImageUtils.rotate(spriteImg, Scalr.Rotation.CW_270);

                // Trimmed
                if (frame.trimmed)
                    spriteImg = ImageUtils.expand(spriteImg, frame.width, frame.height, frame.offsetX, frame.offsetY);

                ImageUtils.save(spriteImg, new File(outputFolder, frame.name + ".png"));

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

    }

}

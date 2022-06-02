package io.github.kjarrio.extractor.parsers.base;

import io.github.kjarrio.extractor.objects.ImageFrame;
import io.github.kjarrio.extractor.pair.ImageFramesPair;
import io.github.kjarrio.extractor.utils.FSUtils;
import io.github.kjarrio.extractor.utils.ImageUtils;
import io.github.kjarrio.extractor.utils.JsonUtils;
import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

public abstract class AbstractJSONParser extends AbstractParser implements SheetParser {

    protected String JSON_SCHEMA;

    public Boolean checkType(File inputFile) {
        return FSUtils.hasExt(inputFile, EXTENSION) && JsonUtils.validateSchema(inputFile, JSON_SCHEMA);
    }

    protected abstract ImageFramesPair parse(File inputFile, File outputFolder) throws Exception;

    @Override
    public void extract(File inputFile, File outputFolder) throws Exception {
        ImageFramesPair parsed = parse(inputFile, outputFolder);
        extractImages(parsed.getImage(), outputFolder, parsed.getFrames());
    }

    protected File getImageFile(File inputFile) throws Exception {
        String ext = FilenameUtils.getExtension(inputFile.getName());
        File inputImage = new File(inputFile.getParentFile(), inputFile.getName().replace("."+ext, ".png"));
        if (!inputImage.exists()) throw new Exception("Image doesn't exists");
        return inputImage;
    }

    protected void extractImages(File inputImage, File outputFolder, List<ImageFrame> frames) throws Exception {

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

                String fileName = FilenameUtils.removeExtension(frame.name + ".png");

                ImageUtils.save(spriteImg, new File(outputFolder, fileName + ".png"));

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

    }

}

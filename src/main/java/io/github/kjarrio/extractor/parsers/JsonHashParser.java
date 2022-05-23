package io.github.kjarrio.extractor.parsers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.kjarrio.extractor.utils.ImageUtils;
import io.github.kjarrio.extractor.utils.JsonUtils;
import org.apache.commons.io.FileUtils;
import org.imgscalr.Scalr;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class JsonHashParser {

    private final File inputFile;
    private final JsonObject json;

    public JsonHashParser(File inputFile) throws Exception {

        this.inputFile = inputFile;

        String contents = "";

        try {
            contents = FileUtils.readFileToString(this.inputFile, "UTF-8");
        } catch (IOException e) {
            throw new Exception("Error reading file: " + this.inputFile.getAbsolutePath());
        }

        if (contents.isEmpty() || !JsonUtils.isValidJson(contents)) {
            throw new Exception("Invalid JSON file: " + this.inputFile.getAbsolutePath());
        }

        json = JsonParser.parseString(contents).getAsJsonObject();

    }

    public void extract(File outputFolder) throws Exception {

        // Parse the meta data
        Meta metaData = parseMeta();

        // Parse the image frames
        Map<String, ImageFrame> imageFrames = parseFrames();

        // Read the input image
        File inputImage = new File(inputFile.getParentFile(), metaData.image);
        BufferedImage inputImageData = ImageUtils.read(inputImage);

        // Extract images
        for (String imageName : imageFrames.keySet()) {

            ImageFrame fr = imageFrames.get(imageName);

            // Extract
            BufferedImage img = ImageUtils.rectangle(inputImageData, fr.frameX, fr.frameY, fr.rotated ? fr.frameH : fr.frameW, fr.rotated ? fr.frameW : fr.frameH);

            // Rotate
            if (fr.rotated) {
                img = ImageUtils.rotate(img, Scalr.Rotation.CW_270);
            }

            // Trimmed
            if (fr.trimmed) {
                img = ImageUtils.expand(img, fr.sourceSizeW, fr.sourceSizeH, fr.sourceX, fr.sourceY);
            }

            // Save
            File outputImage = new File(outputFolder, imageName);
            ImageUtils.save(img, outputImage);

        }

    }

    private Meta parseMeta() {

        Meta meta = new Meta();

        JsonObject metaJson = json.getAsJsonObject("meta");

        meta.image = metaJson.get("image").getAsString();
        meta.format = metaJson.get("format").getAsString();
        meta.scale = metaJson.get("scale").getAsInt();
        meta.sizeW = metaJson.getAsJsonObject("size").get("w").getAsInt();
        meta.sizeH = metaJson.getAsJsonObject("size").get("h").getAsInt();

        return meta;

    }

    private Map<String, ImageFrame> parseFrames() {

        JsonObject frames = json.getAsJsonObject("frames");

        Set<String> imageKeys = frames.keySet();
        Map<String, ImageFrame> imageFrames = new HashMap<>();

        for (String imageKey : imageKeys) {

            JsonObject imageObject = frames.getAsJsonObject(imageKey);
            JsonObject frameObject = imageObject.getAsJsonObject("frame");
            JsonObject spriteSourceSizeObject = imageObject.getAsJsonObject("spriteSourceSize");
            JsonObject sourceSizeObject = imageObject.getAsJsonObject("sourceSize");

            ImageFrame imageFrame = new ImageFrame();

            imageFrame.name = imageKey;
            imageFrame.frameX = frameObject.get("x").getAsInt();
            imageFrame.frameY = frameObject.get("y").getAsInt();
            imageFrame.frameW = frameObject.get("w").getAsInt();
            imageFrame.frameH = frameObject.get("h").getAsInt();
            imageFrame.rotated = imageObject.get("rotated").getAsBoolean();
            imageFrame.trimmed = imageObject.get("trimmed").getAsBoolean();
            imageFrame.sourceX = spriteSourceSizeObject.get("x").getAsInt();
            imageFrame.sourceY = spriteSourceSizeObject.get("y").getAsInt();
            imageFrame.sourceW = spriteSourceSizeObject.get("w").getAsInt();
            imageFrame.sourceH = spriteSourceSizeObject.get("h").getAsInt();
            imageFrame.sourceSizeW = sourceSizeObject.get("w").getAsInt();
            imageFrame.sourceSizeH = sourceSizeObject.get("h").getAsInt();

            imageFrames.put(imageKey, imageFrame);

        }

        return imageFrames;

    }

    private static class ImageFrame {

        public String name;

        public Integer frameX;
        public Integer frameY;
        public Integer frameW;
        public Integer frameH;

        public Boolean rotated;
        public Boolean trimmed;

        public Integer sourceX;
        public Integer sourceY;
        public Integer sourceW;
        public Integer sourceH;

        public Integer sourceSizeW;
        public Integer sourceSizeH;

    }

    private static class Meta {

        public String image;
        public String format;

        public Integer sizeW;
        public Integer sizeH;
        public Integer scale;

    }

}

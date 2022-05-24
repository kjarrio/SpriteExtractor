package io.github.kjarrio.extractor.parsers.json;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.kjarrio.extractor.parsers.json.objects.ImageFrame;
import io.github.kjarrio.extractor.parsers.json.objects.JsonMetaData;
import io.github.kjarrio.extractor.parsers.SheetParser;
import io.github.kjarrio.extractor.parsers.AbstractParser;
import io.github.kjarrio.extractor.utils.ImageUtils;
import io.github.kjarrio.extractor.utils.JsonUtils;
import org.imgscalr.Scalr;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class JsonHashParser extends AbstractParser implements SheetParser {

    @Override
    public Boolean checkType(File inputFile) {

        if (!JsonUtils.isJsonFile(inputFile)) {
            return false;
        }

        try {

            String contents = readFile(inputFile);

            if (!JsonUtils.isValidJson(contents)) {
                return false;
            }

            JsonObject json = JsonParser.parseString(contents).getAsJsonObject();

            if (!json.has("frames")) {
                return false;
            }

            if (json.get("frames").isJsonObject()) {
                return true;
            }

        } catch (Exception e) {
            return false;
        }

        return false;

    }

    @Override
    public void extract(File inputFile, File outputFolder) throws Exception {

        String contents = readFile(inputFile);

        JsonObject json = JsonParser.parseString(contents).getAsJsonObject();

        // Parse the meta data
        JsonMetaData metaData = parseMeta(json);

        // Parse the image frames
        Map<String, ImageFrame> imageFrames = parseFrames(json);

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

    protected JsonMetaData parseMeta(JsonObject json) {

        JsonMetaData meta = new JsonMetaData();

        JsonObject metaJson = json.getAsJsonObject("meta");

        meta.image = metaJson.get("image").getAsString();
        meta.format = metaJson.get("format").getAsString();
        meta.scale = metaJson.get("scale").getAsInt();
        meta.sizeW = metaJson.getAsJsonObject("size").get("w").getAsInt();
        meta.sizeH = metaJson.getAsJsonObject("size").get("h").getAsInt();

        return meta;

    }

    protected Map<String, ImageFrame> parseFrames(JsonObject json) {

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

}

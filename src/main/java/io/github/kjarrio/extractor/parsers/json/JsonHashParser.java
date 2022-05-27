package io.github.kjarrio.extractor.parsers.json;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.kjarrio.extractor.objects.ImageFrame;
import io.github.kjarrio.extractor.objects.ImageData;
import io.github.kjarrio.extractor.parsers.SheetParser;
import io.github.kjarrio.extractor.parsers.AbstractParser;
import io.github.kjarrio.extractor.utils.JsonUtils;
import java.io.File;
import java.util.*;

public class JsonHashParser extends AbstractParser implements SheetParser {

    @Override
    public Boolean checkType(File inputFile) {

        if (!JsonUtils.isJsonFile(inputFile)) return false;

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
        ImageData metaData = parseMeta(json);

        // Parse the image frames
        List<ImageFrame> frames = parseFrames(json);

        // Read the input image
        File inputImage = new File(inputFile.getParentFile(), metaData.image);

        // Extract
        extractImages(inputImage, outputFolder, frames);

    }

    protected ImageData parseMeta(JsonObject json) {

        ImageData meta = new ImageData();

        JsonObject metaJson = json.getAsJsonObject("meta");

        meta.image = metaJson.get("image").getAsString();
        meta.format = metaJson.get("format").getAsString();
        meta.scale = metaJson.get("scale").getAsInt();
        meta.width = metaJson.getAsJsonObject("size").get("w").getAsInt();
        meta.height = metaJson.getAsJsonObject("size").get("h").getAsInt();

        return meta;

    }

    protected List<ImageFrame> parseFrames(JsonObject json) {

        JsonObject frames = json.getAsJsonObject("frames");

        Set<String> imageKeys = frames.keySet();
        List<ImageFrame> imageFrames = new ArrayList<>();

        for (String imageKey : imageKeys) {

            JsonObject imageObject = frames.getAsJsonObject(imageKey);
            JsonObject frameObject = imageObject.getAsJsonObject("frame");
            JsonObject spriteSourceSizeObject = imageObject.getAsJsonObject("spriteSourceSize");
            JsonObject sourceSizeObject = imageObject.getAsJsonObject("sourceSize");

            ImageFrame imageFrame = new ImageFrame();

            imageFrame.name = imageKey;
            imageFrame.rectX = frameObject.get("x").getAsInt();
            imageFrame.rectY = frameObject.get("y").getAsInt();
            imageFrame.rectW = frameObject.get("w").getAsInt();
            imageFrame.rectH = frameObject.get("h").getAsInt();
            imageFrame.rotated = imageObject.get("rotated").getAsBoolean();
            imageFrame.trimmed = imageObject.get("trimmed").getAsBoolean();
            imageFrame.offsetX = spriteSourceSizeObject.get("x").getAsInt();
            imageFrame.offsetY = spriteSourceSizeObject.get("y").getAsInt();
            imageFrame.width = sourceSizeObject.get("w").getAsInt();
            imageFrame.height = sourceSizeObject.get("h").getAsInt();

            imageFrames.add(imageFrame);

        }

        return imageFrames;

    }

}

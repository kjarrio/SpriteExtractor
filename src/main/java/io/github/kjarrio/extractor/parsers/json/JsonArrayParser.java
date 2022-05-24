package io.github.kjarrio.extractor.parsers.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.kjarrio.extractor.parsers.json.objects.ImageFrame;
import io.github.kjarrio.extractor.parsers.SheetParser;
import io.github.kjarrio.extractor.utils.JsonUtils;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class JsonArrayParser extends JsonHashParser implements SheetParser {

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

            if (json.get("frames").isJsonArray()) {
                return true;
            }

        } catch (Exception e) {
            return false;
        }

        return false;

    }

    protected Map<String, ImageFrame> parseFrames(JsonObject json) {

        JsonArray frames = json.getAsJsonArray("frames");

        Map<String, ImageFrame> imageFrames = new HashMap<>();

        for (JsonElement element : frames) {

            JsonObject imageObject = element.getAsJsonObject();
            JsonObject frameObject = imageObject.getAsJsonObject("frame");
            JsonObject spriteSourceSizeObject = imageObject.getAsJsonObject("spriteSourceSize");
            JsonObject sourceSizeObject = imageObject.getAsJsonObject("sourceSize");

            ImageFrame imageFrame = new ImageFrame();

            imageFrame.name = imageObject.get("filename").getAsString();
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

            imageFrames.put(imageFrame.name, imageFrame);

        }

        return imageFrames;

    }

}

package io.github.kjarrio.extractor.parsers.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.kjarrio.extractor.objects.ImageFrame;
import io.github.kjarrio.extractor.parsers.SheetParser;
import io.github.kjarrio.extractor.utils.JsonUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

    protected List<ImageFrame> parseFrames(JsonObject json) {

        JsonArray frames = json.getAsJsonArray("frames");

        List<ImageFrame> imageFrames = new ArrayList<>();

        for (JsonElement element : frames) {

            JsonObject imageObject = element.getAsJsonObject();
            JsonObject frameObject = imageObject.getAsJsonObject("frame");
            JsonObject spriteSourceSizeObject = imageObject.getAsJsonObject("spriteSourceSize");
            JsonObject sourceSizeObject = imageObject.getAsJsonObject("sourceSize");

            ImageFrame imageFrame = new ImageFrame();

            imageFrame.name = imageObject.get("filename").getAsString();
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

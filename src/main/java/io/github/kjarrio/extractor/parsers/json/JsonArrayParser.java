package io.github.kjarrio.extractor.parsers.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.kjarrio.extractor.objects.ImageFrame;
import io.github.kjarrio.extractor.parsers.SheetParser;
import java.util.ArrayList;
import java.util.List;

public class JsonArrayParser extends JsonHashParser implements SheetParser {

    {
        this.JSON_SCHEMA = "json_array_schema.json";
        this.EXTENSION = "json";
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

package io.github.kjarrio.extractor.parsers.json;

import com.google.gson.JsonObject;
import io.github.kjarrio.extractor.objects.ImageFrame;
import io.github.kjarrio.extractor.parsers.SheetParser;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BHiveParser extends JsonHashParser implements SheetParser {

    {
        this.JSON_SCHEMA = "bhive_schema.json";
        this.EXTENSION = "json";
    }

    @Override
    protected List<ImageFrame> parseFrames(JsonObject json) {

        JsonObject frames = json.getAsJsonObject("frames");

        Set<String> imageKeys = frames.keySet();
        List<ImageFrame> imageFrames = new ArrayList<>();

        for (String imageKey : imageKeys) {

            JsonObject imageObject = frames.getAsJsonObject(imageKey);
            JsonObject frameObject = imageObject.getAsJsonObject("frame");
            JsonObject offsetObject = imageObject.getAsJsonObject("offset");
            JsonObject sourceSizeObject = imageObject.getAsJsonObject("sourceSize");

            ImageFrame imageFrame = new ImageFrame();

            imageFrame.name = imageKey;
            imageFrame.rectX = frameObject.get("x").getAsInt();
            imageFrame.rectY = frameObject.get("y").getAsInt();
            imageFrame.rectW = frameObject.get("w").getAsInt();
            imageFrame.rectH = frameObject.get("h").getAsInt();
            imageFrame.rotated = imageObject.has("rotated") && imageObject.get("rotated").getAsBoolean();
            imageFrame.trimmed = imageObject.has("trimmed") && imageObject.get("trimmed").getAsBoolean();
            imageFrame.offsetX = offsetObject.get("x").getAsInt();
            imageFrame.offsetY = offsetObject.get("y").getAsInt();
            imageFrame.width = sourceSizeObject.get("w").getAsInt();
            imageFrame.height = sourceSizeObject.get("h").getAsInt();

            imageFrames.add(imageFrame);

        }

        return imageFrames;

    }

}

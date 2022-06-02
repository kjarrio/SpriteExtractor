package io.github.kjarrio.extractor.parsers.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.kjarrio.extractor.objects.FrameBuilder;
import io.github.kjarrio.extractor.objects.ImageFrame;
import io.github.kjarrio.extractor.parsers.base.SheetParser;
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
            JsonObject imgObj = element.getAsJsonObject();
            ImageFrame fr = new ImageFrame(imgObj.get("filename").getAsString());
            FrameBuilder.rect(fr, imgObj.getAsJsonObject("frame"));
            FrameBuilder.rotated(fr, imgObj);
            FrameBuilder.trimmed(fr, imgObj);
            FrameBuilder.offsets(fr, imgObj.getAsJsonObject("spriteSourceSize"));
            FrameBuilder.size(fr, imgObj.getAsJsonObject("sourceSize"));
            imageFrames.add(fr);
        }

        return imageFrames;

    }

}

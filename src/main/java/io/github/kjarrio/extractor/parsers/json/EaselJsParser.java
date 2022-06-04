package io.github.kjarrio.extractor.parsers.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.github.kjarrio.extractor.objects.ImageData;
import io.github.kjarrio.extractor.objects.ImageFrame;
import io.github.kjarrio.extractor.parsers.base.SheetParser;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class EaselJsParser extends JsonHashParser implements SheetParser {

    {
        this.JSON_SCHEMA = "easeljs_schema.json";
        this.EXTENSION = "json";
    }

    @Override
    protected ImageData parseMeta(JsonObject json) {
        ImageData meta = new ImageData();
        JsonArray images = json.getAsJsonArray("images");
        meta.image = images.get(0).getAsString();
        return meta;
    }

    @Override
    protected List<ImageFrame> parseFrames(JsonObject json) {

        JsonArray frames = json.getAsJsonArray("frames");
        JsonObject animations = json.getAsJsonObject("animations");

        Set<String> imageNames = animations.keySet();
        List<ImageFrame> imageFrames = new ArrayList<>();

        int frameIndex = 0;

        for (String imageName : imageNames) {
            JsonArray frameArray = frames.get(frameIndex).getAsJsonArray();
            ImageFrame fr = new ImageFrame(imageName);
            fr.rectX = frameArray.get(0).getAsInt();
            fr.rectY = frameArray.get(1).getAsInt();
            fr.rectW = frameArray.get(2).getAsInt();
            fr.rectH = frameArray.get(3).getAsInt();
            fr.offsetX = frameArray.get(5).getAsInt();
            fr.offsetY = frameArray.get(6).getAsInt();
            imageFrames.add(fr);
            frameIndex++;
        }

        return imageFrames;

    }

}

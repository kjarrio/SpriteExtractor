package io.github.kjarrio.extractor.parsers.json;

import com.google.gson.JsonObject;
import io.github.kjarrio.extractor.objects.FrameBuilder;
import io.github.kjarrio.extractor.objects.ImageData;
import io.github.kjarrio.extractor.objects.ImageFrame;
import io.github.kjarrio.extractor.parsers.base.SheetParser;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class EgretParser extends JsonHashParser implements SheetParser {

    {
        this.JSON_SCHEMA = "egret_schema.json";
        this.EXTENSION = "json";
    }

    @Override
    protected ImageData parseMeta(JsonObject json) {
        ImageData meta = new ImageData();
        meta.image = json.get("file").getAsString();
        return meta;
    }

    protected List<ImageFrame> parseFrames(JsonObject json) {

        JsonObject frames = json.getAsJsonObject("frames");

        Set<String> imageKeys = frames.keySet();
        List<ImageFrame> imageFrames = new ArrayList<>();

        for (String name : imageKeys) {
            JsonObject imgObj = frames.getAsJsonObject(name);
            ImageFrame fr = new ImageFrame(name);
            FrameBuilder.rect(fr, imgObj);
            FrameBuilder.offsets(fr, imgObj);
            FrameBuilder.size(fr, imgObj);
            imageFrames.add(fr);
        }

        return imageFrames;

    }

}

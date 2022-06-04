package io.github.kjarrio.extractor.parsers.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.github.kjarrio.extractor.objects.ImageData;
import io.github.kjarrio.extractor.objects.ImageFrame;
import io.github.kjarrio.extractor.parsers.base.SheetParser;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class KivyParser extends JsonHashParser implements SheetParser {

    {
        this.JSON_SCHEMA = "kivy_schema.json";
        this.EXTENSION = "atlas";
    }

    @Override
    protected ImageData parseMeta(JsonObject json) {
        ImageData meta = new ImageData();
        meta.image = json.keySet().iterator().next();
        return meta;
    }

    protected List<ImageFrame> parseFrames(JsonObject json) {

        String inputImageName = json.keySet().iterator().next();

        JsonObject frames = json.getAsJsonObject(inputImageName);

        Set<String> imageKeys = frames.keySet();
        List<ImageFrame> imageFrames = new ArrayList<>();

        for (String name : imageKeys) {
            JsonArray imgArr = frames.getAsJsonArray(name);
            ImageFrame fr = new ImageFrame(name);
            fr.rectX = imgArr.get(0).getAsInt();
            fr.rectY = imgArr.get(1).getAsInt();
            fr.rectW = imgArr.get(2).getAsInt();
            fr.rectH = imgArr.get(3).getAsInt();
            imageFrames.add(fr);
        }

        return imageFrames;

    }

}

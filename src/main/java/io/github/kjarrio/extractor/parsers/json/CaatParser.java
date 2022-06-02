package io.github.kjarrio.extractor.parsers.json;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.kjarrio.extractor.objects.FrameBuilder;
import io.github.kjarrio.extractor.objects.ImageFrame;
import io.github.kjarrio.extractor.pair.ImageFramesPair;
import io.github.kjarrio.extractor.parsers.base.SheetParser;
import io.github.kjarrio.extractor.utils.FSUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CaatParser extends JsonHashParser implements SheetParser {

    {
        this.JSON_SCHEMA = "caat_schema.json";
        this.EXTENSION = "json";
    }

    @Override
    protected ImageFramesPair parse(File inputFile, File outputFolder) throws Exception {
        JsonObject json = JsonParser.parseString(FSUtils.readFile(inputFile)).getAsJsonObject();
        return new ImageFramesPair(getImageFile(inputFile), parseFrames(json));
    }

    @Override
    protected List<ImageFrame> parseFrames(JsonObject json) {

        JsonObject sprites = json.getAsJsonObject("sprites");

        Set<String> imageKeys = sprites.keySet();
        List<ImageFrame> imageFrames = new ArrayList<>();

        for (String imageName : imageKeys) {
            JsonObject imageObj = sprites.getAsJsonObject(imageName);
            ImageFrame fr = new ImageFrame(imageName);
            FrameBuilder.rect(fr, imageObj);
            imageFrames.add(fr);

        }

        return imageFrames;

    }

}

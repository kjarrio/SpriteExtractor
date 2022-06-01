package io.github.kjarrio.extractor.parsers.json;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.kjarrio.extractor.objects.ImageFrame;
import io.github.kjarrio.extractor.objects.ImageFramesPair;
import io.github.kjarrio.extractor.parsers.SheetParser;
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

        // Parse the JSON
        JsonObject json = JsonParser.parseString(readFile(inputFile)).getAsJsonObject();

        return new ImageFramesPair(getImageFile(inputFile), parseFrames(json));

    }

    @Override
    protected List<ImageFrame> parseFrames(JsonObject json) {

        JsonObject sprites = json.getAsJsonObject("sprites");

        Set<String> imageKeys = sprites.keySet();
        List<ImageFrame> imageFrames = new ArrayList<>();

        for (String imageName : imageKeys) {
            JsonObject imageObj = sprites.getAsJsonObject(imageName);
            ImageFrame imageFrame = new ImageFrame();
            imageFrame.name = imageName;
            imageFrame.rectX = imageObj.get("x").getAsInt();
            imageFrame.rectY = imageObj.get("y").getAsInt();
            imageFrame.width = imageFrame.rectW = imageObj.get("width").getAsInt();
            imageFrame.height = imageFrame.rectH = imageObj.get("height").getAsInt();
            imageFrames.add(imageFrame);

        }

        return imageFrames;

    }

}

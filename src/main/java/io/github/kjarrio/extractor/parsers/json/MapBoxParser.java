package io.github.kjarrio.extractor.parsers.json;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.kjarrio.extractor.objects.ImageFrame;
import io.github.kjarrio.extractor.pair.ImageFramesPair;
import io.github.kjarrio.extractor.parsers.base.AbstractJSONParser;
import io.github.kjarrio.extractor.parsers.base.SheetParser;
import io.github.kjarrio.extractor.utils.FSUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MapBoxParser extends AbstractJSONParser implements SheetParser {

    {
        this.JSON_SCHEMA = "mapbox_schema.json";
        this.EXTENSION = "json";
    }

    @Override
    protected ImageFramesPair parse(File inputFile, File outputFolder) throws Exception {

        String contents = FSUtils.readFile(inputFile);

        JsonObject json = JsonParser.parseString(contents).getAsJsonObject();

        // Parse the image frames
        List<ImageFrame> frames = parseFrames(json);

        // Read the input image
        File inputImage = new File(inputFile.getParentFile(), getImageFile(inputFile).getName());

        return new ImageFramesPair(inputImage, frames);

    }

    protected List<ImageFrame> parseFrames(JsonObject json) {

        Set<String> imageKeys = json.keySet();
        List<ImageFrame> imageFrames = new ArrayList<>();

        for (String name : imageKeys) {
            JsonObject imgObj = json.getAsJsonObject(name);
            ImageFrame fr = new ImageFrame(name);
            fr.rectX = imgObj.get("x").getAsInt();
            fr.rectY = imgObj.get("y").getAsInt();
            fr.rectW = fr.width = imgObj.get("width").getAsInt();
            fr.rectH = fr.height = imgObj.get("height").getAsInt();
            imageFrames.add(fr);
        }

        return imageFrames;

    }

}

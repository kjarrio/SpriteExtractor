package io.github.kjarrio.extractor.parsers.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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

public class MoleculeParser extends AbstractJSONParser implements SheetParser {

    {
        this.JSON_SCHEMA = "molecule_schema.json";
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

        JsonArray frames = json.getAsJsonArray("frames");

        List<ImageFrame> imageFrames = new ArrayList<>();

        for (JsonElement element : frames) {

            JsonObject imgObj = element.getAsJsonObject();
            ImageFrame fr = new ImageFrame(imgObj.get("name").getAsString());

            JsonObject posObj = imgObj.get("position").getAsJsonObject();
            fr.rectX = posObj.get("x").getAsInt();
            fr.rectY = posObj.get("y").getAsInt();

            JsonObject sizeObj = imgObj.get("size").getAsJsonObject();
            fr.rectW = sizeObj.get("w").getAsInt();
            fr.rectH = sizeObj.get("h").getAsInt();

            imageFrames.add(fr);

        }

        return imageFrames;

    }

}

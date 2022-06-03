package io.github.kjarrio.extractor.parsers.json;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.kjarrio.extractor.objects.ImageFrame;
import io.github.kjarrio.extractor.pair.ImageFramesPair;
import io.github.kjarrio.extractor.parsers.base.SheetParser;
import io.github.kjarrio.extractor.utils.FSUtils;
import io.github.kjarrio.extractor.utils.JsonUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Blacksmith2DParser extends JsonHashParser implements SheetParser {

    {
        this.JSON_SCHEMA = "blacksmith_2d_schema.json";
        this.EXTENSION = "json";
    }

    @Override
    protected ImageFramesPair parse(File inputFile, File outputFolder) throws Exception {
        JsonObject json = JsonParser.parseString(FSUtils.readFile(inputFile)).getAsJsonObject();
        return new ImageFramesPair(getImageFile(inputFile), parseFrames(json));
    }

    @Override
    protected List<ImageFrame> parseFrames(JsonObject json) {

        JsonObject frames = json.getAsJsonObject("frames");

        Set<String> imageKeys = frames.keySet();
        List<ImageFrame> imageFrames = new ArrayList<>();

        for (String imageName : imageKeys) {
            List<Integer> integers = JsonUtils.getIntArray(frames.getAsJsonArray(imageName));
            ImageFrame fr = new ImageFrame(imageName);
            fr.rectX = integers.get(0);
            fr.rectY = integers.get(1);
            fr.rectW = integers.get(2);
            fr.rectH = integers.get(3);
            fr.offsetX = integers.get(4);
            fr.offsetY = integers.get(5);
            fr.width = integers.get(6);
            fr.height = integers.get(7);
            imageFrames.add(fr);
        }

        return imageFrames;

    }

}

package io.github.kjarrio.extractor.parsers.json;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.kjarrio.extractor.objects.FrameBuilder;
import io.github.kjarrio.extractor.objects.ImageData;
import io.github.kjarrio.extractor.objects.ImageFrame;
import io.github.kjarrio.extractor.pair.ImageFramesPair;
import io.github.kjarrio.extractor.parsers.base.AbstractJSONParser;
import io.github.kjarrio.extractor.parsers.base.SheetParser;
import io.github.kjarrio.extractor.utils.FSUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class LayaAir2Parser extends AbstractJSONParser implements SheetParser {

    {
        this.JSON_SCHEMA = "layaair_2_schema.json";
        this.EXTENSION = "json";
    }

    @Override
    protected ImageFramesPair parse(File inputFile, File outputFolder) throws Exception {

        String contents = FSUtils.readFile(inputFile);

        JsonObject json = JsonParser.parseString(contents).getAsJsonObject();

        // Parse the meta data
        ImageData metaData = parseMeta(json);

        // Parse the image frames
        List<ImageFrame> frames = parseFrames(json);

        // Read the input image
        File inputImage = new File(inputFile.getParentFile(), metaData.image);

        return new ImageFramesPair(inputImage, frames);

    }

    protected ImageData parseMeta(JsonObject json) {

        ImageData meta = new ImageData();

        JsonObject metaJson = json.getAsJsonObject("meta");

        meta.image = metaJson.get("image").getAsString();
        meta.scale = metaJson.get("scale").getAsInt();

        return meta;

    }

    protected List<ImageFrame> parseFrames(JsonObject json) {

        JsonObject frames = json.getAsJsonObject("frames");

        Set<String> imageKeys = frames.keySet();
        List<ImageFrame> imageFrames = new ArrayList<>();

        for (String name : imageKeys) {
            JsonObject imgObj = frames.getAsJsonObject(name);
            ImageFrame fr = new ImageFrame(name);
            FrameBuilder.rect(fr, imgObj.getAsJsonObject("frame"));
            FrameBuilder.offsets(fr, imgObj.getAsJsonObject("spriteSourceSize"));
            FrameBuilder.size(fr, imgObj.getAsJsonObject("sourceSize"));
            imageFrames.add(fr);
        }

        return imageFrames;

    }

}

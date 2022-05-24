package io.github.kjarrio.extractor.parsers.other;

import io.github.kjarrio.extractor.parsers.AbstractParser;
import io.github.kjarrio.extractor.parsers.SheetParser;
import io.github.kjarrio.extractor.utils.ImageUtils;
import org.apache.commons.io.FileUtils;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UnityTexture2dParser extends AbstractParser implements SheetParser {

    @Override
    public Boolean checkType(File inputFile) {
        return inputFile.getName().toLowerCase().endsWith(".tpsheet");
    }

    private File inputFile;
    private File inputImage;
    private final List<UnityTexture> textures = new ArrayList<>();

    @Override
    public void extract(File inputFile, File outputFolder) throws Exception {

        this.inputFile = inputFile;

        FileUtils.readLines(inputFile, "UTF-8").forEach(line -> {

            if (line.startsWith("#") || line.isEmpty()) return;

            if (line.startsWith(":")) {
                parseMetaLine(line);
            } else {
                parseImageLine(line);
            }

        });

        textures.forEach(texture -> {
            try {
                BufferedImage inputImg = ImageUtils.read(inputImage);
                BufferedImage spriteImg = ImageUtils.rectangle(inputImg, texture.x, texture.y, texture.width, texture.height);
                File outputFile = new File(outputFolder, texture.name + ".png");
                ImageUtils.save(spriteImg, outputFile);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

    }

    private void parseImageLine(String line) {

        String[] parts = line.split(";");

        UnityTexture texture = new UnityTexture();
        texture.name = parts[0];
        texture.x = Integer.valueOf(parts[1]);
        texture.y = Integer.valueOf(parts[2]);
        texture.width = Integer.valueOf(parts[3]);
        texture.height = Integer.valueOf(parts[4]);

        textures.add(texture);

    }

    private void parseMetaLine(String line) {

        String[] parts = line.substring(1).split("=");

        if (parts.length != 2) return;

        String key = parts[0];
        String value = parts[1];

        switch (key) {
            case "texture":
                inputImage = new File(inputFile.getParentFile(), value);
                break;
        }

    }

    private static class UnityTexture {
        public String name;
        public Integer x;
        public Integer y;
        public Integer width;
        public Integer height;
    }

}

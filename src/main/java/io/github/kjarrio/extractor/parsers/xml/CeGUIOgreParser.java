package io.github.kjarrio.extractor.parsers.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.github.kjarrio.extractor.objects.ImageFrame;
import io.github.kjarrio.extractor.pair.ImageFramesPair;
import io.github.kjarrio.extractor.parsers.base.AbstractXMLParser;
import io.github.kjarrio.extractor.parsers.base.SheetParser;
import io.github.kjarrio.extractor.utils.FSUtils;
import io.github.kjarrio.extractor.utils.FormatUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CeGUIOgreParser extends AbstractXMLParser implements SheetParser {

    { this.EXTENSION = "xml"; }

    @Override
    public Boolean checkType(File inputFile) {
        return FormatUtils.hasString(inputFile, "Imageset", "NativeHorzRes") && super.checkType(inputFile);
    }

    @Override
    protected ImageFramesPair parse(File inputFile, File outputFolder) throws Exception {

        // Parse the XML file
        Imageset imageSet = mapper.readValue(FSUtils.readFile(inputFile), Imageset.class);

        // Input image
        File inputImage = new File(inputFile.getParentFile(), imageSet.Imagefile);

        List<ImageFrame> frames = new ArrayList<>();

        for (Imageset.Image image : imageSet.getImages()) {
            ImageFrame frame = new ImageFrame();
            frame.name = image.Name;
            frame.rectX = Integer.valueOf(image.XPos);
            frame.rectY = Integer.valueOf(image.YPos);
            frame.width = frame.rectW = Integer.valueOf(image.Width);
            frame.height = frame.rectH = Integer.valueOf(image.Height);
            frames.add(frame);
        }

        return new ImageFramesPair(inputImage, frames);

    }

    /** XML POJO for parsing the XML file. */

    @JacksonXmlRootElement(localName = "Imageset")
    private static class Imageset {

        @JacksonXmlProperty(isAttribute = true)
        public String Name = "";

        @JacksonXmlProperty(isAttribute = true)
        public String Imagefile = "";

        @JacksonXmlProperty(isAttribute = true)
        public String NativeHorzRes = "";

        @JacksonXmlProperty(isAttribute = true)
        public String NativeVertRes = "";

        @JacksonXmlProperty(isAttribute = true)
        public String AutoScaled = "";

        @JacksonXmlElementWrapper(useWrapping = false, localName = "Image")
        private final List<Image> Image = new ArrayList<>();

        public List<Imageset.Image> getImages() {
            return Image;
        }

        @JacksonXmlRootElement(localName = "Image")
        private static class Image {

            @JacksonXmlProperty(isAttribute = true)
            public String Name = "";

            @JacksonXmlProperty(isAttribute = true)
            public String XPos = "";

            @JacksonXmlProperty(isAttribute = true)
            public String YPos = "";

            @JacksonXmlProperty(isAttribute = true)
            public String Width = "";

            @JacksonXmlProperty(isAttribute = true)
            public String Height = "";

        }

    }

}

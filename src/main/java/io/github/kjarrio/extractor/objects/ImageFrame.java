package io.github.kjarrio.extractor.objects;

public class ImageFrame {

    public ImageFrame() {}

    public ImageFrame(String name) {
        this.name = name;
    }

    public String name;

    public Integer rectX;
    public Integer rectY;
    public Integer rectW;
    public Integer rectH;

    public Integer offsetX;
    public Integer offsetY;
    public Integer width;
    public Integer height;

    public Boolean rotated = false;
    public Boolean trimmed = false;

}

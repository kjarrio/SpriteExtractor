package io.github.kjarrio.extractor.pair;

import io.github.kjarrio.extractor.objects.ImageFrame;

import java.io.File;
import java.util.List;

public class ImageFramesPair {

    public final File image;
    public final List<ImageFrame> frames;

    public ImageFramesPair(File image, List<ImageFrame> frames) {
        this.image = image;
        this.frames = frames;
    }

    public File getImage() {
        return image;
    }

    public List<ImageFrame> getFrames() {
        return frames;
    }

}

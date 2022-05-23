package io.github.kjarrio.extractor.utils;

import org.imgscalr.Scalr;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageUtils {

    public static BufferedImage read(File source) throws Exception {
        return ImageIO.read(source);
    }

    public static BufferedImage rectangle(BufferedImage img, Integer x, Integer y, Integer w, Integer h) {
        return img.getSubimage(x, y, w, h);
    }

    public static BufferedImage rotate(BufferedImage img, Scalr.Rotation rotation) {
        return Scalr.rotate(img, rotation);
    }

    public static BufferedImage expand(BufferedImage img, Integer w, Integer h, Integer x, Integer y) {
        BufferedImage out = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics g = out.getGraphics();
        g.drawImage(img, x, y, null);
        g.dispose();
        return out;
    }

    public static void save(BufferedImage img, File dest) throws Exception {
        ImageIO.write(img, "PNG", dest);
    }

}

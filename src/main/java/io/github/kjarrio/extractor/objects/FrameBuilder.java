package io.github.kjarrio.extractor.objects;

import com.google.gson.JsonObject;

public class FrameBuilder {

    public static void rect(ImageFrame fr, JsonObject obj) {

        if (hasFields(obj, "x", "y")) {
            fr.rectX = get(obj, "x");
            fr.rectY = get(obj, "y");
        }

        if (hasFields(obj, "w", "h")) {
            fr.rectW = get(obj, "w");
            fr.rectH = get(obj, "h");

        } else if (hasFields(obj, "width", "height")) {
            fr.rectW = get(obj, "width");
            fr.rectH = get(obj, "height");
        }

    }

    public static void offsets(ImageFrame fr, JsonObject obj) {

        if (hasFields(obj, "x", "y")) {
            fr.offsetX = get(obj, "x");
            fr.offsetY = get(obj, "y");
        } else if (hasFields(obj, "offsetX", "offsetY")) {
            fr.offsetX = get(obj, "offsetX");
            fr.offsetY = get(obj, "offsetY");
        } else if (hasFields(obj, "offX", "offY")) {
            fr.offsetX = get(obj, "offX");
            fr.offsetY = get(obj, "offY");
        }

    }

    public static void size(ImageFrame fr, JsonObject obj) {

        if (hasFields(obj, "w", "h")) {
            fr.width = get(obj, "w");
            fr.height = get(obj, "h");

        } else if (hasFields(obj, "width", "height")) {
            fr.width = get(obj, "width");
            fr.height = get(obj, "height");

        } else if (hasFields(obj, "sourceW", "sourceH")) {
            fr.width = get(obj, "sourceW");
            fr.height = get(obj, "sourceH");

        } else {
            fr.width = fr.rectW;
            fr.height = fr.rectH;
        }

    }

    public static void rotated(ImageFrame fr, JsonObject obj) {
        fr.rotated = hasFields(obj, "rotated") && obj.get("rotated").getAsBoolean();
    }

    public static void trimmed(ImageFrame fr, JsonObject obj) {
        fr.rotated = hasFields(obj, "trimmed") && obj.get("trimmed").getAsBoolean();
    }

    private static Integer get(JsonObject obj, String f) {
        return obj.get(f).getAsInt();
    }

    private static Boolean hasFields(JsonObject obj, String... fields) {

        for (String field : fields) {
            if (!obj.has(field)) {
                return false;
            }
        }

        return true;

    }

}

package io.github.kjarrio.extractor.objects;

public final class IntPair {

    public final Integer a;
    public final Integer b;

    public IntPair(String s) {
        String[] parts = s.split(" ");
        a = Integer.valueOf(parts[0]);
        b = Integer.valueOf(parts[0]);
    }

}

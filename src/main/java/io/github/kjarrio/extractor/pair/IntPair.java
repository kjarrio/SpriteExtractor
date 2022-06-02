package io.github.kjarrio.extractor.pair;

public final class IntPair {

    public final Integer a;
    public final Integer b;

    public IntPair(String s) {
        String[] parts = s.split(" ");
        a = Integer.valueOf(parts[0]);
        b = Integer.valueOf(parts[0]);
    }

    public IntPair(Integer a, Integer b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public String toString() {
        return a + ", " + b;
    }
}

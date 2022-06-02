package io.github.kjarrio.extractor.pair;

public final class Pair<V> {

    public final V a;
    public final V b;

    public Pair(V a, V b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public String toString() {
        return a + ", " + b;
    }

}

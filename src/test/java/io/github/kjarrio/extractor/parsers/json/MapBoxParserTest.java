package io.github.kjarrio.extractor.parsers.json;

import io.github.kjarrio.extractor.parsers.AbstractParserTest;
import org.junit.jupiter.api.BeforeEach;

class MapBoxParserTest extends AbstractParserTest {

    @BeforeEach
    void setUp() {
        sheetFile = "mapbox.json";
        parser = new MapBoxParser();
    }

}
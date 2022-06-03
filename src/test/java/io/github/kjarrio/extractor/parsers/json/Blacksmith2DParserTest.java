package io.github.kjarrio.extractor.parsers.json;

import io.github.kjarrio.extractor.parsers.AbstractParserTest;
import org.junit.jupiter.api.BeforeEach;

class Blacksmith2DParserTest extends AbstractParserTest {

    @BeforeEach
    void setUp() {
        sheetFile = "blacksmith_2d.json";
        parser = new Blacksmith2DParser();
    }

}
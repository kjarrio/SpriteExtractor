package io.github.kjarrio.extractor.parsers.json;

import io.github.kjarrio.extractor.parsers.AbstractParserTest;
import org.junit.jupiter.api.BeforeEach;

class LayaAir2ParserTest extends AbstractParserTest {

    @BeforeEach
    void setUp() {
        sheetFile = "layaair_2.json";
        parser = new LayaAir2Parser();
    }

}
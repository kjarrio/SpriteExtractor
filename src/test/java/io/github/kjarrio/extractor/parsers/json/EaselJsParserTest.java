package io.github.kjarrio.extractor.parsers.json;

import io.github.kjarrio.extractor.parsers.AbstractParserTest;
import org.junit.jupiter.api.BeforeEach;

class EaselJsParserTest extends AbstractParserTest {

    @BeforeEach
    void setUp() {
        sheetFile = "easeljs.json";
        parser = new EaselJsParser();
    }

}
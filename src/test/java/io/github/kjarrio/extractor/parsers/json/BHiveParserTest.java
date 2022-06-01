package io.github.kjarrio.extractor.parsers.json;

import io.github.kjarrio.extractor.parsers.AbstractParserTest;
import org.junit.jupiter.api.BeforeEach;

class BHiveParserTest extends AbstractParserTest {

    @BeforeEach
    void setUp() {
        sheetFile = "bhive.json";
        parser = new BHiveParser();
    }

}
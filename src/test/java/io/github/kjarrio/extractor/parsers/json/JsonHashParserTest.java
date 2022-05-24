package io.github.kjarrio.extractor.parsers.json;

import io.github.kjarrio.extractor.parsers.AbstractParserTest;
import org.junit.jupiter.api.BeforeEach;

class JsonHashParserTest extends AbstractParserTest {

    @BeforeEach
    void setUp() {
        sheetFile = "json_hash.json";
        parser = new JsonHashParser();
    }

}
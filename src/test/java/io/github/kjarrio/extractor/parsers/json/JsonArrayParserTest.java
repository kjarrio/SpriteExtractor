package io.github.kjarrio.extractor.parsers.json;

import io.github.kjarrio.extractor.parsers.AbstractParserTest;
import org.junit.jupiter.api.BeforeEach;

class JsonArrayParserTest extends AbstractParserTest {

    @BeforeEach
    void setUp() {
        sheetFile = "json_array.json";
        parser = new JsonArrayParser();
    }

}
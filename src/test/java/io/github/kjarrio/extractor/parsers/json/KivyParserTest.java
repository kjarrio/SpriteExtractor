package io.github.kjarrio.extractor.parsers.json;

import io.github.kjarrio.extractor.parsers.AbstractParserTest;
import org.junit.jupiter.api.BeforeEach;

class KivyParserTest extends AbstractParserTest {

    @BeforeEach
    void setUp() {
        sheetFile = "kivy.atlas";
        parser = new KivyParser();
    }

}
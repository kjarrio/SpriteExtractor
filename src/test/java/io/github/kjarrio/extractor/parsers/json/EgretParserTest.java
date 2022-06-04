package io.github.kjarrio.extractor.parsers.json;

import io.github.kjarrio.extractor.parsers.AbstractParserTest;
import org.junit.jupiter.api.BeforeEach;

class EgretParserTest extends AbstractParserTest {

    @BeforeEach
    void setUp() {
        sheetFile = "egret.json";
        parser = new EgretParser();
    }

}
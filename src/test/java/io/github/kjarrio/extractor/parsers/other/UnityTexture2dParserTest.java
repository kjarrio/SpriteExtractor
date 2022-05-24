package io.github.kjarrio.extractor.parsers.other;

import io.github.kjarrio.extractor.parsers.AbstractParserTest;
import org.junit.jupiter.api.BeforeEach;

class UnityTexture2dParserTest extends AbstractParserTest {

    @BeforeEach
    void setUp() {
        sheetFile = "unity2d.tpsheet";
        parser = new UnityTexture2dParser();
    }

}
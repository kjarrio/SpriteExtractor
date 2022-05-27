package io.github.kjarrio.extractor.parsers.other;

import io.github.kjarrio.extractor.parsers.AbstractParserTest;
import org.junit.jupiter.api.BeforeEach;

class Toolkit2dParserTest extends AbstractParserTest {

    @BeforeEach
    void setUp() {
        sheetFile = "2d_toolkit.bytes";
        parser = new Toolkit2dParser();
    }

}
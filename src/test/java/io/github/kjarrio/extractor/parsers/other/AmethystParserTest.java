package io.github.kjarrio.extractor.parsers.other;

import io.github.kjarrio.extractor.parsers.AbstractParserTest;
import org.junit.jupiter.api.BeforeEach;

class AmethystParserTest extends AbstractParserTest {

    @BeforeEach
    void setUp() {
        sheetFile = "amethyst.ron";
        parser = new AmethystParser();
    }

}
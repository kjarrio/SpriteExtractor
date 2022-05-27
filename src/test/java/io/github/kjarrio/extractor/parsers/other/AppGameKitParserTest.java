package io.github.kjarrio.extractor.parsers.other;

import io.github.kjarrio.extractor.parsers.AbstractParserTest;
import org.junit.jupiter.api.BeforeEach;

class AppGameKitParserTest extends AbstractParserTest {

    @BeforeEach
    void setUp() {
        sheetFile = "app_game_kit.txt";
        parser = new AppGameKitParser();
    }

}
package io.github.kjarrio.extractor.parsers.json;

import io.github.kjarrio.extractor.parsers.AbstractParserTest;
import org.junit.jupiter.api.BeforeEach;

class MoleculeParserTest extends AbstractParserTest {

    @BeforeEach
    void setUp() {
        sheetFile = "molecule.json";
        parser = new MoleculeParser();
    }

}
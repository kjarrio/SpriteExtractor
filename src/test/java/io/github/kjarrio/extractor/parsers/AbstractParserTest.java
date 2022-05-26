package io.github.kjarrio.extractor.parsers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractParserTest {

    @TempDir
    protected File outputFolder;

    protected SheetParser parser;
    protected String sheetFile;
    protected File sheetDirectory = new File("src/test/resources/sheets/");

    protected void checkAllSheets() {

        assertTrue(parser.checkType(getSheet()));

        File[] files = sheetDirectory.listFiles(f -> !f.getName().endsWith(".png"));

        for (File file : files) {
            if (file.getName().equals(sheetFile)) continue;
            assertFalse(parser.checkType(file));
        }

    }

    protected File getSheet() {
        return new File(sheetDirectory, sheetFile);
    }

    @Test
    public void checkType() {
        checkAllSheets();
    }

    @Test
    public void extract() {

        try {
            parser.extract(getSheet(), outputFolder);
            File[] files = outputFolder.listFiles();
            assertNotNull(files);
            assertEquals(files.length, 8);
        } catch (Exception e) {
            assertEquals(0, 1, "Exception: " + e.getMessage());
        }

    }

}
package io.github.kjarrio.extractor.parsers;

import java.io.File;

public interface SheetParser {

    Boolean checkType(File file);
    void extract(File inputFile, File outputFolder) throws Exception;

}

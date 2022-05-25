# SpriteExtractor
Library that extracts images from sprite sheets.

Currently, in development, and supports:

* JSON (Hash)
* JSON (Array)
* Unity® - Texture2D
* 2D Toolkit

More formats will be supported soon...

**Usage:**
    
```java
import java.io.File;
import io.github.kjarrio.extractor.SpriteExtractor;

File spriteSheet = new File("spritesheet.json");
File outputDir = new File("/output/dir");

SpriteExtractor.extract(spriteSheet, outputDir);
```

License: MIT
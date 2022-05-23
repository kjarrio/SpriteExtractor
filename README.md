# SpriteExtractor
Library that extracts images from sprite sheets.

It is currently in development, and only support JSON Hash sprite sheets, but more formats will be supported soon.

**Usage:**
    
```java
import java.io.File;
import io.github.kjarrio.extractor.SpriteExtractor;

File spriteSheet = new File("spritesheet.json");
File outputDir = new File("/output/dir");

SpriteExtractor.extract(spriteSheet, outputDir);
```

License: MIT
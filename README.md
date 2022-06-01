# SpriteExtractor
Library that extracts images from sprite sheets.

It supports these formats:

* 2D Toolkit
* Amethyst
* App Game Kit
* BHive
* CAAT
* JSON (Hash)
* JSON (Array)
* MelonJS
* Unity® - Texture2D

*More formats will be supported soon...*

**Usage:**
    
```java
import java.io.File;
import io.github.kjarrio.extractor.SpriteExtractor;

File spriteSheet = new File("spritesheet.json");
File outputDir = new File("/output/dir");

SpriteExtractor.extract(spriteSheet, outputDir);
```

License: MIT
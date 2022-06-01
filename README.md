# SpriteExtractor
Java Library that extracts images from sprite sheets.

It supports these formats:

* 2D Toolkit
* Amethyst
* AppGameKit
* BHive
* CAAT
* JSON (Hash)
* JSON (Array)
* MelonJS
* Unity® - Texture2D

*More formats will be supported soon... Please see the Roadmap below for further details*

**Usage:**
    
```java
import java.io.File;
import io.github.kjarrio.extractor.SpriteExtractor;

File spriteSheet = new File("spritesheet.json");
File outputDir = new File("/output/dir");

SpriteExtractor.extract(spriteSheet, outputDir);
```

Roadmap for supporting more formats:

* BatteryTech SDK **(Work in Progress)**
* Blacksmith 2D
* CEGUI / OGRE
* cocos2d
* cocos2d-x
* CSS (responsive / retina)
* CSS (simple)
* EaselJS / CreateJS
* Egret Engine
* Gideros
* Godot 3 SpriteSheet
* Godot 3 TileSet
* Kivy
* Kwik (image sheet)
* LayaAir 2.0
* LESS (css)
* LibGDX
* libRocket RCSS
* Mapbox
* Moai
* Molecule
* MonoGame
* Orx
* Panda 2
* Phaser (JSONArray)
* Phaser (JSONHash)
* Phaser 3
* PixiJS
* PopcornFX
* SASS Mixins (css)
* Shiva3D (+ JPSprite extension)
* Slick2d
* Solar3D
* SPark AR Studio (Facebook)
* Sparrow / Starling
* Spine
* SpriteKit (Swift)
* Spriter
* SpriteStudio
* TreSensa
* UIKit (Plist)
* Unity® - JSON data (.txt)
* Unity® - Texture2D
* UnrealEngine - Paper2d
* V-Play
* WaveEngine (xml)
* x2d engine
* XAML Resource Dictionary (NoesisGUI)
* XML (generic)

License: MIT
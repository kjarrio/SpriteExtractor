# Sprite Extractor

Sprite Extractor is a Java Library that exports images from multiple formats of sprite sheets.

It currently supports these formats:

* 2D Toolkit
* Amethyst
* AppGameKit
* BHive
* CAAT
* JSON (Hash)
* JSON (Array)
* MelonJS
* Unity® - Texture2D

**It will detect the format automatically and extract to a given output folder.**

## Installation

Using Maven:

```xml
<dependency>
    <groupId>io.github.kjarrio</groupId>
    <artifactId>SpriteExtractor</artifactId>
    <version>1.1</version>
</dependency>
```

## Usage

```java
import io.github.kjarrio.extractor.SpriteExtractor;

File spriteSheet = new File("spritesheet.json");
File outputDir = new File("/output/dir");

SpriteExtractor.extract(spriteSheet, outputDir);
```
## Roadmap

* Support multiple output image formats, it currently only support PNG.

**The goal is to support as many formats as possible, such as:**

* BatteryTech SDK **(Work in Progress)**
* Blacksmith 2D
* CEGUI / OGRE
* cocos2d and cocos2d-x
* CSS Simple & (Responsive/Retina)
* EaselJS / CreateJS
* Egret Engine
* Gideros
* Godot 3 SpriteSheet and TileSet
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
* Phaser (JSONArray / JSONHash)
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
* Unity® - JSON data (txt)
* UnrealEngine - Paper2d
* V-Play
* WaveEngine (xml)
* x2d engine
* XAML Resource Dictionary (NoesisGUI)
* XML (generic)

**Future goals also include converting between different types of formats.**

## Contributing
Pull requests are welcome and please make sure to update tests as appropriate.

## Authors
[Kjartan Olason (kjarrio)](https://github.com/kjarrio)

## License
[MIT](https://choosealicense.com/licenses/mit/)
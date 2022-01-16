<a href="https://github.com/uinnn/interface-framework">
  <img align="center" src="https://img.shields.io/static/v1?style=for-the-badge&label=author&message=uinnn&color=informational"/>
</a>
<a href="https://github.com/uinnn/interface-framework">
  <img align="center" src="https://img.shields.io/static/v1?style=for-the-badge&label=version&message=1.9.0&color=yellow"/>
</a>

# interface-framework

### 1.9.0 Patch news

#### New engines:
```kt
CountEngine
CycleEngine
ToggleEngine
ProcessorEngine
RequirementEngine
FilterEngine
SorterEngine
ToggleSorterEngine
ToggleFilterEngine
```

#### New Graphical/Engine DSL examples:
```kt
val menu = filterable(title = "My Title", lines = 6, indexes = 1..1000) {
  requestIndex { value, index ->
    newEngine(Materials.SIGN, "§aValue: $value")
  }
  
  addNoFilter("Nenhum")
  addFilter("1 até 500") { it in 1..500 }
  addFilter("500 até 1000") { it in 500..1000 }
}

menu.access(player)
```


## Setup for development

### Gradle KTS
```gradle
implementation("io.github.uinnn:interface-framework:1.9.0")
```

### Gradle
```gradle
implementation 'io.github.uinnn:interface-framework:1.9.0"'
```

### Maven
```xml
<dependency>
  <groupId>io.github.uinnn</groupId>
  <artifactId>interface-framework</artifactId>
  <version>1.9.0"</version>
</dependency>
```









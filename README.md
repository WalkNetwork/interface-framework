<a href="https://github.com/uinnn/interface-framework">
  <img align="center" src="https://img.shields.io/static/v1?style=for-the-badge&label=author&message=uinnn&color=informational"/>
</a>
<a href="https://github.com/uinnn/interface-framework">
  <img align="center" src="https://img.shields.io/static/v1?style=for-the-badge&label=version&message=1.3.1v&color=ff69b4"/>
</a>
<a href="https://github.com/uinnn/interface-framework">
  <img align="center" src="https://img.shields.io/static/v1?style=for-the-badge&label=maven-central&message=1.3.1&color=orange"/>
</a>
<a href="https://github.com/uinnn/interface-framework">
  <img align="center" src="https://img.shields.io/static/v1?style=for-the-badge&label=license&message=General-Public-License&color=success"/>
</a>

# interface-framework
Is a very complete framework to create inventories in spigot with kotlin!

### Objective 📝
As you spend time programming as a Bukkit/Spigot developer, you realize that some things should be implemented to make development even easier, don't you think?
And one of those things was precisely inventories! Something of extreme importance! 
Just then, that's why I made this complete framework! Thus making life easier for many!

### Supports:
* Graphical interfaces. ✔️
* Scrollable interfaces. ✔️
* Engines. ✔️
* A lot of listeners. ✔️
* Observable bukkit-events, allowing or negating the cancellament of event. ✔️
* Schematics. ✔️
* Mappers. ✔️
* Statistics. ✔️
* Workers. ✔️

### How to use:
If you are interested how to use this framework see the [wiki](https://github.com/uinnn/interface-framework/wiki)
or see the dokka [documentation](https://uinnn.github.io/interface-framework/)

## Setup for development
The `interface-framework` is in the central maven repository. Thus making things very easy!

### Gradle Kotlin DSL
```gradle
implementation("io.github.uinnn:interface-framework:1.3.1")
```

### Gradle
```gradle
implementation 'io.github.uinnn:interface-framework:1.3.1'
```

### Maven
```xml
<dependency>
  <groupId>io.github.uinnn</groupId>
  <artifactId>interface-framework</artifactId>
  <version>1.3.1</version>
</dependency>
```

### Final notes
The `interface-framework` **NOT** contains the kotlin runtime, kotlin coroutines and others needed to run this framework,
so you should implement them directly in your project.
To make your life easier, here is all the implementation of the libraries needed to run the framework:

```gradle
plugins {
  kotlin("jvm") version "1.5.21"
  // for serializing interfaces.
  kotlin("plugin.serialization") version "1.5.21"
}

dependencies {
  implementation(kotlin("stdlib-jdk8")) // the kotlin std lib with jdk8
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.1") // the kotlin coroutines used by worker
  implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.2.2") // the kotlin serialization core 1.2.2
}
```














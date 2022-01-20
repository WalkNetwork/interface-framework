<a href="https://github.com/uinnn/interface-framework">
  <img align="center" src="https://img.shields.io/static/v1?style=for-the-badge&label=author&message=uinnn&color=informational"/>
</a>
<a href="https://github.com/uinnn/interface-framework">
  <img align="center" src="https://img.shields.io/static/v1?style=for-the-badge&label=version&message=1.9.0&color=yellow"/>
</a>

# interface-framework

### Note:
> This repository is a showcase from WalkMC Network interface-framework.
> This will not work in your server.

## Showcase:
### Filterable
```kt
val menu = filterable(title = "My Title", lines = 6, indexes = 1..1000) {
  requestIndex { value, index ->
    newEngine(Materials.SIGN, "§aValue: $value")
  }
  
  addNoFilter("None")
  addFilter("1 to 500") { it in 1..500 }
  addFilter("500 to 1000") { it in 500..1000 }
}

menu.access(player)
```

### Sortable

```kt
val menu = sortable(title = "My Title", lines = 6, indexes = 1..1000) {
  requestIndex { value, index ->
    newEngine(Materials.SIGN, "§aValue: $value")
  }
  
  addSorter("Lower quantity") { it }
  addSorterDescending("Higher quantity") { it }
}

menu.access(player)
```

### Updating

```kt
graphical(title = "My Title", lines = 6) {
  onTick { } // called every tick
  onTick(20) { } // called every 20 ticks
  onEverySecond { } // called every second (20 ticks)
  onEverySecond(5) { } // called every 5 seconds (100 ticks)
  onFirstSecond(3) { } // called once after 3 seconds (60 ticks)
  onTick(100..200) { } // called every tick in a range of 100 to 200 ticks, this is, after 100 ticks will start ticking and ending after more 100 ticks
  
  // more
}
```

### Engine
```kt
val engine = newEngine(Materials.ANDESITE, "§7Engine")
engine.onPress { } // add click listener
engine.onPress { } // add another click listener
engine.onRender { } // add render listener
engine.onTick { } // add tick listener
engine.onScroll { } // add scroll listener

// more
```



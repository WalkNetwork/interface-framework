package io.github.uinnn.interfaces

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

typealias EngineAction = Engine.() -> Unit

object Engines {
  fun empty(slot: Int = 0): Engine {
    return create(Material.AIR, slot)
  }

  fun create(material: Material, slot: Int = 0): Engine {
    return Engine(material).apply {
      this.slot = slot
    }
  }

  inline fun create(material: Material, slot: Int = 0, action: EngineAction): Engine {
    return create(material, slot).apply(action)
  }

  fun from(item: ItemStack, slot: Int = 0): Engine {
    return Engine(item).apply {
      this.slot = slot
    }
  }

  fun copy(engine: Engine): Engine {
    return engine.clone() as Engine
  }
}
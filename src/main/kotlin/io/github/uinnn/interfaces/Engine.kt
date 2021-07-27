package io.github.uinnn.interfaces

import io.github.uinnn.interfaces.common.*
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.material.MaterialData

typealias EngineStack = HashMap<Int, Engine>

open class Engine : ItemStack, Alterable, Renderable, Pressable, Visible, Metadatable, Workable {
  var graphical: GraphicalUserInterface? = null
  override var works: WorkSet = WorkSet()
  override var renders: RenderSet = RenderSet()
  override var pressSet: PressSet = PressSet()
  override var storage: Storage = Storage()
  
  var slot: Int = 0
    set(value) {
      field = value
      alter(this)
    }
  
  override var isVisible: Boolean = true
    set(value) {
      if (value) graphical?.setItem(slot, this)
      else graphical?.setItem(slot, null)
      field = value
    }
  
  constructor(type: Material) : super(type)
  constructor(data: MaterialData) : this(data.itemType, 1, data.data.toInt())
  constructor(type: Material, amount: Int) : super(type, amount)
  constructor(type: Material, amount: Int, damage: Int) : super(type, amount, damage.toShort())
  constructor(stack: ItemStack) : super(stack)
  
  override fun alter(engine: Engine): Engine {
    if (!isVisible) return this
    graphical?.setItem(slot, engine)
    return engine
  }
  
  override fun press(event: InventoryClickEvent) {
    if (graphical == null || !isVisible) return
    pressSet.forEach { press ->
      press(event, graphical!!)
    }
  }
  
  override fun render(player: Player) {
    if (graphical == null) return
    renders.forEach { render ->
      render(graphical!!, player)
    }
  }
  
  override suspend fun work() {
    if (graphical == null) return
    works.forEach { work ->
      work(graphical!!)
    }
  }
  
  fun relocate(slot: Int): Engine {
    this.slot = slot
    return this
  }
}

inline var Engine.isPersistent: Boolean
  get() = locate("Persistent") ?: false
  set(value) {
    interject("Persistent", value)
  }
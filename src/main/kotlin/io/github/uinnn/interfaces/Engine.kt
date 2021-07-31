package io.github.uinnn.interfaces

import io.github.uinnn.interfaces.interfaces.*
import org.bukkit.Material
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.material.MaterialData
import java.util.concurrent.ConcurrentHashMap
import kotlin.math.max

typealias EngineStack = ConcurrentHashMap<Int, Engine>

/**
 * A engine is a more extensible [ItemStack]. Supporting everything
 * when working with [GraphicalInterface] and [ScrollableGraphicalInterface].
 * This class supports:
 * * Renders [Renderable]
 * * Press [Pressable]
 * * Alter [Alterable]
 * * Visibility [Visible]
 * * Metadata [Metadatable]
 * * Work [Workable]
 * * Scroll [Scrollable]
 */
open class Engine : ItemStack, Alterable, Renderable, Pressable, Visible, Metadatable, Workable, Scrollable {
  var graphical: GraphicalInterface? = null
  override var works: WorkSet = WorkSet()
  override var renders: RenderSet = RenderSet()
  override var pressSet: PressSet = PressSet()
  override var storage: Storage = Storage()
  override var scrollers: ScrollSet = ScrollSet()

  var slot: Int = 0
    set(value) {
      isVisible = false
      field = value
      isVisible = true
      alter(this)
    }

  override var isVisible: Boolean = true
    set(value) {
      graphical?.let {
        it.setItem(slot, if (value) this else it.backgroundOrDefault(null))
      }
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
    presseds++
  }

  override fun render() {
    if (graphical == null) return
    renders.forEach { render ->
      render(graphical!!)
    }
    rendereds++
  }

  override suspend fun work() {
    if (graphical == null) return
    works.forEach { action ->
      action(graphical!!)
    }
  }

  override fun scroll() {
    scrollers.forEach { scroll ->
      scroll(graphical as ScrollableGraphicalInterface)
    }
    scrolleds++
  }
}

/**
 * Changes the slot of this engine and returns itself
 */
fun Engine.relocate(slot: Int): Engine = apply {
  this.slot = slot
}

/**
 * A metadata used to know if this engine is
 * persistent or not. Most commonly used in
 * [ScrollableGraphicalInterface].
 */
var Engine.isPersistent: Boolean
  get() = locate("Persistent") ?: false
  set(value) {
    interject("Persistent", value)
  }

/**
 * Returns the amount of renders that this engines makes.
 */
var Engine.rendereds: Int
  get() = locate("Rendereds") ?: 0
  set(value) {
    interject("Rendereds", max(1, value))
  }

/**
 * Returns the amount of scrolls that this engines makes.
 */
var Engine.scrolleds: Int
  get() = locate("Scrolleds") ?: 0
  set(value) {
    interject("Scrolleds", max(1, value))
  }

/**
 * Returns the amount of presseds that this engines makes.
 */
var Engine.presseds: Int
  get() = locate("Presseds") ?: 0
  set(value) {
    interject("Presseds", max(1, value))
  }

/**
 * Registers a handlers to scrollers and renders
 * of this scrollable graphical interface.
 * This will be only applied if the graphical of
 * this engine is a [ScrollableGraphicalInterface].
 */
fun Engine.onScrollAndRender(action: ScrollRenderAction) {
  onScroll { action(this) }
  onRender { action(graphical as ScrollableGraphicalInterface) }
}

/**
 * Returns a mutable engine builder of this engine.
 */
fun Engine.builder(): EngineBuilder = EngineBuilder.from(this)

/**
 * Returns a immutable engine builder of this engine.
 */
fun Engine.builderImmutable(): EngineBuilder = EngineBuilder.fromImmutable(this)

/**
 * Uninstall this engine from the graphical interface.
 */
fun Engine.uninstall() = graphical?.uninstall(slot)
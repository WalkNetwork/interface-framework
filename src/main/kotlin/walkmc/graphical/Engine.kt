package walkmc.graphical

import org.bukkit.event.inventory.*
import org.bukkit.inventory.*
import walkmc.*
import walkmc.graphical.interfaces.*
import walkmc.graphical.interfaces.Storage
import kotlin.math.*
import java.util.concurrent.*

typealias EngineStack = ConcurrentHashMap<Int, Engine>

/**
 * An engine is a more extensible [ItemStack]. Supporting everything
 * when working with [IGraphical] and [IScrollGraphical].
 *
 * This class supports:
 * * Renders: [Renderable]
 * * Press: [Pressable]
 * * Alter: [Alterable]
 * * Visibility: [Visible]
 * * Metadata: [Metadatable]
 * * Work: [Workable]
 * * Scroll: [Scrollable]
 * * Tickable: [Tickable]
 */
open class Engine : ItemStack, Alterable, Renderable, Pressable, Visible, Metadatable, Workable, Scrollable, Tickable {
   constructor(type: Materials, amount: Int = 1) : super(type.toItem(amount))
   constructor(stack: ItemStack, amount: Int = 1) : super(stack) { this.amount = amount }
   constructor(stack: ItemStack) : super(stack)
   
   open var graphical: IGraphical? = null
   
   override var works: WorkSet = LinkedHashSet()
   override var renders: RenderSet = LinkedHashSet()
   override var pressSet: PressSet = LinkedHashSet()
   override var storage: Storage = ConcurrentHashMap()
   override var scrollers: ScrollSet = LinkedHashSet()
   override var tickers: TickSet = LinkedHashSet()
   override var ticks: Int = 0
   override var tickDelay: Int = 1
   override var allowTick: Boolean = true
   override var isPressable: Boolean = false
   
   open var slot: Int = 0
      set(value) {
         if (field == value) return
         
         graphical?.let {
            it.uninstall(field)
            it.installSafe(value, this)
         }
         
         field = value
      }
   
   override var isVisible: Boolean = true
      set(value) {
         if (field == value) return
         
         graphical?.let {
            it.setItem(slot, if (value) this else it.backgroundOrDefault())
         }
         
         field = value
      }
   
   open fun handleTick() = Unit
   open fun handleClick(event: InventoryClickEvent) = Unit
   open fun handleRender() = Unit
   open fun handleScroll() = Unit
   
   @Deprecated("Workers are now replaced by Tickable.")
   open suspend fun handleWork() = Unit
   
   open fun notifyChange() {
      alter(this)
   }
   
   override fun alter(engine: Engine): Engine {
      if (!isVisible) return this
      graphical?.setItem(slot, engine)
      return engine
   }
   
   override fun alter(item: ItemStack): ItemStack {
      if (!isVisible) return this
      graphical?.setItem(slot, item)
      return item
   }
   
   override fun tick() {
      if (graphical == null || !allowTick) return
      if (ticks++ % tickDelay != 0) return
      for (act in tickers) act(graphical!!)
      handleTick()
   }
   
   override fun press(event: InventoryClickEvent) {
      if (graphical == null || !isVisible) return
      for (press in pressSet) press(event, graphical!!)
      handleClick(event)
      event.isCancelled = !isPressable
      presseds++
   }
   
   override fun render() {
      if (graphical == null) return
      rendereds++
      for (act in renders) act(graphical!!)
      handleRender()
   }
   
   override suspend fun work() {
      if (graphical == null) return
      workedAmount++
      for (act in works) act(graphical!!)
      handleWork()
   }
   
   override fun scroll() {
      if (graphical !is IScrollGraphical) return
      scrolleds++
      for (scroll in scrollers) scroll(graphical as IScrollGraphical)
      handleScroll()
   }
}

/**
 * Changes the slot of this engine and returns itself
 */
fun Engine.relocate(slot: Int): Engine {
   this.slot = slot
   return this
}

/**
 * A metadata used to know if this engine is
 * persistent or not. Most commonly used in
 * [IScrollGraphical].
 */
var Engine.isPersistent: Boolean
   get() = locate("Persistent") ?: false
   set(value) {
      interject("Persistent", value)
   }

/**
 * Returns the amount of renders that these engines makes.
 */
var Engine.rendereds: Int
   get() = locate("Rendereds") ?: 0
   set(value) {
      interject("Rendereds", max(1, value))
   }

/**
 * Returns the amount of scrolls that these engines makes.
 */
var Engine.scrolleds: Int
   get() = locate("Scrolleds") ?: 0
   set(value) {
      interject("Scrolleds", max(1, value))
   }

/**
 * Returns the amount of presseds that these engines makes.
 */
var Engine.presseds: Int
   get() = locate("Presseds") ?: 0
   set(value) {
      interject("Presseds", max(1, value))
   }

/**
 * Returns the amount of works that's this engine makes.
 */
var Engine.workedAmount: Int
   get() = locate("Works") ?: 0
   set(value) {
      interject("Works", max(1, value))
   }

/**
 * Registers a handlers to scrollers and renders
 * of this scrollable graphical interface.
 * This will be only applied if the graphical of
 * this engine is a [IScrollGraphical].
 */
fun Engine.onScrollAndRender(action: ScrollRenderAction) {
   onScroll(action)
   onRender { action(graphical as IScrollGraphical) }
}

/**
 * Uninstall this engine from the graphical interface.
 */
fun Engine.uninstall() = graphical?.uninstall(slot)

/**
 * Converts this slot to an engine.
 */
fun Slot.toEngine(): Engine {
   val engine = Engine(item)
   engine.slot = slot
   return engine
}

/**
 * Gets the player owner from the graphical owner of this engine or throws
 * an exception if the menu is not open yet or the graphical owner is null.
 */
val Engine.player get() = graphical?.owner ?: error("This menu is not open yet.")

/**
 * Adds a new render listener that's only will be executed if [rendereds] is equal to [amount].
 */
inline fun Engine.onRenderAt(amount: Int, crossinline action: RenderAction) =
   onRender { if (rendereds == amount) action() }

/**
 *  Adds a new render listener that's only will be executed in the first render
 */
inline fun Engine.onFirstRender(crossinline action: RenderAction) =
   onRender { if (rendereds == 0) action() }

/**
 *  Adds a new press listener that's only will be executed if [presseds] is equal to [amount].
 */
inline fun Engine.onPressAt(amount: Int, crossinline action: PressAction) =
   onPress { if (presseds == amount) action(graphical!!) }

/**
 * Adds a new press listener that's only will be executed in the first press
 */
inline fun Engine.onFirstPress(crossinline action: PressAction) =
   onPress { if (presseds == 0) action(graphical!!) }

/**
 * Adds a new press listener that's only will be executed
 * if the [type] is equals to the click type pressed.
 */
inline fun Engine.onPressWith(type: ClickType, crossinline action: PressAction) =
   onPress { if (click == type) action(graphical!!) }

/**
 * Adds a new press listener that's only will execute if the click type is double click.
 */
inline fun Engine.onDoubleClick(crossinline action: PressAction) =
   onPress { if (click == ClickType.DOUBLE_CLICK) action(graphical!!) }

/**
 * Adds a new press listener that's only will be executed
 * in the first press and if the click type is double click.
 */
inline fun Engine.onFirstDoubleClick(crossinline action: PressAction) =
   onPress { if (presseds == 0 && click == ClickType.DOUBLE_CLICK) action(graphical!!) }

/**
 * Adds a new press listener that's only will be executed
 * in the first press and if the click type is double click.
 */
inline fun Engine.onDoubleClickAt(amount: Int, crossinline action: PressAction) =
   onPress { if (presseds == amount && click == ClickType.DOUBLE_CLICK) action(graphical!!) }

/**
 * Adds a new press listener that's only will execute if the click type is drop.
 */
inline fun Engine.onDrop(crossinline action: PressAction) =
   onPress { if (click == ClickType.DROP) action(graphical!!) }

/**
 * Adds a new press listener that's only will be executed
 * in the first press and if the click type is drop.
 */
inline fun Engine.onFirstDrop(crossinline action: PressAction) =
   onPress { if (presseds == 0 && click == ClickType.DROP) action(graphical!!) }

/**
 * Adds a new press listener that's only will be executed
 * in the first press and if the click type is drop.
 */
inline fun Engine.onDropAt(amount: Int, crossinline action: PressAction) =
   onPress { if (presseds == amount && click == ClickType.DROP) action(graphical!!) }

/**
 * Adds a new press listener that's only will execute if the click type is middle.
 */
inline fun Engine.onMiddleClick(crossinline action: PressAction) =
   onPress { if (click == ClickType.MIDDLE) action(graphical!!) }

/**
 * Adds a new press listener that's only will be executed
 * in the first press and if the click type is middle.
 */
inline fun Engine.onFirstMiddleClick(crossinline action: PressAction) =
   onPress { if (presseds == 0 && click == ClickType.MIDDLE) action(graphical!!) }

/**
 * Adds a new press listener that's only will be executed
 * in the first press and if the click type is middle.
 */
inline fun Engine.onMiddleClickAt(amount: Int, crossinline action: PressAction) =
   onPress { if (presseds == amount && click == ClickType.MIDDLE) action(graphical!!) }

/**
 * Adds a new press listener that's only will execute if the click type is left.
 */
inline fun Engine.onLeftClick(crossinline action: PressAction) =
   onPress { if (click == ClickType.LEFT) action(graphical!!) }

/**
 * Adds a new press listener that's only will be executed
 * in the first press and if the click type is left.
 */
inline fun Engine.onFirstLeftClick(crossinline action: PressAction) =
   onPress { if (presseds == 0 && click == ClickType.LEFT) action(graphical!!) }

/**
 * Adds a new press listener that's only will be executed
 * in the first press and if the click type is left.
 */
inline fun Engine.onLeftClickAt(amount: Int, crossinline action: PressAction) =
   onPress { if (presseds == amount && click == ClickType.LEFT) action(graphical!!) }

/**
 * Adds a new press listener that's only will execute if the click type is right.
 */
inline fun Engine.onRightClick(crossinline action: PressAction) =
   onPress { if (click == ClickType.RIGHT) action(graphical!!) }

/**
 * Adds a new press listener that's only will be executed
 * in the first press and if the click type is right.
 */
inline fun Engine.onFirstRightClick(crossinline action: PressAction) =
   onPress { if (presseds == 0 && click == ClickType.RIGHT) action(graphical!!) }

/**
 * Adds a new press listener that's only will be executed
 * in the first press and if the click type is right.
 */
inline fun Engine.onRightClickAt(amount: Int, crossinline action: PressAction) =
   onPress { if (presseds == amount && click == ClickType.RIGHT) action(graphical!!) }

/**
 *  Adds a new work listener that's only will be executed if [workedAmount] is equal to [amount].
 */
@Deprecated("Workers are now replaced by Tickable.")
inline fun Engine.onWorkAt(amount: Int, crossinline action: WorkerAction) =
   onWork { if (workedAmount == amount) action() }

/**
 * Adds a new work listener that's only will be executed in the first work
 */
@Deprecated("Workers are now replaced by Tickable.")
inline fun Engine.onFirstWork(crossinline action: WorkerAction) =
   onWork { if (workedAmount == 0) action() }

/**
 * Adds a new tick listener that's will show this engine in the first [amount] of ticks
 */
fun Engine.makeVisibleAfterEvery(amount: Int) = onTick(amount) { isVisible = true }

/**
 * Adds a new tick listener that's will unshow this engine in the first [amount] of ticks
 */
fun Engine.makeInvisibleAfterEvery(amount: Int) = onTick(amount) { isVisible = true }

/**
 * Adds a new tick listener that's will show this engine in the first [amount] of ticks
 */
fun Engine.makeVisibleAfter(amount: Int) = onFirstTick(amount) { isVisible = true }

/**
 * Adds a new tick listener that's will unshow this engine in the first [amount] of ticks
 */
fun Engine.makeInvisibleAfter(amount: Int) = onFirstTick(amount) { isVisible = true }

/**
 * Adds a new tick listener that's will unshow this engine every [amount] of ticks
 */
fun Engine.toggleVisibilityEvery(amount: Int) = onTick(amount) { toggleVisibility() }

/**
 * Adds a new tick listener that's will unshow this engine every [amount] of ticks
 */
fun Engine.notifyEvery(amount: Int) = onTick(amount) { notifyChange() }

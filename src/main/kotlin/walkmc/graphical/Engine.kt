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
 */
open class Engine : ItemStack, Alterable, Renderable, Pressable, Visible, Metadatable, Workable, Scrollable {
	var graphical: IGraphical? = null
	override var works: WorkSet = LinkedHashSet()
	override var renders: RenderSet = LinkedHashSet()
	override var pressSet: PressSet = LinkedHashSet()
	override var storage: Storage = ConcurrentHashMap()
	override var scrollers: ScrollSet = LinkedHashSet()
	override var isPressable: Boolean = false
	
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
				it.setItem(slot, if (value) this else it.backgroundOrDefault())
			}
			field = value
		}
	
	constructor(type: Materials, amount: Int = 1) : super(type.toItem(amount))
	constructor(stack: ItemStack, amount: Int = 1) : super(stack) { this.amount = amount }
	
	override fun alter(engine: Engine): Engine {
		if (!isVisible) return this
		graphical?.setItem(slot, engine)
		return engine
	}
	
	override fun press(event: InventoryClickEvent) {
		if (graphical == null || !isVisible) return
		for (press in pressSet) {
			event.isCancelled = !isPressable
			press(event, graphical!!)
		}
		presseds++
	}

	override fun render() {
		if (graphical == null) return
		for (act in renders) act(graphical!!)
		rendereds++
	}
	
	override suspend fun work() {
		if (graphical == null) return
		for (act in works) act(graphical!!)
	}
	
	override fun scroll() {
		if (graphical !is IScrollGraphical) return
		for (scroll in scrollers) scroll(graphical as IScrollGraphical)
		scrolleds++
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
 * Registers a handlers to scrollers and renders
 * of this scrollable graphical interface.
 * This will be only applied if the graphical of
 * this engine is a [IScrollGraphical].
 */
fun Engine.onScrollAndRender(action: ScrollRenderAction) {
	onScroll { action(this) }
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

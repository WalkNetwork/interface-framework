package walkmc.graphical

import kotlinx.serialization.*
import org.bukkit.entity.*
import org.bukkit.inventory.*
import walkmc.*
import walkmc.graphical.common.*
import walkmc.graphical.interfaces.*
import walkmc.graphical.serializer.*
import walkmc.graphical.worker.*
import kotlin.math.*

/**
 * A graphical user interface is a more extensible [Inventory].
 * That's can, holds [Engine] (an extensible ItemStack for use with graphical interfaces),
 * can also register handlers, like access, uncess, render, works. And has
 * a custom implementation of Metadata ([Metadatable]). Also has a pre-made
 * serializer: [GraphicalSerializer].
 *
 * To create an interface:
 * ```
 * val graphical = Interfaces.create(title, lines)
 * ```
 *
 * You can also use a pre-made functions to short your code:
 * A interface that's already allows worker:
 * ```
 * val graphical = Interfaces.workable(title, lines)
 * ```
 *
 * AN interface that's holds a parent:
 * ```
 * val graphical = Interfaces.parent(title, lines, parent)
 * ```
 *
 * To create a scrollable graphical interface see [IScrollGraphical]
 * @see IScrollGraphical
 */
@Serializable(GraphicalSerializer::class)
interface IGraphical : Interface, Accessible, Renderable, Workable, Observable {
	var engineStack: EngineStack
	var worker: Worker
	
	override fun access(player: Player) {
		owner = player
		owner.closeInventory()
		
		render()
		for (accessor in accessors)
			accessor(this)
		
		worker.start()
		owner.openInventory(this)
		isOpen = true
		accesseds++
	}
	
	override fun uncess(close: Boolean) {
		for (unc in uncessors)
			unc(this)
		
		if (close)
			owner.closeInventory()
		
		worker.cancel()
		isOpen = false
		uncesseds++
	}
	
	override fun render() = renderAll()
	
	override suspend fun work() = workAll()
	
	/**
	 * Install an engine to this [IGraphical].
	 * Corresponding by the slot.
	 */
	fun install(slot: Int, engine: Engine): Engine {
		setItem(slot, engine)
		engineStack[slot] = engine
		engine.slot = slot
		engine.graphical = this
		return engine
	}
	
	/**
	 * Install an engine to this [IGraphical].
	 * Corresponding by the slot and a [AlterAction] to be triggered.
	 */
	fun install(slot: Int, engine: Engine, action: AlterAction): Engine {
		return install(slot, engine.apply(action))
	}
	
	/**
	 * Uninstalls an engine of this [IGraphical]
	 * corresponding by the slot.
	 */
	fun uninstall(slot: Int) {
		setItem(slot, null)
		engineStack.remove(slot)
	}
}

/**
 * Installs an engine to this [IGraphical] abstractly.
 */
fun IGraphical.install(engine: Engine): Engine = install(engine.slot, engine)

/**
 * Install an engine copy to this [IGraphical].
 */
fun IGraphical.installCopy(engine: Engine): Engine = install(engine.clone().toEngine())

/**
 * Installs all engines of the specified [engines]
 * to this [IGraphical].
 */
fun IGraphical.installAll(engines: Iterable<Engine>) = engines.onEach { engine ->
	install(engine)
}

/**
 * Uninstalls all engines of this [IGraphical].
 */
fun IGraphical.uninstallAll() = engineStack.onEach { engine ->
	uninstall(engine.key)
}

/**
 * Uninstalls all engines of this [IGraphical]
 * if the engine satisfies the filter.
 */
inline fun IGraphical.uninstallAll(filter: Filter<Engine>) = engineStack.onEach { engine ->
	if (filter(engine.value)) {
		uninstall(engine.key)
	}
}

/**
 * Uninstalls all not persistents engines
 * of this [IGraphical].
 */
fun IGraphical.uninstallAllNonPersistents() = uninstallAll { engine ->
	!engine.isPersistent
}

/**
 * Selects an existent engine of this [IGraphical].
 */
fun IGraphical.select(slot: Int): Engine = engineStack[slot]!!

/**
 * Selects a possible existent engine of this
 * [IGraphical], or null if the engine not exists.
 */
fun IGraphical.selectOrNull(slot: Int): Engine? = engineStack[slot]

/**
 * Selects all current engines of this [IGraphical].
 */
fun IGraphical.selectAll() = engineStack.values.toMutableList()

/**
 * Selects all current engines of this [IGraphical]
 * if the engine satisfy the filter.
 */
inline fun IGraphical.selectAll(filter: Filter<Engine>) = engineStack.values.filter(filter).toMutableList()

/**
 * Selects all current engines of this [IGraphical]
 * if the engine is persistent.
 */
fun IGraphical.selectAllPersistents() = selectAll { engine ->
	engine.isPersistent
}

/**
 * Selects all current engines of this [IGraphical]
 * if the engine is not persistent.
 */
fun IGraphical.selectAllNonPersistents() = selectAll { engine ->
	!engine.isPersistent
}

/**
 * Try locates the background storage data
 * of this [IGraphical].
 */
var IGraphical.background: Materials?
	get() = locate("Background")
	set(value) {
		if (value != null) {
			interject("Background", value)
			fill(value.data)
		}
	}

/**
 * Verifies if this [IGraphical]
 * contains any background storage data.
 */
val IGraphical.hasBackground get() = background != null

/**
 * Try locates the parent storage data
 * of this [IGraphical].
 */
var IGraphical.parent: IGraphical?
	get() = locate("Parent")
	set(value) {
		if (value != null) {
			interject("Parent", value)
		}
	}

/**
 * Verifies if this [IGraphical]
 * contains any parent storage data.
 */
val IGraphical.hasParent get() = parent != null

/**
 * Renders all engines of this [IGraphical]
 * to a specified player.
 */
fun IGraphical.renderEngines() {
	for (engine in selectAll()) engine.render()
}

/**
 * Works all engines of this [IGraphical].
 */
suspend fun IGraphical.workEngines() {
	for (engine in selectAll()) engine.work()
}

/**
 * Renders this [IGraphical] to a specified player.
 */
fun IGraphical.renderInterface() {
	for (rnd in renders)
		rnd()
	
	rendereds++
}

/**
 * Works this [IGraphical].
 */
suspend fun IGraphical.workInterface() {
	for (work in works)
		work()
}

/**
 * Renders everything of this [IGraphical]
 * to a specified player. This includes the interface and engines.
 */
fun IGraphical.renderAll() {
	renderInterface()
	renderEngines()
}

/**
 * Works everything of this [IGraphical].
 * This includes the interface and engines.
 */
suspend fun IGraphical.workAll() {
	workInterface()
	workEngines()
}

/**
 * Converts this [IGraphical]
 * in to a [IScrollGraphical].
 */
fun IGraphical.asScrollable(): IScrollGraphical = this as IScrollGraphical

/**
 * Gets the background as icon if not null or default.
 */
fun IGraphical.backgroundOrDefault(default: ItemStack? = null): ItemStack? {
	return background?.toItem() ?: default
}

/**
 * Returns the accesseds amount of this graphical interface.
 */
var IGraphical.accesseds: Int
	get() = locate("Accesseds") ?: 0
	set(value) {
		interject("Accesseds", max(1, value))
	}

/**
 * Returns the uncesseds amount of this graphical interface.
 */
var IGraphical.uncesseds: Int
	get() = locate("Uncesseds") ?: 0
	set(value) {
		interject("Uncesseds", max(1, value))
	}

/**
 * Returns the amount of renders that these engines makes.
 */
var IGraphical.rendereds: Int
	get() = locate("Rendereds") ?: 0
	set(value) {
		interject("Rendereds", max(1, value))
	}

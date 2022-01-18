package walkmc.graphical

import org.bukkit.*
import org.bukkit.entity.*
import org.bukkit.inventory.*
import walkmc.graphical.interfaces.*
import walkmc.graphical.worker.*
import java.util.concurrent.*

/**
 * AN abstract implementation of a [IGraphical].
 */
abstract class Graphical(title: String, lines: Int) : IGraphical {
	override var model: Inventory = Bukkit.createInventory(this, lines * 9, title)
	override var engineStack: EngineStack = EngineStack()
	override var worker: Worker = AsyncWorker(this)
	override var works: WorkSet = LinkedHashSet()
	override var renders: RenderSet = LinkedHashSet()
	override var accessors: AccessSet = LinkedHashSet()
	override var uncessors: AccessSet = LinkedHashSet()
	override var storage: Storage = ConcurrentHashMap()
	override var observers: Observers = Observers()
	override lateinit var owner: Player
	override var isOpen: Boolean = false
	
	override var tickers: TickSet = LinkedHashSet()
	override var ticks: Int = 0
	override var tickDelay: Int = 1
	override var allowTick: Boolean = true
	
	init {
		defaultObservers()
		fillBackground()
	}
}

/**
 * A standard implementation of a [IGraphical].
 */
class StandardGraphical(title: String, lines: Int) : Graphical(title, lines)

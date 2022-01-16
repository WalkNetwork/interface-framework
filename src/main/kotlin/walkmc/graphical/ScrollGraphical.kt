package walkmc.graphical

import org.bukkit.*
import org.bukkit.entity.*
import org.bukkit.inventory.*
import walkmc.graphical.common.*
import walkmc.graphical.interfaces.*
import walkmc.graphical.mapper.*
import walkmc.graphical.schema.*
import walkmc.graphical.worker.*
import java.util.*
import java.util.concurrent.*

/**
 * A abstract implementation of a [IScrollGraphical].
 */
abstract class ScrollGraphical(title: String, lines: Int) : IScrollGraphical {
	override var model: Inventory = Bukkit.createInventory(this, lines * 9, title)
	override var engineStack: EngineStack = EngineStack()
	override var worker: Worker = AsyncWorker(this)
	override var works: WorkSet = LinkedHashSet()
	override var renders: RenderSet = LinkedHashSet()
	override var accessors: AccessSet = LinkedHashSet()
	override var uncessors: AccessSet = LinkedHashSet()
	override var scrollers: ScrollSet = LinkedHashSet()
	override var storage: Storage = ConcurrentHashMap()
	override var observers: Observers = Observers()
	override var scrollDownEngine: Engine = defaultScrollDownEngine()
	override var scrollUpEngine: Engine = defaultScrollUpEngine()
	override var schema: Schema = StandardSchema()
	override var source: Source = LinkedList()
	override var page: Int = 1
	override var mapper: Mapper = PartialMapper
	override lateinit var owner: Player
	override var isOpen: Boolean = false
	
	override var tickers: TickSet = LinkedHashSet()
	override var ticks: Int = 0
	override var tickDelay: Int = 1
	override var allowTick: Boolean = true
	
	init {
		defaultObservers()
		if (hasBackground)
			fill(background!!.data)
	}
}

/**
 * A standard implementation of a [IScrollGraphical].
 */
class StandardScrollGraphical(title: String, lines: Int) : ScrollGraphical(title, lines)

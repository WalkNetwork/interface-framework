package walkmc.graphical

import org.bukkit.*
import org.bukkit.entity.*
import org.bukkit.inventory.*
import walkmc.*
import walkmc.extensions.*
import walkmc.graphical.common.*
import walkmc.graphical.engines.*
import walkmc.graphical.interfaces.*
import walkmc.graphical.interfaces.Storage
import walkmc.graphical.mapper.*
import walkmc.graphical.schema.*
import walkmc.graphical.worker.*
import java.util.concurrent.*

/**
 * An abstract implementation of a [IScrollGraphical].
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
	
	override var scrollDownEngine: ScrollDownEngine =
		newItem(Materials.ARROW, "§c◀ Voltar", listOf("§7Clique para voltar á página anterior."))
			.toScrollDownEngine(startSlot(lines))
	
	override var scrollUpEngine: ScrollUpEngine =
		newItem(Materials.ARROW, "§aAvançar ▶", listOf("§7Clique para ir á próxima página."))
			.toScrollUpEngine(lastSlot(lines))
	
	override var schema: Schema = basicSchema()
	override var source: Source = Source(this)
	override var isDirty: Boolean = true
	override var page: Int = 1
	override var pages: Scrollers = emptyList()
	override var mapper: Mapper = PartialMapper
	override lateinit var owner: Player
	override var isOpen: Boolean = false
	override var scrolleds: Int = 0
	
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
 * A standard implementation of a [IScrollGraphical].
 */
class StandardScrollGraphical(title: String, lines: Int) : ScrollGraphical(title, lines)

package walkmc.graphical

import walkmc.*
import walkmc.graphical.common.*
import walkmc.graphical.interfaces.*
import walkmc.graphical.mapper.*
import walkmc.graphical.schema.*
import kotlin.math.*

typealias Scrollers = List<List<Engine>>
typealias Source = MutableList<Engine>
typealias ScrollRenderAction = Action<IScrollGraphical>

/**
 * Represents a scrollable graphical interface. This interface
 * can map all [source] with [schema] and [mapper].
 * This is equals to a paginated inventory. This not stores all pages
 * in a separed graphical interface in a list, this map in runtime
 * all [source] and the [schema] and [mapper] will decide
 * when showing a engine.
 */
interface IScrollGraphical : IGraphical, Scrollable {
	
	/**
	 * Represents a engine used to scroll down
	 * this scrollable graphical interface.
	 */
	var scrollDownEngine: Engine
	
	/**
	 * Represents a engine used to scroll up
	 * this scrollable graphical interface.
	 */
	var scrollUpEngine: Engine
	
	/**
	 * The schematic used for mapping all scrollable engines
	 * of this scrollable graphical interface.
	 */
	var schema: Schema
	
	/**
	 * All possible scrollable engines. This is used to map
	 * all engines in this scrollable graphical interface.
	 * ### Note:
	 * This is different of [engineStack] because engineStack just
	 * stores all current engines, so for example, if i have a scrollable
	 * graphical interface with two pages and my current page is one, the
	 * [engineStack] only will shows the engines of my current page, while
	 * [source] will show all pages.
	 * This not includes [scrollUpEngine] and [scrollDownEngine] engine.
	 * To not includes a scrollable engine in the mapper, just adds the
	 * [isPersistent] metadata to a engine.
	 */
	var source: Source
	
	/**
	 * A mapper to order all [source].
	 */
	var mapper: Mapper
	
	/**
	 * The current page of this [IScrollGraphical]
	 */
	var page: Int
	
	/**
	 * Scrolls this [IScrollGraphical] to a certain page.
	 * If a [to] is less than 0, equals than [page] or is greather
	 * than [scrollSize] nothing will be do.
	 */
	fun scrollTo(to: Int = 1) {
		// fast non possible or equals scroll
		if (to < 1 || hasScrolled && to > scrollSize)
			return
		
		// evict out of index error
		if (source.isEmpty()) {
			uninstallAllNonPersistents()
			return
		}
		
		val mapped = mapper.map(this, source)
		
		page = to
		pages = mapped
		
		val engines = currentEngines
		if (hasScrolled) uninstallAllNonPersistents()
		
		val limit = installPerScroll
		var installed = 0
		for (index in schema) {
			if (installed >= limit || installed >= engines.size)
				break
			
			install(index, engines[installed])
			installed++
		}
		
		if (!hasScrolled) {
			install(scrollUpEngine)
			install(scrollDownEngine)
		}
		
		scrolleds++
		scrollAll()
	}
	
	override fun scroll() {
		for (scroll in scrollers)
			scroll(this)
	}
	
	override fun install(slot: Int, engine: Engine): Engine {
		return super.install(slot, engine).apply {
			graphical = this@IScrollGraphical
		}
	}
	
	override fun install(slot: Int, engine: Engine, action: AlterAction): Engine {
		return super.install(slot, engine, action).apply {
			graphical = this@IScrollGraphical
		}
	}
}

/**
 * Scrolls up this scrollable graphical interface.
 */
fun IScrollGraphical.scrollUp() = scrollTo(next)

/**
 * Scrolls down this scrollable graphical interface.
 */
fun IScrollGraphical.scrollDown() = scrollTo(previous)

/**
 * Updates the current page of this scrollable graphical interface.
 */
fun IScrollGraphical.updatePage() = scrollTo(page)

/**
 * Triggers all scrollers handlers. Including
 * [IScrollGraphical] scrolls handlers and
 * engines scrolls handlers
 */
fun IScrollGraphical.scrollAll() {
	scroll()
	for (engine in engineStack.values) engine.scroll()
}

/**
 * Returns all pages item of this
 * paginated graphical interface.
 */
var IScrollGraphical.pages: Scrollers
	get() = locate("Pages") ?: listOf()
	set(value) {
		interject("Pages", value)
	}

/**
 * Returns all current mappable engines of
 * this scrollable graphical interface
 */
inline val IScrollGraphical.currentEngines get() = pages[page - 1]

/**
 * Returns the amount of pages thats this
 * scrollable graphical interface contains.
 */
inline val IScrollGraphical.scrollSize get() = pages.size

/**
 * Returns if this scrollable graphical interface
 * is in the first page.
 */
inline val IScrollGraphical.isFirstScroll get() = page <= 1

/**
 * Returns the scroll page that is the previous of the current.
 */
inline val IScrollGraphical.previous get() = max(1, page - 1)

/**
 * Returns the scroll page that is the next of the current.
 */
inline val IScrollGraphical.next get() = min(scrollSize, page + 1)

/**
 * Returns if this scrollable graphical interface
 * is in the last page.
 */
inline val IScrollGraphical.isLastScroll get() = page >= pages.size

/**
 * Gets the amount of this scrollable
 * graphical interface draws.
 */
var IScrollGraphical.scrolleds: Int
	get() = locate("Scrolleds") ?: 0
	set(value) {
		interject("Scrolleds", max(1, value))
	}

/**
 * Returns if this scrollable graphical interface never drewed.
 */
inline val IScrollGraphical.hasScrolled get() = scrolleds >= 1

/**
 * Returns the amount of engines that a scrollable
 * graphical interface can install per page.
 */
val IScrollGraphical.installPerScroll: Int
	get() = schema.count()

/**
 * Returns the last mappable column of this scrollable graphical interface.
 */
inline val IScrollGraphical.lastSlot get() = schema.last

/**
 * Returns the first mappable column of this scrollable graphical interface.
 */
inline val IScrollGraphical.startSlot get() = schema.start

/**
 * Registers a handlers to scrollers and renders
 * of this [IScrollGraphical].
 */
fun IScrollGraphical.onScrollAndRender(action: ScrollRenderAction) {
	onScroll { action(this) }
	onRender { action(this@onScrollAndRender) }
}

/**
 * Creates a new default scroll down engine. Thats contains the
 * basic funcionality to scrolling down a [IScrollGraphical].
 */
fun IScrollGraphical.defaultScrollDownEngine(): Engine {
	return buildEngine(Materials.ARROW, "§c◀ Voltar", listOf("§7Clique para voltar á página anterior.")) {
		slot = startSlot(lines)
		isPersistent = true
		onPress { scrollDown() }
		onScroll { turnVisibility(!isFirstScroll) }
	}
}

/**
 * Creates a new default scroll up engine. Thats contains the
 * basic funcionality to scrolling up a [IScrollGraphical].
 */
fun IScrollGraphical.defaultScrollUpEngine(): Engine {
	return buildEngine(Materials.ARROW, "§aAvançar ▶", listOf("§7Clique para ir á próxima página.")) {
		slot = lastSlot(lines)
		isPersistent = true
		onPress { scrollUp() }
		onScroll { turnVisibility(!isLastScroll) }
	}
}

package io.github.uinnn.interfaces

import io.github.uinnn.interfaces.common.Action
import io.github.uinnn.interfaces.common.lastSlot
import io.github.uinnn.interfaces.common.startSlot
import io.github.uinnn.interfaces.interfaces.AlterAction
import io.github.uinnn.interfaces.interfaces.Scrollable
import io.github.uinnn.interfaces.mapper.Mapper
import io.github.uinnn.interfaces.schematic.Schematic
import io.github.uinnn.interfaces.schematic.isInRange
import org.bukkit.Material
import java.util.*
import kotlin.math.max
import kotlin.math.min

typealias Scrollers = List<List<Engine>>
typealias ScrollableEngines = LinkedList<Engine>
typealias ScrollRenderAction = Action<ScrollableGraphicalInterface>

/**
 * Represents a scrollable graphical interface. This interface
 * can map all [scrollableEngines] with [schematic] and [mapper].
 * This is equals to a paginated inventory. This not stores all pages
 * in a separed graphical interface in a list, this map in runtime
 * all [scrollableEngines] and the [schematic] and [mapper] will decide
 * when showing a engine.
 */
interface ScrollableGraphicalInterface : GraphicalInterface, Scrollable {

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
  var schematic: Schematic

  /**
   * All possible scrollable engines. This is used to map
   * all engines in this scrollable graphical interface.
   * ### Note:
   * This is different of [engineStack] because engineStack just
   * stores all current engines, so for example, if i have a scrollable
   * graphical interface with two pages and my current page is one, the
   * [engineStack] only will shows the engines of my current page, while
   * [scrollableEngines] will show all pages.
   * This not includes [scrollUpEngine] and [scrollDownEngine] engine.
   * To not includes a scrollable engine in the mapper, just adds the
   * [isPersistent] metadata to a engine.
   */
  var scrollableEngines: ScrollableEngines

  /**
   * A mapper to order all [scrollableEngines].
   */
  var mapper: Mapper

  /**
   * The current page of this [ScrollableGraphicalInterface]
   */
  var page: Int

  /**
   * Scrolls this [ScrollableGraphicalInterface] to a certain page.
   * If a [to] is less than 0, equals than [page] or is greather
   * than [scrollSize] nothing will be do.
   */
  fun scrollTo(to: Int = 1) {
    // fast non possible or equals scroll
    if (to < 1 || hasScrolled && (to > scrollSize || page == to))
      return

    page = to
    val mapped = mapper.map(this, scrollableEngines)
    pages = mapped
    val engines = currentEngines
    if (hasScrolled) uninstallAllNonPersistents()

    val limit = installPerScroll
    var installed = 0
    for (index in 0 until size) {
      if (schematic.isInRange(index)) {
        if (installed >= limit || installed >= engines.size)
          break
        install(index, engines[installed])
        installed++
      }
    }

    if (!hasScrolled) {
      install(scrollUpEngine)
      install(scrollDownEngine)
    }

    scrolleds++
    scrollAll()
  }

  override fun scroll() {
    scrollers.forEach { scroll ->
      scroll(this)
    }
  }

  override fun install(slot: Int, engine: Engine): Engine {
    return super.install(slot, engine).apply {
      graphical = this@ScrollableGraphicalInterface
    }
  }

  override fun install(slot: Int, engine: Engine, action: AlterAction): Engine {
    return super.install(slot, engine, action).apply {
      graphical = this@ScrollableGraphicalInterface
    }
  }
}

/**
 * Scrolls up this scrollable graphical interface.
 */
fun ScrollableGraphicalInterface.scrollUp() = scrollTo(next)

/**
 * Scrolls down this scrollable graphical interface.
 */
fun ScrollableGraphicalInterface.scrollDown() = scrollTo(previous)

/**
 * Triggers all scrollers handlers. Including
 * [ScrollableGraphicalInterface] scrolls handlers and
 * engines scrolls handlers
 */
fun ScrollableGraphicalInterface.scrollAll() {
  scroll()
  engineStack.values.forEach { engine ->
    engine.scroll()
  }
}

/**
 * Returns all pages item of this
 * paginated graphical interface.
 */
var ScrollableGraphicalInterface.pages: Scrollers
  get() = locate("Pages") ?: listOf()
  set(value) {
    interject("Pages", value)
  }

/**
 * Returns all current mappable engines of
 * this scrollable graphical interface
 */
inline val ScrollableGraphicalInterface.currentEngines get() = pages[page - 1]

/**
 * Returns the amount of pages thats this
 * scrollable graphical interface contains.
 */
inline val ScrollableGraphicalInterface.scrollSize get() = pages.size

/**
 * Returns if this scrollable graphical interface
 * is in the first page.
 */
inline val ScrollableGraphicalInterface.isFirstScroll get() = page <= 1

/**
 * Returns the scroll page that is the previous of the current.
 */
inline val ScrollableGraphicalInterface.previous get() = max(1, page - 1)

/**
 * Returns the scroll page that is the next of the current.
 */
inline val ScrollableGraphicalInterface.next get() = min(scrollSize, page + 1)

/**
 * Returns if this scrollable graphical interface
 * is in the last page.
 */
inline val ScrollableGraphicalInterface.isLastScroll get() = page >= pages.size

/**
 * Gets the amount of this scrollable
 * graphical interface draws.
 */
var ScrollableGraphicalInterface.scrolleds: Int
  get() = locate("Scrolleds") ?: 0
  set(value) {
    interject("Scrolleds", max(1, value))
  }

/**
 * Returns if this scrollable graphical interface never drewed.
 */
inline val ScrollableGraphicalInterface.hasScrolled get() = scrolleds >= 1

/**
 * Returns the amount of engines that a scrollable
 * graphical interface can install per page.
 */
val ScrollableGraphicalInterface.installPerScroll: Int get() {
  return (lastSlot - (schematic.exclude.size - 1) - startSlot) + schematic.include.size
}

/**
 * Returns the last mappable column of this scrollable graphical interface.
 */
inline val ScrollableGraphicalInterface.lastSlot get() = schematic.last

/**
 * Returns the first mappable column of this scrollable graphical interface.
 */
inline val ScrollableGraphicalInterface.startSlot get() = schematic.start

/**
 * Registers a handlers to scrollers and renders
 * of this [ScrollableGraphicalInterface].
 */
fun ScrollableGraphicalInterface.onScrollAndRender(action: ScrollRenderAction) {
  onScroll { action(this) }
  onRender { action(this@onScrollAndRender) }
}

/**
 * Creates a new default scroll down engine. Thats contains the
 * basic funcionality to scrolling down a [ScrollableGraphicalInterface].
 */
fun ScrollableGraphicalInterface.defaultScrollDownEngine(): Engine {
  return EngineBuilder.of(Material.ARROW).apply {
    name("§c« Back")
    lore("§7Click to scroll down.")
    transform {
      slot = startSlot(lines)
      isPersistent = true
      onPress { scrollDown() }
      onScroll { turn(!isFirstScroll) }
    }
  }.build()
}

/**
 * Creates a new default scroll up engine. Thats contains the
 * basic funcionality to scrolling up a [ScrollableGraphicalInterface].
 */
fun ScrollableGraphicalInterface.defaultScrollUpEngine(): Engine {
  return EngineBuilder.of(Material.ARROW).apply {
    name("§aNext »")
    lore("§7Click to scroll up.")
    transform {
      slot = lastSlot(lines)
      isPersistent = true
      onPress { scrollUp() }
      onScroll { turn(!isLastScroll) }
    }
  }.build()
}



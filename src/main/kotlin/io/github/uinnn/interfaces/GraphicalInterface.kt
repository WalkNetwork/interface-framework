package io.github.uinnn.interfaces

import io.github.uinnn.interfaces.common.Filter
import io.github.uinnn.interfaces.common.fill
import io.github.uinnn.interfaces.interfaces.*
import io.github.uinnn.interfaces.serializer.GraphicalInterfaceSerializer
import io.github.uinnn.interfaces.worker.Worker
import kotlinx.serialization.Serializable
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.material.MaterialData

/**
 * A graphical user interface is a more extensible [Inventory].
 * Thats can, holds [Engine] (a extensible ItemStack for use with graphical interfaces),
 * can also register handlers, like access, uncess, render, works. And has
 * a custom implementation of Metadata ([Metadatable]). Also has a pre-made
 * serializer: [GraphicalInterfaceSerializer].
 *
 * To create a interface:
 * ```
 * val graphical = Interfaces.create(title, lines)
 * ```
 *
 * You can also uses a pre-made functions to short your code:
 * A interface thats already allows worker:
 * ```
 * val graphical = Interfaces.workable(title, lines)
 * ```
 *
 * A interface thats holds a parent:
 * ```
 * val graphical = Interfaces.parent(title, lines, parent)
 * ```
 *
 * To create a scrollable graphical interface see [ScrollableGraphicalInterface]
 * @see ScrollableGraphicalInterface
 */
@Serializable(GraphicalInterfaceSerializer::class)
interface GraphicalInterface : Interface, Accessible, Renderable, Workable, Observable {
  var engineStack: EngineStack
  var worker: Worker

  override fun access(player: Player) {
    owner = player
    owner.closeInventory()
    render()
    accessors.forEach { accessor ->
      accessor(this)
    }
    if (isClosed) worker.start()
    owner.openInventory(this)
    isOpen = true
  }

  override fun uncess(close: Boolean) {
    uncessors.forEach { uncessor ->
      uncessor(this)
    }
    if (close) owner.closeInventory()
    worker.cancel()
    isOpen = false
  }

  override fun render() {
    renderAll()
  }

  override suspend fun work() {
    workAll()
  }

  /**
   * Install a engine to this [GraphicalInterface].
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
   * Install a engine to this [GraphicalInterface].
   * Corresponding by the slot and a [AlterAction] to be triggered.
   */
  fun install(slot: Int, engine: Engine, action: AlterAction): Engine {
    return install(slot, engine).apply(action)
  }

  /**
   * Uninstalls a engine of this [GraphicalInterface]
   * corresponding by the slot.
   */
  fun uninstall(slot: Int) {
    setItem(slot, null)
    engineStack.remove(slot)
  }
}

/**
 * Installs a engine to this [GraphicalInterface] abstractly.
 */
fun GraphicalInterface.install(engine: Engine): Engine = install(engine.slot, engine)

/**
 * Install e engine copy to this [GraphicalInterface].
 */
fun GraphicalInterface.installCopy(engine: Engine): Engine = install(Engines.copy(engine))

/**
 * Installs all engines of a collection
 * to this [GraphicalInterface].
 */
fun GraphicalInterface.installAll(engines: Collection<Engine>) = engines.onEach { engine ->
  install(engine)
}

/**
 * Uninstalls all engines of this [GraphicalInterface].
 */
fun GraphicalInterface.uninstallAll() = engineStack.onEach { engine ->
  uninstall(engine.key)
}

/**
 * Uninstalls all engines of this [GraphicalInterface]
 * if the engine satisfies the filter.
 */
fun GraphicalInterface.uninstallAll(filter: Filter<Engine>) = engineStack.onEach { engine ->
  if (filter(engine.value)) {
    uninstall(engine.key)
  }
}

/**
 * Uninstalls all not persistents engines
 * of this [GraphicalInterface].
 */
fun GraphicalInterface.uninstallAllNonPersistents() = uninstallAll { engine ->
  !engine.isPersistent
}

/**
 * Selects a existent engine of this [GraphicalInterface].
 */
fun GraphicalInterface.select(slot: Int): Engine = engineStack[slot]!!

/**
 * Selects a possible existent engine of this
 * [GraphicalInterface], or null if the engine not exists.
 */
fun GraphicalInterface.selectOrNull(slot: Int): Engine? = engineStack[slot]

/**
 * Selects all current engines of this [GraphicalInterface].
 */
fun GraphicalInterface.selectAll() = engineStack.values.toMutableList()

/**
 * Selects all current engines of this [GraphicalInterface]
 * if the engine satisfy the filter.
 */
fun GraphicalInterface.selectAll(filter: Filter<Engine>) = engineStack.values.filter(filter).toMutableList()

/**
 * Selects all current engines of this [GraphicalInterface]
 * if the engine is persistent.
 */
fun GraphicalInterface.selectAllPersistents() = selectAll { engine ->
  engine.isPersistent
}

/**
 * Selects all current engines of this [GraphicalInterface]
 * if the engine is not persistent.
 */
fun GraphicalInterface.selectAllNonPersistents() = selectAll { engine ->
  !engine.isPersistent
}

/**
 * Try locates the background storage data
 * of this [GraphicalInterface].
 */
inline var GraphicalInterface.background: MaterialData?
  get() = locate("Background")
  set(value) {
    if (value != null) {
      interject("Background", value)
      fill(value)
    }
  }

/**
 * Verifies if this [GraphicalInterface]
 * contains any background storage data.
 */
inline val GraphicalInterface.hasBackground get() = background != null

/**
 * Try locates the parent storage data
 * of this [GraphicalInterface].
 */
inline var GraphicalInterface.parent: GraphicalInterface?
  get() = locate("Parent")
  set(value) {
    if (value != null) {
      interject("Parent", value)
    }
  }

/**
 * Verifies if this [GraphicalInterface]
 * contains any parent storage data.
 */
inline val GraphicalInterface.hasParent get() = parent != null

/**
 * Renders all engines of this [GraphicalInterface]
 * to a specified player.
 */
fun GraphicalInterface.renderEngines() {
  selectAll().forEach { engine ->
    engine.render()
  }
}

/**
 * Works all engines of this [GraphicalInterface].
 */
suspend fun GraphicalInterface.workEngines() {
  selectAll().forEach { engine ->
    engine.work()
  }
}

/**
 * Renders this [GraphicalInterface] to a specified player.
 */
fun GraphicalInterface.renderInterface() {
  renders.forEach { action ->
    action(this)
  }
}

/**
 * Works this [GraphicalInterface].
 */
suspend fun GraphicalInterface.workInterface() {
  works.forEach { action ->
    action(this)
  }
}

/**
 * Renders everything of this [GraphicalInterface]
 * to a specified player. This includes the interface and engines.
 */
fun GraphicalInterface.renderAll() {
  renderInterface()
  renderEngines()
}

/**
 * Works everything of this [GraphicalInterface].
 * This includes the interface and engines.
 */
suspend fun GraphicalInterface.workAll() {
  workInterface()
  workEngines()
}

/**
 * Copies a [GraphicalInterface].
 */
@Deprecated("Requires setting engines in the interface")
fun GraphicalInterface.copy(): GraphicalInterface {
  val copy = Interfaces.create(title, lines)
  copy.owner = owner
  copy.model = model
  copy.engineStack = engineStack
  copy.works = works
  copy.renders = renders
  copy.accessors = accessors
  copy.uncessors = uncessors
  copy.storage = storage
  copy.observers = observers
  return copy
}

/**
 * Converts this [GraphicalInterface]
 * in to a [ScrollableGraphicalInterface].
 */
fun GraphicalInterface.asScrollable(): ScrollableGraphicalInterface = this as ScrollableGraphicalInterface

/**
 * Gets the background as icon if not null or default.
 */
fun GraphicalInterface.backgroundOrDefault(default: ItemStack?): ItemStack? {
  return if (hasBackground) background!!.toItemStack(1) else default
}

package io.github.uinnn.interfaces

import io.github.uinnn.interfaces.common.Filter
import io.github.uinnn.interfaces.common.fill
import io.github.uinnn.interfaces.interfaces.*
import io.github.uinnn.interfaces.serializer.GraphicalInterfaceSerializer
import io.github.uinnn.interfaces.worker.Worker
import kotlinx.serialization.Serializable
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
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
 * A interface thats holds a owner:
 * ```
 * val graphical = Interfaces.ownerable(title, lines, player)
 * ```
 *
 * A interface thats holds a parent:
 * ```
 * val graphical = Interfaces.parent(title, lines, parent)
 * ```
 */
@Serializable(GraphicalInterfaceSerializer::class)
interface GraphicalUserInterface : UserInterface, Accessible, Renderable, Metadatable, Workable, Observable {
  var engineStack: EngineStack
  var worker: Worker
  
  override fun access(player: Player) {
    player.closeInventory()
    render(player)
    accessors.forEach { accessor ->
      accessor(this, player)
    }
    if (!isOpen) worker.launch()
    users.add(player)
    player.openInventory(this)
  }
  
  override fun uncess(player: Player, close: Boolean) {
    uncessors.forEach { uncessor ->
      uncessor(this, player)
    }
    if (close) player.closeInventory()
    users.remove(player)
    if (!isOpen) worker.resume()
  }
  
  override fun render(player: Player) {
    renderAll(player)
  }

  override suspend fun work() {
    workAll()
  }
  
  /**
   * Install a engine to this graphical user interface.
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
   * Install a engine to this graphical user interface.
   * Corresponding by the slot and a [AlterAction] to be triggered.
   */
  fun install(slot: Int, engine: Engine, action: AlterAction): Engine {
    return install(slot, engine).apply(action)
  }
  
  /**
   * Uninstalls a engine of this graphical user interface
   * corresponding by the slot.
   */
  fun uninstall(slot: Int) {
    setItem(slot, null)
    engineStack.remove(slot)
  }
}

/**
 * Installs a engine to this
 * graphical user interface abstractly.
 */
fun GraphicalUserInterface.install(engine: Engine): Engine = install(engine.slot, engine)

/**
 * Install e engine copy to this
 * graphical user interface.
 */
fun GraphicalUserInterface.installCopy(engine: Engine): Engine = install(Engines.copy(engine))

/**
 * Installs all engines of a collection
 * to this graphical user interface.
 */
fun GraphicalUserInterface.installAll(engines: Collection<Engine>) = engines.onEach { engine ->
  install(engine)
}

/**
 * Uninstalls all engines of this graphical user interface
 */
fun GraphicalUserInterface.uninstallAll() = engineStack.onEach { engine ->
  uninstall(engine.key)
}

/**
 * Uninstalls all engines of this graphical user interface
 * if the engine satisfies the filter.
 */
fun GraphicalUserInterface.uninstallAll(filter: Filter<Engine>) = engineStack.onEach { engine ->
  if (filter(engine.value)) {
    uninstall(engine.key)
  }
}

/**
 * Uninstalls all not persistents engines
 * of this graphical user interface.
 */
fun GraphicalUserInterface.uninstallAllNonPersistents() = uninstallAll { engine ->
  !engine.isPersistent
}

/**
 * Selects a existent engine of this
 * graphical user interface.
 */
fun GraphicalUserInterface.select(slot: Int): Engine = engineStack[slot]!!

/**
 * Selects a possible existent engine
 * of this graphical user interface, or
 * null if the engine not exists.
 */
fun GraphicalUserInterface.selectOrNull(slot: Int): Engine? = engineStack[slot]

/**
 * Selects all current engines of
 * this graphical user interface.
 */
fun GraphicalUserInterface.selectAll() = engineStack.values.toMutableList()

/**
 * Selects all current engines of this graphical user interface
 * if the engine satisfy the filter.
 */
fun GraphicalUserInterface.selectAll(filter: Filter<Engine>) = engineStack.values.filter(filter).toMutableList()

/**
 * Selects all current engines of this graphical user interface
 * if the engine is persistent.
 */
fun GraphicalUserInterface.selectAllPersistents() = selectAll { engine ->
  engine.isPersistent
}

/**
 * Selects all current engines of this graphical user interface
 * if the engine is not persistent.
 */
fun GraphicalUserInterface.selectAllNonPersistents() = selectAll { engine ->
  !engine.isPersistent
}

/**
 * Try locates the background storage data
 * of this graphical user interface.
 */
inline var GraphicalUserInterface.background: MaterialData?
  get() = locate("Background")
  set(value) {
    if (value != null) {
      interject("Background", value)
      fill(value)
    }
  }

/**
 * Verifies if this graphical user interface
 * contains any background storage data
 */
inline val GraphicalUserInterface.hasBackground get() = background != null

/**
 * Try locates the owner storage data
 * of this graphical user interface.
 */
inline var GraphicalUserInterface.owner: Player?
  get() = locate("Owner")
  set(value) {
    if (value != null) {
      interject("Owner", value)
    }
  }

/**
 * Verifies if this graphical user interface
 * contains any owner storage data
 */
inline val GraphicalUserInterface.hasOwner get() = owner != null

/**
 * Try locates the parent storage data
 * of this graphical user interface.
 */
inline var GraphicalUserInterface.parent: GraphicalUserInterface?
  get() = locate("Parent")
  set(value) {
    if (value != null) {
      interject("Parent", value)
    }
  }

/**
 * Verifies if this graphical user interface
 * contains any parent storage data
 */
inline val GraphicalUserInterface.hasParent get() = parent != null

/**
 * Renders all engines of this graphical user interface
 * to a specified player.
 */
fun GraphicalUserInterface.renderEngines(player: Player) {
  selectAll().forEach { engine ->
    engine.render(player)
  }
}

/**
 * Works all engines of this graphical user interface.
 */
suspend fun GraphicalUserInterface.workEngines() {
  selectAll().forEach { engine ->
    engine.work()
  }
}

/**
 * Renders this graphical user interface to a specified player.
 */
fun GraphicalUserInterface.renderInterface(player: Player) {
  renders.forEach { action ->
    action(this, player)
  }
}

/**
 * Works this graphical user interface.
 */
suspend fun GraphicalUserInterface.workInterface() {
  works.forEach { action ->
    action(this)
  }
}

/**
 * Renders everything of this graphical user interface
 * to a specified player. This includes the interface and engines.
 */
fun GraphicalUserInterface.renderAll(player: Player) {
  renderInterface(player)
  renderEngines(player)
}

/**
 * Works everything of this graphical user interface.
 * This includes the interface and engines.
 */
suspend fun GraphicalUserInterface.workAll() {
  workInterface()
  workEngines()
}

/**
 * Copies a graphical user interface.
 */
fun GraphicalUserInterface.copy(): GraphicalUserInterface {
  val copy = Interfaces.create(title, lines)
  copy.model = model
  copy.engineStack = engineStack
  copy.worker = worker
  copy.works = works
  copy.renders = renders
  copy.accessors = accessors
  copy.uncessors = uncessors
  copy.storage = storage
  copy.observers = observers
  copy.users = users
  return copy
}
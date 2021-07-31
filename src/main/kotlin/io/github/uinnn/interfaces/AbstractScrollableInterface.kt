package io.github.uinnn.interfaces

import io.github.uinnn.interfaces.common.fill
import io.github.uinnn.interfaces.interfaces.*
import io.github.uinnn.interfaces.mapper.Mapper
import io.github.uinnn.interfaces.mapper.PartialMapper
import io.github.uinnn.interfaces.schematic.StandardSchematic
import io.github.uinnn.interfaces.schematic.Schematic
import io.github.uinnn.interfaces.worker.AsynchronousWorker
import io.github.uinnn.interfaces.worker.Worker
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory

/**
 * A abstract implementation of a [ScrollableGraphicalInterface].
 */
abstract class AbstractScrollableInterface(title: String, lines: Int) : ScrollableGraphicalInterface {
  override var model: Inventory = Bukkit.createInventory(this, lines * 9, title)
  override var engineStack: EngineStack = EngineStack()
  override var worker: Worker = AsynchronousWorker(this)
  override var works: WorkSet = WorkSet()
  override var renders: RenderSet = RenderSet()
  override var accessors: AccessSet = AccessSet()
  override var uncessors: AccessSet = AccessSet()
  override var scrollers: ScrollSet = ScrollSet()
  override var storage: Storage = Storage()
  override var observers: Observers = Observers()
  override var scrollDownEngine: Engine = defaultScrollDownEngine()
  override var scrollUpEngine: Engine = defaultScrollUpEngine()
  override var schematic: Schematic = StandardSchematic()
  override var scrollableEngines: ScrollableEngines = ScrollableEngines()
  override var page: Int = 1
  override var mapper: Mapper = PartialMapper
  override lateinit var owner: Player
  override var isOpen: Boolean = false

  init {
    defaultObservers()
    if (hasBackground)
      fill(background!!)
  }
}

/**
 * A standard implementation of a [ScrollableGraphicalInterface].
 */
class StandardScrollableInterface(title: String, lines: Int) : AbstractScrollableInterface(title, lines)

package io.github.uinnn.interfaces

import io.github.uinnn.interfaces.common.*
import io.github.uinnn.interfaces.interfaces.*
import io.github.uinnn.interfaces.schematic.DefaultSchematic
import io.github.uinnn.interfaces.schematic.Schematic
import io.github.uinnn.interfaces.worker.AsynchronousWorker
import io.github.uinnn.interfaces.worker.Worker
import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory

abstract class AbstractPaginatedInterface(title: String, lines: Int) : PaginatedGraphicalInterface {
  override var model: Inventory = Bukkit.createInventory(this, lines * 9, title)
  override var engineStack: EngineStack = EngineStack()
  override var worker: Worker = AsynchronousWorker(this)
  override var works: WorkSet = WorkSet()
  override var renders: RenderSet = RenderSet()
  override var accessors: AccessSet = AccessSet()
  override var uncessors: AccessSet = AccessSet()
  override var storage: Storage = Storage()
  override var observers: Observers = Observers()
  override var users: UserSet = UserSet()
  override var previousPageEngine: Engine = Engines.empty()
  override var nextPageEngine: Engine = Engines.empty()
  override var schematic: Schematic = DefaultSchematic()
  override var page: Int = 1

  init {
    defaultObservers()
    if (hasBackground) {
      fill(background!!)
    }
  }
}

class SimplePaginatedInterface(title: String, lines: Int) : AbstractPaginatedInterface(title, lines)

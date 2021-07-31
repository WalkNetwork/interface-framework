package io.github.uinnn.interfaces

import io.github.uinnn.interfaces.common.fill
import io.github.uinnn.interfaces.interfaces.*
import io.github.uinnn.interfaces.worker.AsynchronousWorker
import io.github.uinnn.interfaces.worker.Worker
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory

/**
 * A abstract implementation of a [GraphicalInterface].
 */
abstract class AbstractGraphicalInterface(title: String, lines: Int) : GraphicalInterface {
  override var model: Inventory = Bukkit.createInventory(this, lines * 9, title)
  override var engineStack: EngineStack = EngineStack()
  override var worker: Worker = AsynchronousWorker(this)
  override var works: WorkSet = WorkSet()
  override var renders: RenderSet = RenderSet()
  override var accessors: AccessSet = AccessSet()
  override var uncessors: AccessSet = AccessSet()
  override var storage: Storage = Storage()
  override var observers: Observers = Observers()
  override lateinit var owner: Player
  override var isOpen: Boolean = false

  init {
    defaultObservers()
    if (hasBackground) {
      fill(background!!)
    }
  }
}

/**
 * A standard implementation of a [GraphicalInterface].
 */
class StandardGraphicalInterface(title: String, lines: Int) : AbstractGraphicalInterface(title, lines)
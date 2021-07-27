package io.github.uinnn.interfaces

import io.github.uinnn.interfaces.common.fill
import io.github.uinnn.interfaces.interfaces.*
import io.github.uinnn.interfaces.worker.AsynchronousWorker
import io.github.uinnn.interfaces.worker.Worker
import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory

abstract class AbstractGraphicalInterface(title: String, lines: Int) : GraphicalUserInterface {
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
  
  init {
    defaultObservers()
    if (hasBackground) {
      fill(background!!)
    }
  }
}

class SimpleGraphicalInterface(title: String, lines: Int) : AbstractGraphicalInterface(title, lines)
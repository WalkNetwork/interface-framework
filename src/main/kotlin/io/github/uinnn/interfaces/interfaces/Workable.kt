package io.github.uinnn.interfaces.interfaces

import io.github.uinnn.interfaces.GraphicalInterface
import io.github.uinnn.interfaces.common.SuspendableAction

typealias WorkerAction = SuspendableAction<GraphicalInterface>
typealias WorkSet = HashSet<WorkerAction>

/**
 * Represents a workable object for graphical user interfaces or engines.
 * This is, the object will be able to work.
 */
interface Workable {

  /**
   * All registered works of this workable object.
   */
  var works: WorkSet

  /**
   * Starts the work of this
   * workable object.
   */
  suspend fun work()

  /**
   * Register a handler to be
   * triggered when works. (update)
   */
  fun onWork(action: WorkerAction) = works.add(action)
}
package io.github.uinnn.interfaces.interfaces

import io.github.uinnn.interfaces.GraphicalInterface
import io.github.uinnn.interfaces.common.Action
import org.bukkit.entity.Player

typealias AccessAction = Action<GraphicalInterface>
typealias AccessSet = HashSet<AccessAction>

/**
 * Represents a accessible object for graphical interfaces.
 * This is, the object will be to accessable or inaccessible by a player
 */
interface Accessible {

  /**
   * All registered accessors of this accessible object.
   */
  var accessors: AccessSet

  /**
   * All registered uncessor of this accesible object.
   */
  var uncessors: AccessSet
  
  /**
   * Makes player access (open) this graphical interface.
   * Also sets the specified player as owner
   * of this graphical interface
   */
  fun access(player: Player)
  
  /**
   * Makes owner uncess (close)
   * this graphical interface.
   */
  fun uncess(close: Boolean = true)
  
  /**
   * Register a handler to be
   * triggered when access. (open)
   */
  fun onAccess(action: AccessAction) = accessors.add(action)
  
  /**
   * Register a handler to be
   * triggered when unccess. (close)
   */
  fun onUncess(action: AccessAction) = uncessors.add(action)
}
package io.github.uinnn.interfaces.common

import io.github.uinnn.interfaces.GraphicalUserInterface
import org.bukkit.entity.Player

typealias AccessAction = GraphicalUserInterface.(Player) -> Unit
typealias AccessSet = HashSet<AccessAction>

interface Accessible {
  var accessors: AccessSet
  var uncessors: AccessSet
  
  /**
   * Makes player access (open)
   * this graphical user interface.
   */
  fun access(player: Player)
  
  /**
   * Makes player uncess (close)
   * this graphical user interface.
   */
  fun uncess(player: Player, close: Boolean = true)
  
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
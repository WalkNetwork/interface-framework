package io.github.uinnn.interfaces.interfaces

import io.github.uinnn.interfaces.GraphicalUserInterface
import org.bukkit.entity.Player

typealias RenderAction = GraphicalUserInterface.(Player) -> Unit
typealias RenderSet = HashSet<RenderAction>

/**
 * Represents a renderable object for graphical user interfaces or engines.
 * This is, the object will be able to renderize.
 */
interface Renderable {

  /**
   * All registered renders of this renderable object.
   */
  var renders: RenderSet
  
  /**
   * Render this renderable
   * object to the player.
   */
  fun render(player: Player)
  
  /**
   * Register a handler to be
   * triggered when render.
   */
  fun onRender(action: RenderAction) = renders.add(action)
}
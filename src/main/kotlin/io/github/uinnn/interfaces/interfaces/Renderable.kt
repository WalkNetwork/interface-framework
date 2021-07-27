package io.github.uinnn.interfaces.interfaces

import io.github.uinnn.interfaces.GraphicalUserInterface
import org.bukkit.entity.Player

typealias RenderAction = GraphicalUserInterface.(Player) -> Unit
typealias RenderSet = HashSet<RenderAction>

interface Renderable {
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
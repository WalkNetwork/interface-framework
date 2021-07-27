package io.github.uinnn.interfaces.common

import io.github.uinnn.interfaces.GraphicalUserInterface
import org.bukkit.event.inventory.InventoryClickEvent

typealias PressAction = InventoryClickEvent.(GraphicalUserInterface) -> Unit
typealias PressSet = HashSet<PressAction>

interface Pressable {
  var pressSet: PressSet

  /**
   * Triggers all registered handlers
   * of this pressable object.
   */
  fun press(event: InventoryClickEvent)

  /**
   * Register a handler to be triggered when
   * a player press on a engine.
   */
  fun onPress(action: PressAction) = pressSet.add(action)
}
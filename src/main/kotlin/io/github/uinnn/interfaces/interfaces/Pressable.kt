package io.github.uinnn.interfaces.interfaces

import io.github.uinnn.interfaces.GraphicalUserInterface
import org.bukkit.event.inventory.InventoryClickEvent

typealias PressAction = InventoryClickEvent.(GraphicalUserInterface) -> Unit
typealias PressSet = HashSet<PressAction>

/**
 * Represents a pressable object for engines.
 * This is, the object will be able to press.
 */
interface Pressable {

  /**
   * All registered press of this pressable object.
   */
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
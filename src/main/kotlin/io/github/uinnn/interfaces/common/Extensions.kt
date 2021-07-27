package io.github.uinnn.interfaces.common

import io.github.uinnn.interfaces.GraphicalUserInterface
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryEvent
import org.bukkit.event.inventory.InventoryInteractEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.material.MaterialData

typealias Filter<T> = (T) -> Boolean

/**
 * Fills all slots of the inventory with gived item.
 */
fun Inventory.fill(item: ItemStack, replaces: Boolean = false) {
  for (index in 0 until size) {
    if (getItem(index) != null && !replaces)
      continue
    setItem(index, item)
  }
}

/**
 * Fills all slots of the inventory with gived material.
 */
fun Inventory.fill(material: Material, replaces: Boolean = false) = fill(ItemStack(material), replaces)

/**
 * Fills all slots of the inventory with gived material data.
 */
fun Inventory.fill(data: MaterialData, replaces: Boolean = false) = fill(data.toItemStack(1), replaces)

/**
 * Gets a graphical user interface from a inventory
 * or nulls, if the inventory is not a interface.
 */
fun Inventory.interfaceOrNull(): GraphicalUserInterface? {
  return if (holder is GraphicalUserInterface) holder as GraphicalUserInterface else null
}

/**
 * Gets a graphical user interface from a inventory of a
 * inventory event or nulls, if the inventory is not a interface.
 */
fun InventoryEvent.interfaceOrNull(): GraphicalUserInterface? = inventory.interfaceOrNull()

/**
 * Casts who clicked this inventory event as Player
 */
inline val InventoryInteractEvent.player get() = whoClicked as Player

/**
 * Returns the start slot of a line.
 */
fun startSlot(line: Int) = line * 9 - 9

/**
 * Returns the last slot of a line.
 */
fun lastSlot(line: Int) = line * 9 - 1

/**
 * Returns a slot defined in a line and the
 * line slot. This is, if a want to select the slot
 * `13` will be:
 * ```kt
 * slotAt(line = 2, slot = 4)
 * ```
 */
fun slotAt(line: Int, slot: Int) = startSlot(line) + slot - 1


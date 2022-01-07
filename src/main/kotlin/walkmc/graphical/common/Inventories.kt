package walkmc.graphical.common

import org.bukkit.*
import org.bukkit.entity.*
import org.bukkit.event.inventory.*
import org.bukkit.inventory.*
import org.bukkit.material.*
import walkmc.graphical.*

/**
 * Verifies if this inventory has space for a item.
 */
inline val Inventory.hasSpace get() = firstEmpty() != -1

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
fun Inventory.interfaceOrNull(): IGraphical? {
	return if (holder is IGraphical) holder as IGraphical else null
}

/**
 * Gets a graphical user interface from a inventory of a
 * inventory event or nulls, if the inventory is not a interface.
 */
fun InventoryEvent.interfaceOrNull(): IGraphical? = inventory.interfaceOrNull()

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
package io.github.uinnn.interfaces

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.HumanEntity
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack

typealias UserSet = HashSet<Player>

/**
 * A model class thats is used as base for
 * all graphical users interfaces.
 */
interface UserInterface : Inventory, InventoryHolder {
  var model: Inventory
  var users: UserSet
  
  /*
   DELEGATION
   */
  
  override fun iterator() = model.iterator()
  override fun iterator(p0: Int): MutableListIterator<ItemStack> = model.iterator(p0)
  override fun getSize() = model.size
  override fun getMaxStackSize() = model.maxStackSize
  override fun setMaxStackSize(p0: Int) {
    model.maxStackSize = p0
  }
  
  override fun getName(): String = model.name
  override fun getItem(p0: Int): ItemStack? = model.getItem(p0)
  override fun setItem(p0: Int, p1: ItemStack?) = model.setItem(p0, p1)
  override fun addItem(vararg p0: ItemStack): java.util.HashMap<Int, ItemStack> = model.addItem(*p0)
  override fun removeItem(vararg p0: ItemStack?): java.util.HashMap<Int, ItemStack> = model.removeItem(*p0)
  override fun getContents(): Array<ItemStack> = model.contents
  override fun setContents(p0: Array<out ItemStack>?) {
    model.contents = p0
  }
  
  override fun contains(p0: Int) = model.contains(p0)
  override fun contains(p0: Material) = model.contains(p0)
  override fun contains(p0: ItemStack) = model.contains(p0)
  override fun contains(p0: Int, p1: Int) = model.contains(p0, p1)
  override fun contains(p0: Material, p1: Int) = model.contains(p0, p1)
  override fun contains(p0: ItemStack, p1: Int) = model.contains(p0, p1)
  override fun containsAtLeast(p0: ItemStack, p1: Int) = model.containsAtLeast(p0, p1)
  override fun all(p0: Int): java.util.HashMap<Int, out ItemStack> = model.all(p0)
  override fun all(p0: Material): java.util.HashMap<Int, out ItemStack> = model.all(p0)
  override fun all(p0: ItemStack): java.util.HashMap<Int, out ItemStack> = model.all(p0)
  override fun first(p0: Int) = model.first(p0)
  override fun first(p0: Material) = model.first(p0)
  override fun first(p0: ItemStack) = model.first(p0)
  override fun firstEmpty() = model.firstEmpty()
  override fun remove(p0: Int) = model.remove(p0)
  override fun remove(p0: Material) = model.remove(p0)
  override fun remove(p0: ItemStack) = model.remove(p0)
  override fun clear(p0: Int) = model.clear(p0)
  override fun clear() = model.clear()
  override fun getViewers(): List<HumanEntity> = users.map { it }
  override fun getTitle(): String = model.title
  override fun getType(): InventoryType = model.type
  override fun getHolder() = this
  override fun getInventory() = this
}

fun GraphicalUserInterface.setSize(rows: Int) {
  model = Bukkit.createInventory(this, rows * 9, title).apply {
    engineStack.values.forEach { engine ->
      setItem(engine.slot, engine)
    }
  }
}

fun GraphicalUserInterface.setTitle(title: String) {
  model = Bukkit.createInventory(this, size, title).apply {
    engineStack.values.forEach { engine ->
      setItem(engine.slot, engine)
    }
  }
}

/**
 * Gets the lines amount of this user interface
 */
inline val UserInterface.lines get() = size / 9

/**
 * Verifies if this user interface
 * is open by any player
 */
inline val UserInterface.isOpen get() = users.isNotEmpty()
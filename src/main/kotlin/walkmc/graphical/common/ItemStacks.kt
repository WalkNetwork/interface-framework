@file:Suppress("NOTHING_TO_INLINE")

package walkmc.graphical.common

import org.bukkit.inventory.*
import walkmc.extensions.*
import walkmc.graphical.*
import walkmc.graphical.engines.*

inline fun ItemStack.toEngine() = Engine(this)
inline fun ItemStack.toCycleEngine() = CycleEngine(this)
inline fun ItemStack.toToggleEngine() = ToggleEngine(this)
inline fun ItemStack.toFilterToggleEngine() = ToggleFilterEngine(this)
inline fun ItemStack.toSorterToggleEngine() = ToggleSorterEngine(this)
inline fun ItemStack.toProcessorEngine() = ProcessorEngine(this)
inline fun ItemStack.toReqEngine() = ReqEngine(this)
inline fun ItemStack.toItemReqEngine() = ItemReqEngine(this)
inline fun ItemStack.toCountEngine() = CountEngine(this)
inline fun ItemStack.toAmountCountEngine() = AmountCountEngine(this)
inline fun ItemStack.toFilterEngine() = FilterEngine(this)
inline fun ItemStack.toSorterEngine() = SorterEngine(this)
inline fun ItemStack.toEmptyIndexEngine() = EmptyIndexEngine(this)
inline fun ItemStack.toAccessEngine() = AccessEngine(this)

/**
 * Copies this item stack applying the new name of them.
 */
fun ItemStack.copyWithName(name: String) = copy { this.name = name }

/**
 * Copies this item stack applying the new lore of them.
 */
fun ItemStack.copyWithLore(lore: List<String>) = copy { this.lore = lore }

/**
 * Copies this item stack applying the new lore of them.
 */
fun ItemStack.copyWithLore(lore: Iterable<String>) = copy { this.lore = lore.toList() }

/**
 * Copies this item stack applying the new name of them.
 */
fun ItemStack.newWithName(name: String) = newItem(this, name)

/**
 * Copies this item stack applying the new lore of them.
 */
fun ItemStack.newWithLore(lore: List<String>) = newItem(this, lore)

/**
 * Copies this item stack applying the new lore of them.
 */
fun ItemStack.newWithLore(lore: Iterable<String>) = newItem(this, lore.toList())



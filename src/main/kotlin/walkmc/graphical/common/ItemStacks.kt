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
inline fun ItemStack.toScrollUpEngine() = ScrollUpEngine(this)
inline fun ItemStack.toScrollDownEngine() = ScrollDownEngine(this)

fun ItemStack.toEngine(slot: Int) = Engine(this).also { it.slot = slot }
fun ItemStack.toCycleEngine(slot: Int) = CycleEngine(this).also { it.slot = slot }
fun ItemStack.toToggleEngine(slot: Int) = ToggleEngine(this).also { it.slot = slot }
fun ItemStack.toFilterToggleEngine(slot: Int) = ToggleFilterEngine(this).also { it.slot = slot }
fun ItemStack.toSorterToggleEngine(slot: Int) = ToggleSorterEngine(this).also { it.slot = slot }
fun ItemStack.toProcessorEngine(slot: Int) = ProcessorEngine(this).also { it.slot = slot }
fun ItemStack.toReqEngine(slot: Int) = ReqEngine(this).also { it.slot = slot }
fun ItemStack.toItemReqEngine(slot: Int) = ItemReqEngine(this).also { it.slot = slot }
fun ItemStack.toCountEngine(slot: Int) = CountEngine(this).also { it.slot = slot }
fun ItemStack.toAmountCountEngine(slot: Int) = AmountCountEngine(this).also { it.slot = slot }
fun ItemStack.toFilterEngine(slot: Int) = FilterEngine(this).also { it.slot = slot }
fun ItemStack.toSorterEngine(slot: Int) = SorterEngine(this).also { it.slot = slot }
fun ItemStack.toEmptyIndexEngine(slot: Int) = EmptyIndexEngine(this).also { it.slot = slot }
fun ItemStack.toAccessEngine(slot: Int) = AccessEngine(this).also { it.slot = slot }
fun ItemStack.toScrollUpEngine(slot: Int) = ScrollUpEngine(this).also { it.slot = slot }
fun ItemStack.toScrollDownEngine(slot: Int) = ScrollDownEngine(this).also { it.slot = slot }

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



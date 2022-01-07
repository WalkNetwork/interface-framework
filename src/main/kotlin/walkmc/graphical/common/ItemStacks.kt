@file:Suppress("NOTHING_TO_INLINE")

package walkmc.graphical.common

import org.bukkit.inventory.*
import walkmc.graphical.*

/**
 * Converts this ItemStack to a engine.
 */
inline fun ItemStack.toEngine(): Engine = Engine(this)


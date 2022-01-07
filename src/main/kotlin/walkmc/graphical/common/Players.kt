@file:Suppress("NOTHING_TO_INLINE")

package walkmc.graphical.common

import org.bukkit.entity.*
import walkmc.graphical.*

/**
 * Makes this player access the specified interface
 */
inline fun Player.accessInterface(graphical: IGraphical) = graphical.access(this)

/**
 * Makes this player uncess the current opened interface.
 */
inline fun Player.uncessInterface() = interfaceOrNull()?.uncess()

/**
 * Returns the current opened interface of this player or null.
 */
inline fun Player.interfaceOrNull(): IGraphical? = openInventory.topInventory.interfaceOrNull()
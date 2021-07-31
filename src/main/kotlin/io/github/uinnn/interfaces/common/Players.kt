package io.github.uinnn.interfaces.common

import io.github.uinnn.interfaces.GraphicalInterface
import org.bukkit.entity.Player

/**
 * Makes this player access the specified interface
 */
fun Player.accessInterface(graphical: GraphicalInterface) = graphical.access(this)

/**
 * Returns the current opened interface of this player or null.
 */
fun Player.interfaceOrNull(): GraphicalInterface? = player.openInventory.topInventory.interfaceOrNull()
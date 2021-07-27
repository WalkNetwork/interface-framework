package io.github.uinnn.interfaces

import io.github.uinnn.interfaces.interfaces.ObserverKind
import io.github.uinnn.interfaces.common.interfaceOrNull
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.event.player.PlayerPickupItemEvent
import org.bukkit.plugin.Plugin

/**
 * A object instance for registering all
 * interfaces services.
 */
object InterfaceService : Listener {
  lateinit var plugin: Plugin

  /**
   * Loads all services of a interface can offer
   * with the specified plugin as owner.
   */
  fun load(plugin: Plugin) {
    if (this::plugin.isInitialized) return
    this.plugin = plugin
    Bukkit.getPluginManager().registerEvents(this, plugin)
  }

  @EventHandler
  fun onOpen(event: InventoryOpenEvent) {
    val graphical = event.interfaceOrNull() ?: return
    event.isCancelled = graphical.permits(ObserverKind.ACCESS).not()
  }

  @EventHandler
  fun onDrag(event: InventoryDragEvent) {
    val graphical = event.interfaceOrNull() ?: return
    event.isCancelled = graphical.permits(ObserverKind.DRAG).not()
  }

  @EventHandler
  fun onClick(event: InventoryClickEvent) {
    val graphical = event.interfaceOrNull() ?: return
    event.isCancelled = graphical.permits(ObserverKind.PRESS_CANCELLABLE)
    if (graphical.permits(ObserverKind.PRESS)) {
      graphical.selectOrNull(event.rawSlot)?.press(event)
    }
  }

  @EventHandler
  fun onClose(event: InventoryOpenEvent) {
    val graphical = event.interfaceOrNull() ?: return
    if (graphical.permits(ObserverKind.UNCCESS)) {
      graphical.uncess(event.player as Player, false)
    } else {
      Bukkit.getScheduler().runTaskLater(plugin, {
        event.player.openInventory(graphical)
      }, 1)
    }
  }

  @EventHandler
  fun onPickup(event: PlayerPickupItemEvent) {
    val graphical = event.player.openInventory.topInventory.interfaceOrNull() ?: return
    event.isCancelled = graphical.permits(ObserverKind.PICKUP).not()
  }
}

fun Plugin.loadInterfaceService() = InterfaceService.load(this)
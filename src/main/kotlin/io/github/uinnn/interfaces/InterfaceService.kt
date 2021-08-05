package io.github.uinnn.interfaces

import io.github.uinnn.interfaces.common.interfaceOrNull
import io.github.uinnn.interfaces.interfaces.ObserverKind
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
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
   * with the this plugin as owner.
   */
  fun startup(plugin: Plugin) {
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
  fun onClose(event: InventoryCloseEvent) {
    val graphical = event.interfaceOrNull() ?: return
    if (graphical.permits(ObserverKind.UNCESS)) {
      graphical.uncess(false)
    } else {
      Bukkit.getScheduler().runTaskLater(plugin, {
        event.player.openInventory(graphical)
      }, 1)
    }
  }

  @EventHandler
  fun onPickup(event: PlayerPickupItemEvent) {
    val graphical = event.player.interfaceOrNull() ?: return
    event.isCancelled = graphical.permits(ObserverKind.PICKUP).not()
  }
}

/**
 * Loads all services of a interface can offer
 * with the this plugin as owner.
 */
fun Plugin.loadInterfaceService() = InterfaceService.startup(this)

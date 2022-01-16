package walkmc.graphical

import org.bukkit.*
import org.bukkit.event.*
import org.bukkit.event.inventory.*
import org.bukkit.event.player.*
import org.bukkit.plugin.*
import walkmc.event.*
import walkmc.extensions.*
import walkmc.graphical.common.*
import walkmc.graphical.interfaces.*

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
		if (this::plugin.isInitialized)
			return
		
		this.plugin = plugin
		Bukkit.getPluginManager().registerEvents(this, plugin)
	}
	
	@EventHandler
	fun onTick(event: InventoryTickEvent) {
		val graphical = event.interfaceOrNull() ?: return
		if (graphical.allowTick && graphical.ticks % graphical.tickDelay == 0) {
			graphical.tick()
		}
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
			plugin.scheduleLater(50) {
				event.player.openInventory(graphical)
			}
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

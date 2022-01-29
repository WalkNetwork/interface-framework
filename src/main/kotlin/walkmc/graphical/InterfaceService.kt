package walkmc.graphical

import net.minecraft.server.*
import org.bukkit.*
import org.bukkit.entity.*
import org.bukkit.event.*
import org.bukkit.event.entity.*
import org.bukkit.event.inventory.*
import org.bukkit.event.player.*
import org.bukkit.plugin.*
import walkmc.event.*
import walkmc.extensions.*
import walkmc.graphical.common.*
import walkmc.graphical.interfaces.*

/**
 * An object instance for registering all
 * interfaces services.
 */
object InterfaceService : Listener {
	lateinit var plugin: Plugin
	
	/**
	 * Loads all services of an interface can offer
	 * with the plugin as owner.
	 */
	fun startup(plugin: Plugin) {
		if (this::plugin.isInitialized)
			return
		
		this.plugin = plugin
		Bukkit.getPluginManager().registerEvents(this, plugin)
	}
	
	@EventHandler
	fun onDrag(event: InventoryDragEvent) {
		val graphical = event.interfaceOrNull() ?: return
		event.isCancelled = !graphical.permits(ObserverKind.DRAG)
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
		event.isCancelled = !graphical.permits(ObserverKind.PICKUP)
	}
	
	@EventHandler
	fun onDamage(event: EntityDamageEvent) {
		val entity = event.entity
		if (entity !is Player) return
		val graphical = entity.interfaceOrNull() ?: return
		event.isCancelled = !graphical.permits(ObserverKind.DAMAGE)
	}
	
	@EventHandler
	fun onTeleport(event: PlayerTeleportEvent) {
		val graphical = event.player.interfaceOrNull() ?: return
		event.isCancelled = !graphical.permits(ObserverKind.TELEPORT)
	}
	
	@EventHandler
	fun onMessageReceived(event: PacketSendEvent) {
		if (event.packet !is PacketPlayOutChat) return
		val graphical = event.player.interfaceOrNull() ?: return
		event.isCancelled = !graphical.permits(ObserverKind.RECEIVE_MESSAGE)
	}
}

/**
 * Loads all services of an interface can offer
 * with the plugin as owner.
 */
fun Plugin.loadInterfaceService() = InterfaceService.startup(this)

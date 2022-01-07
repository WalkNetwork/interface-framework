package walkmc.graphical.interfaces

import org.bukkit.event.inventory.*
import walkmc.graphical.*
import walkmc.graphical.common.*

typealias PressAction = MultiAction<InventoryClickEvent, IGraphical>
typealias PressSet = MutableSet<PressAction>

/**
 * Represents a pressable object for engines.
 * This is, the object will be able to press.
 */
interface Pressable {
	
	/**
	 * All registered press handlers
	 * of this pressable object.
	 */
	var pressSet: PressSet
	
	/**
	 * If this pressable object will be cancelled when press.
	 */
	var isPressable: Boolean
	
	/**
	 * Triggers all registered handlers
	 * of this pressable object.
	 */
	fun press(event: InventoryClickEvent)
	
	/**
	 * Register a handler to be triggered when
	 * a player press on a engine.
	 */
	fun onPress(action: PressAction) = pressSet.add(action)
}

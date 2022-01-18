package walkmc.graphical.engines

import org.bukkit.entity.*
import org.bukkit.event.inventory.*
import org.bukkit.inventory.*
import walkmc.*
import walkmc.graphical.*

/**
 * An engine implementation that's opens another graphical.
 */
open class AccessEngine : Engine {
   constructor(type: Materials, amount: Int = 1) : super(type.toItem(amount))
   constructor(stack: ItemStack) : super(stack)
   
   private var access: IGraphical? = null
	
	var accessOnClick = true
   var autoAccess = false
   var autoAccessDelay = 100
	
	fun accessing(graphical: IGraphical): AccessEngine {
		access = graphical
      return this
	}
	
	open fun access(player: Player) {
		access?.access(player)
	}
   
   open fun withAutoAccess(delay: Int): AccessEngine {
      autoAccess = true
      autoAccessDelay = delay
      return this
   }
	
	override fun handleClick(event: InventoryClickEvent) {
		if (accessOnClick) access(event.player)
	}
   
   override fun handleTick() {
      if (autoAccess && ticks == autoAccessDelay)
         access(player)
   }
}

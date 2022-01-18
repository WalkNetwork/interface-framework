package walkmc.graphical.engines

import org.bukkit.event.inventory.*
import org.bukkit.inventory.*
import walkmc.*
import walkmc.extensions.collections.*
import walkmc.graphical.*

/**
 * An implementation of [Engine] that's can support a sequence of requirements
 * options to be displayed if a requirement is done.
 *
 * This is similar to [ReqEngine] but this support more than one requirement
 * and is planned to only change the item displayer of the engine.
 */
open class ItemReqEngine : Engine {
   constructor(type: Materials, amount: Int = 1) : super(type, amount)
   constructor(stack: ItemStack) : super(stack)
   
   open var options: MutableMap<Req<ItemReqEngine>, ItemStack> = LinkedHashMap()
   
   open var notifyOnRender = true
   open var notifyOnClick = false
   open var notifyOnTick = false
   open var notifyTickDelay = 20
   
   fun findAvailable() = options.firstOrNull { it.key(this) }
   
   fun addDefault(req: Req<ItemReqEngine>) {
      options[req] = this
   }
   
   fun addOption(item: ItemStack, req: Req<ItemReqEngine>) {
      options[req] = item
   }
   
   override fun notifyChange() {
      val value = findAvailable()
      if (value == null) super.notifyChange() else alter(value.value)
   }
   
   override fun handleRender() {
      if (notifyOnRender) notifyChange()
   }
   
   override fun handleClick(event: InventoryClickEvent) {
      if (notifyOnClick) notifyChange()
   }
   
   override fun handleTick() {
      if (notifyOnClick) {
         if (ticks % notifyTickDelay == 0) notifyChange()
      }
   }
}

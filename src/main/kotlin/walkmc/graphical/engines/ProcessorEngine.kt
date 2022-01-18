package walkmc.graphical.engines

import org.bukkit.event.inventory.*
import org.bukkit.inventory.*
import walkmc.*
import walkmc.extensions.*
import walkmc.graphical.*

typealias ProcessorCallback = ProcessorEngine.() -> Map<String, Any>

/**
 * An implementation of [Engine] that's process placeholders.
 */
open class ProcessorEngine : Engine {
   constructor(type: Materials, amount: Int = 1) : super(type, amount)
   constructor(stack: ItemStack) : super(stack)
   
   open var processor: ProcessorCallback = { mapOf() }
   
   open var processOnClick = true
   open var processOnRender = true
   open var processOnTick = true
   open var processOnTickDelay = 20
   
   fun processor(callback: ProcessorCallback) {
      processor = callback
   }
   
   fun processor(processors: Map<String, Any>) {
      processor = { processors }
   }
   
   fun processor(vararg processors: Pair<String, Any>) {
      process(processors.toMap())
   }
   
   override fun notifyChange() {
      alter(processCopy(processor(this)))
   }
   
   override fun handleClick(event: InventoryClickEvent) {
      if (processOnClick) notifyChange()
   }
   
   override fun handleRender() {
      if (processOnRender) notifyChange()
   }
   
   override fun handleTick() {
      if (processOnTick && ticks % processOnTickDelay == 0) notifyChange()
   }
}

package walkmc.graphical.engines

import org.bukkit.inventory.*
import walkmc.*

/**
 * An engine that's supports to be toggled.
 *
 * Toggle Engines only supports 2 engines to be toggled.
 */
open class ToggleEngine : CycleEngine {
   constructor(type: Materials, amount: Int = 1) : super(type, amount)
   constructor(stack: ItemStack) : super(stack)
   
   open val toggled get() = options.index == 1
   open var defaultState by boolean { toggleTo(it) }
   
   open fun handleToggled() = Unit
   open fun handleUntoggled() = Unit
   
   fun toggle() = cycle(true)
   
   fun toggleTo(state: Boolean) {
      if (toggled == state) return
      alter(if (state) options.toLast() else options.toFirst())
      if (state) handleToggled() else handleUntoggled()
      callCallback(options.current())
   }
   
   override fun cycle(forward: Boolean) {
      alter(options.toNextOrFirst())
      if (toggled) handleToggled() else handleUntoggled()
      callCallback(options.current())
   }
   
   override fun addCycle(cycle: ItemStack): Boolean {
      if (options.size + 1 > 2) return false
      return super.addCycle(cycle)
   }
   

}

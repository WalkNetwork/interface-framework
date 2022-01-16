package walkmc.graphical.engines

import org.bukkit.inventory.*
import walkmc.*
import walkmc.graphical.*

/**
 * A toggle engine that's will toggle the filter state of a filter graphical.
 *
 * If the graphical owner of this engine is not a [FilterGraphical], nothing will be done.
 */
open class ToggleFilterEngine : ToggleEngine {
   constructor(type: Materials, amount: Int = 1) : super(type, amount)
   constructor(stack: ItemStack) : super(stack)
   
   val graph get() = graphical as FilterGraphical<*>
   
   override fun cycle(forward: Boolean) {
      super.cycle(forward)
      graph.filterEngine.notifyChange()
   }
   
   override fun handleToggled() {
      graph.disableFilter()
   }
   
   override fun handleUntoggled() {
      graph.enableFilter()
   }
}

package walkmc.graphical.engines

import org.bukkit.inventory.*
import walkmc.*
import walkmc.graphical.*

/**
 * A toggle engine that's will toggle the sorter state of a sorter graphical.
 *
 * If the graphical owner of this engine is not a [SortGraphical], nothing will be done.
 */
open class ToggleSorterEngine : ToggleEngine {
   constructor(type: Materials, amount: Int = 1) : super(type, amount)
   constructor(stack: ItemStack) : super(stack)
   
   val graph get() = graphical as SortGraphical<*>
   
   override fun cycle(forward: Boolean) {
      super.cycle(forward)
      graph.sorterEngine.notifyChange()
   }
   
   override fun handleToggled() {
      graph.disableSorter()
   }
   
   override fun handleUntoggled() {
      graph.enableSorter()
   }
}

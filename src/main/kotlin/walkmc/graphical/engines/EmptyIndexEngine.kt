package walkmc.graphical.engines

import org.bukkit.inventory.*
import walkmc.*
import walkmc.graphical.*

/**
 * An implementation of [ReqEngine] that's show's an empty index.
 */
open class EmptyIndexEngine : ReqEngine {
   constructor(type: Materials, amount: Int = 1) : super(type, amount)
   constructor(stack: ItemStack) : super(stack)
   
   val graph get() = graphical as IndexGraphical<*>
   
   override var requirement: (ReqEngine) -> Boolean = { graph.isEmpty }
   
   init {
      isPersistent = true
   }
}

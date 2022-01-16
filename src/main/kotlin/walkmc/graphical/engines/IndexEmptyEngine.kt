package walkmc.graphical.engines

import org.bukkit.inventory.*
import walkmc.*
import walkmc.extensions.*
import walkmc.graphical.*

open class IndexEmptyEngine : RequirementEngine {
   constructor(type: Materials, amount: Int = 1) : super(type, amount)
   constructor(stack: ItemStack) : super(stack)
   
   val graph get() = graphical as IndexGraphical<*>
   
   override var requirement: (RequirementEngine) -> Boolean = { graph.isEmpty }
   
   init {
      isPersistent = true
   }
   
   companion object {
      fun default() = IndexEmptyEngine(newItem(Materials.BARRIER, "§cVazio."))
   }
}

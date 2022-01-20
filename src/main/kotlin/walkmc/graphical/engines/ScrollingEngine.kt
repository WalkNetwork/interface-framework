package walkmc.graphical.engines

import org.bukkit.event.inventory.*
import org.bukkit.inventory.*
import walkmc.*
import walkmc.graphical.*

/**
 * An abstract implementation of [Engine] that's scrolls to next or previous page.
 */
abstract class ScrollingEngine : Engine {
   constructor(type: Materials, amount: Int = 1) : super(type, amount)
   constructor(stack: ItemStack) : super(stack)
   
   val graph get() = graphical as IScrollGraphical
   
   init {
      isPersistent = true
   }
}

/**
 * An implementation of [ScrollingEngine] that's scrolls to the next page.
 */
open class ScrollUpEngine : ScrollingEngine {
   constructor(type: Materials, amount: Int = 1) : super(type, amount)
   constructor(stack: ItemStack) : super(stack)
   
   override fun handleClick(event: InventoryClickEvent) {
      graph.scrollUp()
   }
   
   override fun handleScroll() {
      turnVisibility(!graph.isLastPage)
   }
}

/**
 * An implementation of [ScrollingEngine] that's scrolls to the previous page.
 */
open class ScrollDownEngine : ScrollingEngine {
   constructor(type: Materials, amount: Int = 1) : super(type, amount)
   constructor(stack: ItemStack) : super(stack)
   
   override fun handleClick(event: InventoryClickEvent) {
      graph.scrollDown()
   }
   
   override fun handleScroll() {
      turnVisibility(!graph.isFirstPage)
   }
}

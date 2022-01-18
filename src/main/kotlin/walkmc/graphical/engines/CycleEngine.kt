package walkmc.graphical.engines

import org.bukkit.event.inventory.*
import org.bukkit.inventory.*
import walkmc.*
import walkmc.collections.*
import walkmc.graphical.*
import walkmc.graphical.interfaces.*

typealias CycleCallback<T> = T.(Int) -> Unit

/**
 * Represents an abstract cycle engine.
 */
abstract class AbstractCycleEngine<T> : Engine {
   constructor(type: Materials, amount: Int = 1) : super(type, amount)
   constructor(stack: ItemStack) : super(stack)
   
   open val options = IndexList<T>()
   open var cycleCallback: CycleCallback<T>? = null
   
   open var cycleOnClick = true
   open var autoCycle = false
   open var autoCycleDelay = 20
   
   abstract fun cycle(forward: Boolean = true)
   
   open fun onCycle(callback: CycleCallback<T>) {
      cycleCallback = callback
   }
   
   open fun addCycle(cycle: T) = options.add(cycle)
   open fun callCallback(value: T) = cycleCallback?.invoke(value, options.index)
   
   override fun press(event: InventoryClickEvent) {
      super.press(event)
      if (cycleOnClick) cycle(event.isRightClick)
   }
   
   override fun handleTick() {
      if (autoCycle && ticks % autoCycleDelay == 0) cycle()
   }
}

/**
 * A cycle engine that's alter's their show item.
 */
open class CycleEngine : AbstractCycleEngine<ItemStack> {
   constructor(type: Materials, amount: Int = 1) : super(type, amount)
   constructor(stack: ItemStack) : super(stack)
   
   init {
      onFirstRender { addCycle(this@CycleEngine) }
   }
   
   override fun cycle(forward: Boolean) {
      alter(if (forward) options.toNextOrFirst() else options.toPreviousOrLast())
      callCallback(options.current())
   }
}

/**
 * A cycle engine that's alter's their show material.
 */
open class CycleMaterialEngine : AbstractCycleEngine<Materials> {
   constructor(type: Materials, amount: Int = 1) : super(type, amount)
   constructor(stack: ItemStack) : super(stack)
   
   init {
      onFirstRender { addCycle(material) }
   }
   
   override fun cycle(forward: Boolean) {
      alter { material = if (forward) options.toNextOrFirst() else options.toPreviousOrLast() }
      callCallback(material)
   }
}

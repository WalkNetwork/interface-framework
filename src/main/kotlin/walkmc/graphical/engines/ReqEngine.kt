package walkmc.graphical.engines

import org.bukkit.event.inventory.*
import org.bukkit.inventory.*
import walkmc.*
import walkmc.graphical.*

typealias Req<T> = (T) -> Boolean

/**
 * An implementation of [Engine] that's limit's the actions
 * of an engine if a requirement is not done.
 */
open class ReqEngine : Engine {
   constructor(type: Materials, amount: Int = 1) : super(type, amount)
   constructor(stack: ItemStack) : super(stack)
   
   open var requirement: Req<ReqEngine> = { true }
   
   open var limitVisibility = true
   open var limitClick = true
   open var limitRender = true
   open var limitTick = true
   open var limitScroll = true
   
   open var successCallback: (ReqEngine.() -> Unit)? = null
   open var failureCallback: (ReqEngine.() -> Unit)? = null
   
   fun onSuccess(callback: ReqEngine.() -> Unit) {
      successCallback = callback
   }
   
   fun onFailure(callback: ReqEngine.() -> Unit) {
      failureCallback = callback
   }
   
   /**
    * Handles the successful requirement.
    */
   open fun handleSuccessful() {
      successCallback?.invoke(this)
   }
   
   /**
    * Handles the failure requirement.
    */
   open fun handleFailure() {
      failureCallback?.invoke(this)
   }
   
   /**
    * Verify if the requirement of this engine is done.
    */
   open fun isRequirementDone(): Boolean {
      return if (requirement(this)) {
         handleSuccessful()
         true
      } else {
         handleFailure()
         false
      }
   }
   
   /**
    * Sets the requirement of this engine.
    */
   infix fun require(block: (ReqEngine) -> Boolean): ReqEngine {
      requirement = block
      return this
   }
   
   override fun press(event: InventoryClickEvent) {
      if (isRequirementDone() || !limitClick) super.press(event)
   }
   
   override fun render() {
      val isDone = isRequirementDone()
      isVisible = !(!isDone && limitVisibility)
      if (isDone || !limitRender) super.render()
   }
   
   override fun tick() {
      // delegate all for performance
      if (graphical == null || !allowTick) return
      if (ticks++ % tickDelay != 0) return
      
      if (isRequirementDone() || !limitTick) {
         for (act in tickers) act(graphical!!)
         handleTick()
      }
   }
   
   override fun scroll() {
      val isDone = isRequirementDone()
      isVisible = !(!isDone && limitVisibility)
      if (isDone || !limitScroll) super.scroll()
   }
}

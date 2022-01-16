package walkmc.graphical.engines

import org.bukkit.event.inventory.*
import org.bukkit.inventory.*
import walkmc.*
import walkmc.graphical.*

/**
 * An implementation of [Engine] that's limit's the actions
 * of an engine if a requirement is not done.
 */
open class RequirementEngine : Engine {
   constructor(type: Materials, amount: Int = 1) : super(type, amount)
   constructor(stack: ItemStack) : super(stack)
   
   open var requirement: (RequirementEngine) -> Boolean = { true }
   
   open var limitVisibility = true
   open var limitClick = true
   open var limitRender = true
   open var limitTick = true
   open var limitScroll = true
   
   open var successCallback: (RequirementEngine.() -> Unit)? = null
   open var failureCallback: (RequirementEngine.() -> Unit)? = null
   
   fun onSuccess(callback: RequirementEngine.() -> Unit) {
      successCallback = callback
   }
   
   fun onFailure(callback: RequirementEngine.() -> Unit) {
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
   infix fun require(block: (RequirementEngine) -> Boolean): RequirementEngine {
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
      if (isRequirementDone() || !limitTick) super.tick()
   }
   
   override fun scroll() {
      val isDone = isRequirementDone()
      isVisible = !(!isDone && limitVisibility)
      if (isDone || !limitScroll) super.scroll()
   }
}

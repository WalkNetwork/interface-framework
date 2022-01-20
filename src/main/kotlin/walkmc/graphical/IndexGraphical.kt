package walkmc.graphical

import walkmc.*
import walkmc.extensions.*
import walkmc.extensions.collections.*
import walkmc.graphical.common.*
import walkmc.graphical.dsl.*
import walkmc.graphical.engines.*

/**
 * A graphical that's permits building index engines in time.
 */
abstract class IndexGraphical<T>(title: String, size: Int = 6) : ScrollGraphical(title, size) {
   internal var buildIndexCallback: ((T, Int) -> Engine)? = null
   
   open var isEmpty = false
   open var emptyEngine = emptyIndexEngine(midSlot(size / 2), newItem(Materials.BARRIER, "§cVazio."))
   
   /**
    * A list holding the index values. This is used to map this indexable graphical.
    */
   open var indexes: Iterable<T> = emptyList()
   
   /**
    * A required function to build the engine index of this indexable graphical.
    */
   abstract fun buildIndex(value: T, index: Int): Engine
   
   /**
    * Indexes this graphical to correctly order.
    */
   open fun indexes() {
      val values = indexes.mapIndexed { index, value -> buildIndex(value, index) }
      isEmpty = values.isEmpty()
      updateSource(values)
   }
   
   // index graphical has special case for scrolling
   override fun scrollTo(to: Int) {
      // fast non possible scroll
      if (to < 1 || hasScrolled && to > pageCount)
         return
      
      if (isEmpty) {
         install(emptyEngine)
         // avoid showing scrolls engines.
         scrollDownEngine.turnVisibilityOff()
         scrollUpEngine.turnVisibilityOff()
      } else {
         emptyEngine.turnVisibilityOff()
      }
      
      super.scrollTo(to)
   }
   
   /**
    * Updates [source] elements by clearing adding all [elements].
    */
   fun updateSource(elements: Iterable<Engine>) {
      source.clear()
      source += elements
   }
   
   /**
    * Builds [source] elements by clearing and calling [buildIndex]
    * for every element in [elements].
    */
   fun buildSource(elements: Iterable<T>) {
      isEmpty = elements.isEmpty()
      source.clear()
      source += elements.mapIndexed { index, value -> buildIndex(value, index) }
   }
   
   fun requestIndex(callback: (T, Int) -> Engine) {
      this.buildIndexCallback = callback
   }
   
   inline fun editEmpty(block: EmptyIndexEngine.() -> Unit) {
      emptyEngine.apply(block).notifyChange()
   }
}


open class StandardIndexGraphical<T>(title: String, size: Int) : IndexGraphical<T>(title, size) {
   override fun buildIndex(value: T, index: Int): Engine {
      return buildIndexCallback!!.invoke(value, index)
   }
}

/**
 * Indexes and scrolls to the specified [page] of this graphical.
 */
fun IndexGraphical<*>.scrollIndexing(page: Int = 1) {
   indexes()
   scrollTo(page)
}

package walkmc.graphical

import org.bukkit.inventory.*
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
   open var emptyEngine = makeEngine(midSlot(size / 2), EmptyIndexEngine.default()) {}
   
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
      isEmpty = indexes.isEmpty()
      source.removeIf { !it.isPersistent }
      source += indexes.mapIndexed { index, value -> buildIndex(value, index) }
   }
   
   // index graphical has special case for scrolling
   override fun scrollTo(to: Int) {
      // fast non possible or equals scroll
      if (to < 1 || hasScrolled && to > scrollSize)
         return
      
      if (isEmpty) {
         install(emptyEngine)
         
         // required to avoid showing scrolls engines.
         scrollDownEngine.turnVisibilityOff()
         scrollUpEngine.turnVisibilityOff()
      }
      
      if (source.isEmpty()) {
         uninstallAllNonPersistents()
         return
      }
      
      val mapped = mapper.map(this, source)
      
      page = to
      pages = mapped
      
      val engines = currentEngines
      if (hasScrolled) uninstallAllNonPersistents()
      
      val limit = installPerScroll
      var installed = 0
      for (index in schema) {
         if (installed >= limit || installed >= engines.size)
            break
         
         install(index, engines[installed])
         installed++
      }
      
      if (!hasScrolled) {
         install(scrollUpEngine)
         install(scrollDownEngine)
      }
      
      scrolleds++
      scrollAll()
   }
   
   fun requestIndex(callback: (T, Int) -> Engine) {
      this.buildIndexCallback = callback
   }
   
   fun notifyIfEmpty() {
      if (isEmpty) install(emptyEngine)
   }
   
   fun withEmptyEngine(engine: EmptyIndexEngine): IndexGraphical<T> {
      emptyEngine.alter(engine)
      return this
   }
   
   fun withEmptyEngine(slot: Int, item: ItemStack): IndexGraphical<T> {
      emptyEngine.relocate(slot).alter(item)
      return this
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

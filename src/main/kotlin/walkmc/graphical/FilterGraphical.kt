package walkmc.graphical

import walkmc.*
import walkmc.collections.*
import walkmc.extensions.*
import walkmc.graphical.common.*
import walkmc.graphical.interfaces.*

/**
 * A graphical interface with compatibility to filter their engines and indexes.
 */
abstract class FilterGraphical<T>(title: String, size: Int = 6) : IndexGraphical<T>(title, size), Filterable<T> {
   
   open var limit = Int.MAX_VALUE
   
   override var filterOptions = IndexList<FilterPair<T>>()
   override var isFilterDisabled = false
   override var filterEngine = newItem(Materials.HOPPER, "§dFiltro").toFilterEngine(midSlot(lines))
   
   override fun indexes() {
      buildSource(filtered(indexes))
   }
   
   override fun initEngines() {
      super.initEngines()
      install(filterEngine)
   }
   
   /**
    * Filters all [indexes] by the given [filter] and updates [isEmpty] and [source].
    */
   inline fun filtering(scroll: Boolean = true, filter: Filter<T>) {
      buildSource(indexes.filter(filter).take(limit))
      if (scroll) scrollTo()
   }
   
   override fun filtered(elements: Iterable<T>): List<T> {
      return if (isFilterDisabled || currentFilter == null) {
         elements.toList().take(limit)
      } else {
         elements.filter(currentFilter!!).take(limit)
      }
   }
   
   override fun toNextFilter() {
      filterOptions.toNextOrFirst()
      scrollIndexing()
   }
   
   override fun toPreviousFilter() {
      filterOptions.toPreviousOrLast()
      scrollIndexing()
   }
   
   override fun disableFilter() {
      isFilterDisabled = true
      scrollIndexing()
   }
   
   override fun enableFilter() {
      isFilterDisabled = false
      scrollIndexing()
   }
}

/**
 * A standard implementation of [FilterGraphical].
 */
class StandardFilterGraphical<T>(title: String, size: Int) : FilterGraphical<T>(title, size) {
   override fun buildIndex(value: T, index: Int): Engine {
      return buildIndexCallback!!.invoke(value, index)
   }
}

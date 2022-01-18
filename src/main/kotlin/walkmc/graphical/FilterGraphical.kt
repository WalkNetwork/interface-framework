package walkmc.graphical

import walkmc.*
import walkmc.collections.*
import walkmc.extensions.*
import walkmc.graphical.common.*
import walkmc.graphical.dsl.*
import walkmc.graphical.engines.*

typealias FilterPair<T> = Pair<String, Filter<T>>

/**
 * A graphical interface with compatibility to filter their engines and indexes.
 */
abstract class FilterGraphical<T>(title: String, size: Int = 6) : IndexGraphical<T>(title, size) {
   
   open var options = IndexList<FilterPair<T>>()
   open var limit = Int.MAX_VALUE
   open var isFilterDisabled = false
   
   val currentFilter get() = options.currentOrNull()?.second
   val texts get() = options.map { it.first }
   val filters get() = options.map { it.second }
   
   open var filterEngine = filterEngine(midSlot(lines), newItem(Materials.HOPPER, "§dFiltro")) {}
   
   override fun indexes() {
      val values = if (isFilterDisabled || currentFilter == null) {
         indexes.toList().take(limit)
      } else {
         indexes.filter(currentFilter!!).take(limit)
      }
   
      isEmpty = values.isEmpty()
      source.removeIf { !it.isPersistent }
      source += values.mapIndexed { index, value -> buildIndex(value, index) }
   }
   
   fun toNextFilter() {
      options.toNextOrFirst()
      scrollIndexing()
   }
   
   fun toPreviousFilter() {
      options.toPreviousOrLast()
      scrollIndexing()
   }
   
   fun disableFilter() {
      isFilterDisabled = true
      scrollIndexing()
   }
   
   fun enableFilter() {
      isFilterDisabled = false
      scrollIndexing()
   }
   
   inline fun editFilter(block: FilterEngine.() -> Unit) {
      filterEngine.apply(block)
   }
   
   fun addFilter(text: String, filter: Filter<T>) = options.add(text to filter)
   fun addNoFilter(text: String) = addFilter(text) { true }
}

/**
 * An standard implementation of [FilterGraphical].
 */
class StandardFilterGraphical<T>(title: String, size: Int) : FilterGraphical<T>(title, size) {
   override fun buildIndex(value: T, index: Int): Engine {
      return buildIndexCallback!!.invoke(value, index)
   }
}

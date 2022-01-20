package walkmc.graphical.interfaces

import walkmc.collections.*
import walkmc.graphical.common.*
import walkmc.graphical.engines.*

typealias FilterPair<T> = Pair<String, Filter<T>>

/**
 * Represents a graphical that's can filter.
 */
interface Filterable<T> {
   
   /**
    * All data holding the filter features.
    */
   var filterOptions: IndexList<FilterPair<T>>
   
   /**
    * Returns if the filter is enabled. If not enabled, elements will not be filtered.
    */
   var isFilterDisabled: Boolean
   
   /**
    * The current displaying filter.
    */
   val currentFilter get() = filterOptions.currentOrNull()?.second
   
   /**
    * All filters texts in [filterOptions].
    */
   val filterTexts get() = filterOptions.map { it.first }
   
   /**
    * All filters in [filterOptions].
    */
   val filters get() = filterOptions.map { it.second }
   
   /**
    * The filter engine used to display [filterOptions] and travelling between filters.
    */
   var filterEngine: FilterEngine
   
   /**
    * Returns [T] elements filtered.
    */
   fun filtered(elements: Iterable<T>): List<T>
   
   /**
    * Go to the next filter.
    */
   fun toNextFilter()
   
   /**
    * Go to the previous filter.
    */
   fun toPreviousFilter()
   
   /**
    * Disables the filter.
    */
   fun disableFilter()
   
   /**
    * Enables the filter.
    */
   fun enableFilter()
   
   /**
    * Adds the specified [filter] filtering [T] elements with [text].
    */
   fun addFilter(text: String, filter: Filter<T>) {
      filterOptions.add(text to filter)
   }
}

/**
 * Edits the filter engine applying the given [block].
 */
inline fun <T> Filterable<T>.editFilter(block: FilterEngine.() -> Unit) {
   filterEngine.apply(block)
}

/**
 * Adds an empty filter.
 *
 * Empty filters don't filter anything is just for information purposes.
 */
fun <T> Filterable<T>.addNoFilter(text: String) = addFilter(text) { true }

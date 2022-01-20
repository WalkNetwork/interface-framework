package walkmc.graphical.interfaces

import walkmc.collections.*
import walkmc.graphical.engines.*

typealias SortPair<T> = Pair<String, Comparator<T>>

/**
 * Represents a graphical that's can sort.
 */
interface Sortable<T> {
   
   /**
    * All data holding the sorter features.
    */
   var sorterOptions: IndexList<SortPair<T>>
   
   /**
    * Returns if the sorter is enabled. If not enabled, elements will not be sorted.
    */
   var isSorterDisabled: Boolean
   
   /**
    * The current displaying sorter.
    */
   val currentSorter get() = sorterOptions.currentOrNull()?.second
   
   /**
    * All sorters text in [sorterOptions].
    */
   val sorterTexts get() = sorterOptions.map { it.first }
   
   /**
    * All sorters in [sorterOptions].
    */
   val sorters get() = sorterOptions.map { it.second }
   
   /**
    * The sorter engine used to display [sorterTexts] and travelling between sorters.
    */
   var sorterEngine: SorterEngine
   
   /**
    * Returns [T] elements sorted.
    */
   fun sorted(elements: Iterable<T>): List<T>
   
   /**
    * Go to the next sorter.
    */
   fun toNextSorter()
   
   /**
    * Go to the previous sorter.
    */
   fun toPreviousSorter()
   
   /**
    * Disables the sorter.
    */
   fun disableSorter()
   
   /**
    * Enables the sorter.
    */
   fun enableSorter()
   
   /**
    * Adds a new sorter comparing [T] values with [comparator].
    */
   fun addSorter(text: String, comparator: Comparator<T>) {
      sorterOptions.add(text to comparator)
   }
   
   /**
    * Adds a new sorter comparing [T] values with [comparing].
    */
   fun addSorter(text: String, comparing: (T) -> Comparable<*>) {
      sorterOptions.add(text to compareBy(comparing))
   }
   
   /**
    * Adds a new sorter comparing [T] values with [comparing] in descending order.
    */
   fun addSorterDescending(text: String, comparing: (T) -> Comparable<*>) {
      sorterOptions.add(text to compareByDescending(comparing))
   }
}

/**
 * Edits the sorter engine applying the given [block].
 */
inline fun <T> Sortable<T>.editSorter(block: SorterEngine.() -> Unit) {
   sorterEngine.apply(block)
}

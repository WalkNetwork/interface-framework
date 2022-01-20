package walkmc.graphical

import walkmc.*
import walkmc.collections.*
import walkmc.extensions.*
import walkmc.graphical.common.*
import walkmc.graphical.interfaces.*

/**
 * A graphical interface with compatibility to sort and filter their engines indexes.
 */
abstract class SortFilterGraphical<T>(
   title: String, lines: Int = 6
) : IndexGraphical<T>(title, lines), Sortable<T>, Filterable<T> {
   
   open var limit = Int.MAX_VALUE
   
   override var sorterOptions = IndexList<SortPair<T>>()
   override var isSorterDisabled = false
   override var sorterEngine = newItem(Materials.CHEST, "§dOrdem").toSorterEngine(midSlot(lines) + 1)
   
   override var filterOptions = IndexList<FilterPair<T>>()
   override var isFilterDisabled = false
   override var filterEngine = newItem(Materials.HOPPER, "§dFiltro").toFilterEngine(midSlot(lines) - 1)
   
   override fun indexes() {
      buildSource(sorted(filtered(indexes))) // order is important
   }
   
   override fun initEngines() {
      super.initEngines()
      install(sorterEngine)
      install(filterEngine)
   }
   
   /**
    * Sorts all [indexes] by the given [comparator] and updates [isEmpty] and [source].
    */
   fun sorting(comparator: Comparator<T>, scroll: Boolean = true) {
      buildSource(indexes.sortedWith(comparator).take(limit))
      if (scroll) scrollTo()
   }
   
   /**
    * Sorts all [indexes] by the given [comparator] and updates [isEmpty] and [source].
    */
   inline fun sorting(scroll: Boolean = true, crossinline comparator: (T) -> Comparable<*>) {
      sorting(compareBy(comparator), scroll)
   }
   
   /**
    * Sorts all [indexes] in descending order by the given [comparator]
    * and updates [isEmpty] and [source].
    */
   inline fun sortingDescending(scroll: Boolean = true, crossinline comparator: (T) -> Comparable<*>) {
      sorting(compareByDescending(comparator), scroll)
   }
   
   /**
    * Filters all [indexes] by the given [filter] and updates [isEmpty] and [source].
    */
   inline fun filtering(scroll: Boolean = true, filter: Filter<T>) {
      buildSource(indexes.filter(filter).take(limit))
      if (scroll) scrollTo()
   }
   
   override fun sorted(elements: Iterable<T>): List<T> {
      return if (isSorterDisabled || currentSorter == null) {
         elements.take(limit)
      } else {
         elements.sortedWith(currentSorter!!).take(limit)
      }
   }
   
   override fun filtered(elements: Iterable<T>): List<T> {
      return if (isFilterDisabled || currentFilter == null) {
         elements.toList()
      } else {
         elements.filter(currentFilter!!)
      }
   }
   
   override fun toNextSorter() {
      sorterOptions.toNextOrFirst()
      scrollIndexing()
   }
   
   override fun toPreviousSorter() {
      sorterOptions.toPreviousOrLast()
      scrollIndexing()
   }
   
   override fun disableSorter() {
      isSorterDisabled = true
      scrollIndexing()
   }
   
   override fun enableSorter() {
      isSorterDisabled = false
      scrollIndexing()
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
 * A standard implementation of [SortFilterGraphical].
 */
class StandardSortFilterGraphical<T>(title: String, size: Int) : SortFilterGraphical<T>(title, size) {
   override fun buildIndex(value: T, index: Int): Engine {
      return buildIndexCallback!!.invoke(value, index)
   }
}

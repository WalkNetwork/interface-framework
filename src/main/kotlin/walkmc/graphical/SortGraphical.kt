package walkmc.graphical

import walkmc.*
import walkmc.collections.*
import walkmc.extensions.*
import walkmc.graphical.common.*
import walkmc.graphical.interfaces.*

/**
 * A graphical interface with compatibility to sort their engines and indexes.
 */
abstract class SortGraphical<T>(title: String, size: Int = 6) : IndexGraphical<T>(title, size), Sortable<T> {
   
   open var limit = Int.MAX_VALUE
   
   override var sorterOptions = IndexList<SortPair<T>>()
   override var isSorterDisabled = false
   override var sorterEngine = newItem(Materials.HOPPER, "§dOrdem").toSorterEngine(midSlot(lines))
   
   override fun indexes() {
      buildSource(sorted(indexes))
   }
   
   override fun initEngines() {
      super.initEngines()
      install(sorterEngine)
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
   
   override fun sorted(elements: Iterable<T>): List<T> {
      return if (isSorterDisabled || currentSorter == null) {
         elements.toList().take(limit)
      } else {
         elements.sortedWith(currentSorter!!).take(limit)
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
}

/**
 * A standard implementation of [SortGraphical].
 */
class StandardSortGraphical<T>(title: String, size: Int) : SortGraphical<T>(title, size) {
   override fun buildIndex(value: T, index: Int): Engine {
      return buildIndexCallback!!.invoke(value, index)
   }
}

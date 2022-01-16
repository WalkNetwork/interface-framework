package walkmc.graphical

import walkmc.*
import walkmc.collections.*
import walkmc.extensions.*
import walkmc.graphical.common.*
import walkmc.graphical.dsl.*

typealias SortPair<T> = Pair<String, Comparator<T>>

/**
 * A graphical interface with compatibility to sort their engines and indexes.
 */
abstract class SortGraphical<T>(title: String, size: Int = 6) : IndexGraphical<T>(title, size) {
   
   open var options = IndexList<SortPair<T>>()
   open var limit = Int.MAX_VALUE
   open var isSorterDisabled = false
   
   val currentSorter get() = options.currentOrNull()?.second
   val texts get() = options.map { it.first }
   val sorters get() = options.map { it.second }
   
   open var sorterEngine = sorterEngine(midSlot(lines), newItem(Materials.HOPPER, "§dOrdem")) {}
   
   override fun indexes() {
      val values = if (isSorterDisabled || currentSorter == null) {
         indexes.toList().take(limit)
      } else {
         indexes.sortedWith(currentSorter!!).take(limit)
      }
   
      isEmpty = values.isEmpty()
      source.removeIf { !it.isPersistent }
      source += values.mapIndexed { index, value -> buildIndex(value, index) }
   }
   
   fun toNextSorter() {
      options.toNextOrFirst()
      scrollIndexing()
   }
   
   fun toPreviousSorter() {
      options.toPreviousOrLast()
      scrollIndexing()
   }
   
   fun disableSorter() {
      isSorterDisabled = true
      scrollIndexing()
   }
   
   fun enableSorter() {
      isSorterDisabled = false
      scrollIndexing()
   }
   
   fun addSorter(text: String, comparator: Comparator<T>) {
      options.add(text to comparator)
   }
   
   inline fun addSorter(text: String, crossinline comparator: (T) -> Comparable<*>) {
      addSorter(text, compareBy(comparator))
   }
   
   inline fun addSorterDescending(text: String, crossinline comparator: (T) -> Comparable<*>) {
      addSorter(text, compareByDescending(comparator))
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

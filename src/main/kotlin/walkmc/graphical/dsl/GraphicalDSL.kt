package walkmc.graphical.dsl

import walkmc.graphical.*

/**
 * Constructs a new graphical with the given [title] and [lines].
 */
inline fun graphical(title: String, lines: Int, block: Graphical.() -> Unit) =
   StandardGraphical(title, lines).apply(block)

/**
 * Constructs a new scrollable graphical with the given [title] and [lines].
 */
inline fun scrollable(title: String, lines: Int, block: ScrollGraphical.() -> Unit) =
   StandardScrollGraphical(title, lines).apply(block)

/**
 * Constructs a new indexable graphical with the given [title] and [lines].
 *
 * ### Note: This function automatically calls [IndexGraphical.scrollIndexing].
 */
inline fun <T> indexable(title: String, lines: Int, block: IndexGraphical<T>.() -> Unit) =
   StandardIndexGraphical<T>(title, lines).apply {
      block()
      scrollIndexing()
   }

/**
 * Constructs a new indexable graphical with the given [title] and [lines] and pre-builded [indexes].
 *
 * ### Note: This function automatically calls [IndexGraphical.scrollIndexing].
 */
inline fun <T> indexable(title: String, lines: Int, indexes: Iterable<T>, block: IndexGraphical<T>.() -> Unit) =
   StandardIndexGraphical<T>(title, lines).apply {
      block()
      this.indexes = indexes
      scrollIndexing()
   }

/**
 * Constructs a new filterable graphical with the given [title] and [lines].
 *
 * ### Note: This function automatically calls [FilterGraphical.scrollIndexing].
 */
inline fun <T> filterable(title: String, lines: Int, block: FilterGraphical<T>.() -> Unit) =
   StandardFilterGraphical<T>(title, lines).apply {
      block()
      scrollIndexing()
   }

/**
 * Constructs a new filterable graphical with the given [title] and [lines] and pre-builded [indexes].
 *
 * ### Note: This function automatically calls [FilterGraphical.scrollIndexing].
 */
inline fun <T> filterable(title: String, lines: Int, indexes: Iterable<T>, block: FilterGraphical<T>.() -> Unit) =
   StandardFilterGraphical<T>(title, lines).apply {
      block()
      this.indexes = indexes
      scrollIndexing()
   }

/**
 * Constructs a new sortable graphical with the given [title] and [lines].
 *
 * ### Note: This function automatically calls [SortGraphical.scrollIndexing].
 */
inline fun <T> sortable(title: String, lines: Int, block: SortGraphical<T>.() -> Unit) =
   StandardSortGraphical<T>(title, lines).apply {
      block()
      scrollIndexing()
   }

/**
 * Constructs a new sortable graphical with the given [title] and [lines] and pre-builded [indexes].
 *
 * ### Note: This function automatically calls [SortGraphical.scrollIndexing].
 */
inline fun <T> sortable(title: String, lines: Int, indexes: Iterable<T>, block: SortGraphical<T>.() -> Unit) =
   StandardSortGraphical<T>(title, lines).apply {
      block()
      this.indexes = indexes
      scrollIndexing()
   }

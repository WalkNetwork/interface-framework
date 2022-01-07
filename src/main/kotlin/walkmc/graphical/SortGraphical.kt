package walkmc.graphical

/**
 * A graphical interface with compatibility to sort their engines and indexes.
 */
abstract class SortGraphical<T>(title: String, size: Int = 4) : IndexGraphical<T>(title, size) {
	
	/**
	 * The comparator used to sorts and compares the ranking indexes between this sort graphical.
	 */
	open var comparator: Comparator<T>? = null
	
	/**
	 * The limit that this sort graphical can sort. By default it's [Int.MAX_VALUE].
	 */
	open var limit: Int = Int.MAX_VALUE
	
	override fun indexes() {
		source.removeIf { !it.isPersistent }
		
		val values = if (comparator == null) indexes else indexes.sortedWith(comparator!!)
		
		source += values
			.take(limit)
			.mapIndexed { index, rank -> buildIndex(rank, index) }
	}
	
	/**
	 * Disables the sort of this filter graphical, this is, that this graphical
	 * will not more sort for any indexes.
	 *
	 * It's responsability of the user to call [updatePage] to update the page.
	 */
	fun disableSort(update: Boolean = true) {
		comparator = null
		indexes()
		
		if (update)
			scrollTo()
	}
	
	/**
	 * Compares this filter graphical indexes and updates them.
	 *
	 * It's responsability of the user to call [updatePage] to update the page.
	 */
	fun sorting(comparator: Comparator<T>, update: Boolean = true) {
		this.comparator = comparator
		indexes()
		
		if (update)
			scrollTo()
	}
	
	/**
	 * Compares this filter graphical indexes and updates them.
	 *
	 * It's responsability of the user to call [updatePage] to update the page.
	 */
	inline fun sorting(update: Boolean = true, crossinline comparator: (T) -> Comparable<*>) =
		sorting(compareBy(comparator), update)
	
	/**
	 * Compares this filter graphical indexes and updates them.
	 *
	 * It's responsability of the user to call [updatePage] to update the page.
	 */
	inline fun sortingDescending(update: Boolean = true, crossinline comparator: (T) -> Comparable<*>) =
		sorting(compareByDescending(comparator), update)
}

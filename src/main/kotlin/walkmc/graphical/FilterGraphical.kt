package walkmc.graphical

import walkmc.graphical.common.*

/**
 * A graphical interface with compatibility to filter their engines and indexes.
 */
abstract class FilterGraphical<T>(title: String, size: Int = 4) : IndexGraphical<T>(title, size) {
	
	/**
	 * The comparator used to sorts and compares the ranking indexes between this filter graphical.
	 */
	open var filter: Filter<T>? = null
	
	/**
	 * The limit that this filter graphical can sort. By default it's [Int.MAX_VALUE].
	 */
	open var limit: Int = Int.MAX_VALUE
	
	override fun indexes() {
		source.removeIf { !it.isPersistent }
		
		val values = if (filter == null) indexes else indexes.filter(filter!!)
		
		source += values
			.take(limit)
			.mapIndexed { index, rank -> buildIndex(rank, index) }
	}
	
	/**
	 * Disables the filter of this filter graphical, this is, that this graphical
	 * will not more filter for any indexes.
	 *
	 * It's responsability of the user to call [updatePage] to update the page.
	 */
	fun disableFilter(update: Boolean = true) {
		filter = null
		indexes()
		
		if (update)
			scrollTo()
	}
	
	/**
	 * Filters this filter graphical indexes and updates them.
	 *
	 * It's responsability of the user to call [updatePage] to update the page.
	 */
	fun filtering(update: Boolean = true, filter: (T) -> Boolean) {
		this.filter = filter
		indexes()
		
		if (update)
			scrollTo()
	}
}

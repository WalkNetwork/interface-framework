package walkmc.graphical

/**
 * A graphical that's permits building index engines in time.
 */
abstract class IndexGraphical<T>(title: String, size: Int) : ScrollGraphical(title, size) {
	
	/**
	 * A list holding the ranking index values. This is used to sort this indexable graphical.
	 */
	open var indexes: List<T> = emptyList()
	
	/**
	 * A required function to build the engine ranking index of this indexable graphical.
	 */
	abstract fun buildIndex(value: T, index: Int): Engine
	
	/**
	 * Indexes this graphical to correctly order.
	 */
	open fun indexes() {
		source.removeIf { !it.isPersistent }
		source += indexes.mapIndexed { index, rank -> buildIndex(rank, index) }
	}
}

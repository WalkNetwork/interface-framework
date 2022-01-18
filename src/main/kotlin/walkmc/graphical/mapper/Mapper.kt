package walkmc.graphical.mapper

import walkmc.graphical.*

/**
 * A mapper is a way to modify the order of
 * the scrollable engines of a scrollable graphical interface.
 * You can create your own mapper implementing this interface.
 * @see SingularMapper
 * @see PartialMapper
 */
interface Mapper {
	
	/**
	 * Maps all scrollable engines to a [Scrollers].
	 */
	fun map(graphical: IScrollGraphical, engines: Source): Scrollers
}

/**
 * Creates a new [Mapper] by invoking the specified [block] to map.
 */
inline fun mapping(crossinline block: IScrollGraphical.(Source) -> Scrollers): Mapper {
	return object : Mapper {
		override fun map(graphical: IScrollGraphical, engines: Source): Scrollers {
			return block(graphical, engines)
		}
	}
}

/**
 * Creates a new [Mapper] mapping engines by the specified [size]
 */
fun mappingBySize(size: Int, step: Int = 1, partial: Boolean = true): Mapper {
	return mapping { it.windowed(size, step, partial) }
}

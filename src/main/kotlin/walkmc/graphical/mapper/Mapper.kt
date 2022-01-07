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
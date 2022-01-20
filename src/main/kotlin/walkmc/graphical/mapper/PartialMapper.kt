package walkmc.graphical.mapper

import walkmc.graphical.*

/**
 * A partial mapper is a [Mapper] that will
 * map all scrollable engines in partial pages, this is
 * when scrolling up or down, the mapper will map only
 * [IScrollGraphical.enginesPerPage] engines.
 * This is the default mapper.
 */
object PartialMapper : Mapper {
	override fun map(graphical: IScrollGraphical, engines: Source): Scrollers {
		return engines.chunked(graphical.enginesPerPage)
	}
}

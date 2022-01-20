package walkmc.graphical.mapper

import walkmc.extensions.collections.*
import walkmc.graphical.*

/**
 * A singular mapper is a [Mapper] that will
 * map all scrollable engines one by one.
 */
object SingularMapper : Mapper {
	override fun map(graphical: IScrollGraphical, engines: Source): Scrollers {
		return engines.singulared(graphical.enginesPerPage)
	}
}

package walkmc.graphical.schema

/**
 * A empty schematic implementation when mapping
 * a scrollable graphical interface.
 */
class EmptySchema(
	override var start: Int = 0,
	override var last: Int = 0,
	override var exclude: Excluder = DEFAULT_INCLUDER,
	override var include: Includer = DEFAULT_INCLUDER,
) : Schema
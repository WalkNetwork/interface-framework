package walkmc.graphical.schema

/**
 * The default schematic implementation when mapping
 * a scrollable graphical interface.
 */
class StandardSchema(
	override var start: Int = 10,
	override var last: Int = 34,
	override var exclude: Excluder = DEFAULT_EXCLUDER,
	override var include: Includer = DEFAULT_INCLUDER,
) : Schema
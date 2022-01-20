package walkmc.graphical.schema

/**
 * The default schematic implementation when mapping
 * a scrollable graphical interface.
 */
fun basicSchema() = 10 schema 34 exclude basicExcluder()

/**
 * An empty schematic implementation when mapping
 * a scrollable graphical interface.
 */
fun emptySchema(): Schema = HashSet()

/**
 * Creates a schema by all [elements].
 */
fun schemaOf(vararg elements: Int): Schema = elements.toHashSet()

/**
 * The defaults' excluder for the standard schema.
 */
fun basicExcluder() = schemaOf(17, 18, 26, 27)

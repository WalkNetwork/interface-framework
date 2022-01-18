package walkmc.graphical.schema

typealias Includer = MutableSet<Int>
typealias Excluder = MutableSet<Int>

/**
 * A schema represents how a Scroll graphical
 * should map your content of items.
 *
 * This is an extensive configurable, you can set the start and last slot.
 * Also, can exclude and include certains slots!
 */
typealias Schema = MutableSet<Int>

/**
 * Creates a schematic with this Int as lines
 * and the specified [last] as columns.
 */
infix fun Int.schema(last: Int): Schema {
   return (this..last).toHashSet()
}

/**
 * Includes all slots of the specified
 * includer to this schematic.
 */
fun Schema.includes(vararg includer: Int): Schema = includes(includer.toHashSet())

/**
 * Includes all slots of the specified
 * includer to this schematic.
 */
infix fun Schema.includes(includer: Iterable<Int>): Schema = apply {
   addAll(includer.toHashSet())
}

/**
 * Includes a specified slot to this schematic.
 */
infix fun Schema.includes(slot: Int): Schema = apply {
   add(slot)
}

/**
 * Excludes all slots of the specified
 * excluder to this schematic.
 */
fun Schema.excludes(vararg excluder: Int): Schema = excludes(excluder.toHashSet())

/**
 * Excludes all slots of the specified
 * excluder to this schematic.
 */
infix fun Schema.excludes(excluder: Iterable<Int>): Schema = apply {
   removeAll(excluder.toHashSet())
}

/**
 * Excludes a specified slot to this schematic.
 */
infix fun Schema.excludes(slot: Int): Schema = apply {
   remove(slot)
}

/**
 * Returns the minimum slot that's a
 * graphical user interface can set.
 */
const val MINIMUM_SLOT = 0

/**
 * Returns the maximum slot that's a
 * graphical user interface can set.
 */
const val MAXIMUM_SLOT = 53

/**
 * The defaults' excluder for all schematics.
 */
val DefaultExcluder: Excluder = hashSetOf(17, 18, 26, 27)

/**
 * The defaults' includer for all schematics.
 */
val DefaultIncluder: Includer = HashSet()

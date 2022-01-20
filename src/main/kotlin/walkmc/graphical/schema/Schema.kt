@file:Suppress("NOTHING_TO_INLINE")

package walkmc.graphical.schema

import walkmc.graphical.common.*

/**
 * A schema represents how a Scroll graphical
 * should map your content of items.
 *
 * This is an extensive configurable, you can set the start and last slot.
 * Also, can exclude and include certains slots!
 */
typealias Schema = MutableSet<Int>

/**
 * Creates a schema with this as start and [last] as end.
 */
inline infix fun Int.schema(last: Int): Schema {
   return (this..last).toHashSet()
}

/**
 * Creates a schema by [row].
 */
inline fun rowSchema(row: Int): Schema {
   return (startSlot(row)..lastSlot(row)).toHashSet()
}

/**
 * Includes all slots of the specified
 * includer to this schematic.
 */
inline fun Schema.include(vararg includer: Int): Schema {
   addAll(includer.toHashSet())
   return this
}

/**
 * Includes all slots of the specified
 * includer to this schematic.
 */
inline infix fun Schema.include(includer: Iterable<Int>): Schema {
   addAll(includer)
   return this
}

/**
 * Includes a specified slot to this schematic.
 */
inline infix fun Schema.include(slot: Int): Schema {
   add(slot)
   return this
}

/**
 * Includes all slots from [range].
 */
inline infix fun Schema.include(range: IntRange): Schema {
   addAll(range)
   return this
}

/**
 * Includes all slots of the specified [row].
 */
inline infix fun Schema.includeRow(row: Int): Schema {
   addAll(startSlot(row)..lastSlot(row))
   return this
}

/**
 * Excludes all slots of the specified
 * excluder to this schematic.
 */
inline fun Schema.exclude(vararg excluder: Int): Schema {
   removeAll(excluder.toHashSet())
   return this
}

/**
 * Excludes all slots of the specified excluder.
 */
infix fun Schema.exclude(excluder: Iterable<Int>): Schema {
   removeAll(excluder.toHashSet())
   return this
}

/**
 * Excludes all slots from [range].
 */
inline infix fun Schema.exclude(range: IntRange): Schema {
   removeAll(range)
   return this
}

/**
 * Excludes all slots of the specified [row].
 */
inline infix fun Schema.excludeRow(row: Int): Schema {
   removeAll(startSlot(row)..lastSlot(row))
   return this
}

/**
 * Excludes a specified slot.
 */
inline infix fun Schema.exclude(slot: Int): Schema {
   remove(slot)
   return this
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
val DefaultExcluder: Schema = schemaOf(17, 18, 26, 27)

/**
 * The defaults' includer for all schematics.
 */
val DefaultIncluder: Schema = emptySchema()

fun Iterable<Int>.toSchema(): Schema = toHashSet()
fun IntArray.toSchema(): Schema = toHashSet()

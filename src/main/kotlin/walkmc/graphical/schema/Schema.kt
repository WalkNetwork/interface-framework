package walkmc.graphical.schema

import com.google.common.collect.*
import walkmc.extensions.*
import walkmc.extensions.collections.*
import kotlin.time.*

typealias Includer = MutableSet<Int>
typealias Excluder = MutableSet<Int>

/**
 * A schematic represents how a [IScrollGraphical]
 * should map your content of items. This is a extensive configurable,
 * you can sets the start and last slot. Can configure the jump amount
 * between each item. Also can exclude and include certains slots!
 */
interface Schema : Iterable<Int> {
	
	/**
	 * The start slot that can be mapped.
	 */
	var start: Int
	
	/**
	 * The last slot that can be mapped.
	 */
	var last: Int
	
	/**
	 * A [MutableSet] that works like a excluder,
	 * excluding the specified slots to `NOT` map a item.
	 */
	var exclude: Excluder
	
	/**
	 * A [MutableSet] that works like a includer,
	 * including the specified slots to map a item.
	 */
	var include: Includer
	
	override fun iterator(): Iterator<Int> {
		return (((start..last) + include) - exclude).iterator()
	}
}

/**
 * Creates a schematic with this Int as lines
 * and the specified [last] as columns.
 */
infix fun Int.schema(last: Int): Schema {
	return StandardSchema(this, last)
}

/**
 * Includes all slots of the specified
 * includer to this schematic.
 */
fun Schema.includes(vararg includer: Int): Schema = includes(includer.toSet())

/**
 * Includes all slots of the specified
 * includer to this schematic.
 */
infix fun Schema.includes(includer: Iterable<Int>): Schema = apply {
	val set = includer.toSet() // for performance
	include += set
	exclude.removeAll(set)
}

/**
 * Includes a specified slot to this schematic.
 */
infix fun Schema.includes(slot: Int): Schema = apply {
	include.add(slot)
	exclude.remove(slot)
}

/**
 * Excludes all slots of the specified
 * excluder to this schematic.
 */
fun Schema.excludes(vararg excluder: Int): Schema = excludes(excluder.toSet())

/**
 * Excludes all slots of the specified
 * excluder to this schematic.
 */
infix fun Schema.excludes(excluder: Iterable<Int>): Schema = apply {
	val set = excluder.toSet() // for performance
	exclude.addAll(set)
	include.removeAll(set)
}

/**
 * Excludes a specified slot to this schematic.
 */
infix fun Schema.excludes(slot: Int): Schema = apply {
	exclude.add(slot)
	include.remove(slot)
}

/**
 * Returns the minimum slot thats a
 * graphical user interface can set.
 */
const val MINIMUM_SLOT = 0

/**
 * Returns the maximum slot thats a
 * graphical user interface can set.
 */
const val MAXIMUM_SLOT = 53

/**
 * The defaults excluder for all schematics.
 */
val DEFAULT_EXCLUDER: Excluder = mutableSetOf(17, 18, 26, 27)

/**
 * The defaults includer for all schematics.
 */
val DEFAULT_INCLUDER: Includer = emptyMutableSet()

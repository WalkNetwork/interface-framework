package io.github.uinnn.interfaces.schematic

import io.github.uinnn.interfaces.ScrollableGraphicalInterface

typealias Includer = HashSet<Int>
typealias Excluder = HashSet<Int>

/**
 * A schematic represents how a [ScrollableGraphicalInterface]
 * should map your content of items. This is a extensive configurable,
 * you can sets the start and last slot. Can configure the jump amount
 * between each item. Also can exclude and include certains slots!
 */
interface Schematic {

  /**
   * The start slot that can be mapped.
   */
  var start: Int

  /**
   * The last slot that can be mapped.
   */
  var last: Int

  /**
   * A [HashSet] that works like a excluder,
   * excluding the specified slots to `NOT` map a item.
   */
  var exclude: Excluder

  /**
   * A [HashSet] that works like a includer,
   * including the specified slots to map a item.
   */
  var include: Includer
}

/**
 * Creates a schematic with this Int as lines
 * and the specified [last] as columns.
 */
infix fun Int.schematic(last: Int): Schematic {
  return DefaultSchematic(this, last)
}

/**
 * Includes all slots of the specified
 * includer to this schematic.
 */
fun Schematic.includes(vararg includer: Int): Schematic = apply {
  val to = includer.toMutableList()
  include += to
  exclude.removeAll(to)
}

/**
 * Includes all slots of the specified
 * includer to this schematic.
 */
infix fun Schematic.includes(includer: List<Int>): Schematic = apply {
  include += includer
  exclude.removeAll(includer)
}

/**
 * Includes a specified slot to this schematic.
 */
infix fun Schematic.includes(slot: Int): Schematic = apply {
  include += slot
  exclude.remove(slot)
}

/**
 * Excludes all slots of the specified
 * excluder to this schematic.
 */
fun Schematic.excludes(vararg excluder: Int): Schematic = apply {
  val to = excluder.toMutableList()
  exclude += to
  include.removeAll(to)
}

/**
 * Excludes all slots of the specified
 * excluder to this schematic.
 */
infix fun Schematic.excludes(excluder: List<Int>): Schematic = apply {
  exclude += excluder
  include.removeAll(excluder)
}

/**
 * Excludes a specified slot to this schematic.
 */
infix fun Schematic.excludes(slot: Int): Schematic = apply {
  exclude += slot
  include.remove(slot)
}

/**
 * Verifies if a slot is in a range of this [Schematic]
 */
fun Schematic.isInRange(slot: Int): Boolean {
  return if (exclude.contains(slot)) false
  else include.contains(slot) || slot in start..last
}

/**
 * Creates a new empty includer.
 */
fun emptyIncluder(): Includer = HashSet()

/**
 * Creates a new includer with
 * specified slots.
 */
fun includerOf(vararg slots: Int): Includer = HashSet<Int>().apply {
  for (slot in slots) add(slot)
}

/**
 * Creates a new empty excluder.
 */
fun emptyExcluder(): Excluder = HashSet()

/**
 * Creates a new excluder with
 * specified slots.
 */
fun excluderOf(vararg slots: Int): Excluder = HashSet<Int>().apply {
  for (slot in slots) add(slot)
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
val DEFAULT_EXCLUDER = excluderOf(17, 18, 26, 27)

/**
 * The defaults includer for all schematics.
 */
val DEFAULT_INCLUDER = emptyIncluder()
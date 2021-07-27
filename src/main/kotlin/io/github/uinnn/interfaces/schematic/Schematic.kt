package io.github.uinnn.interfaces.schematic

import io.github.uinnn.interfaces.PaginatedGraphicalInterface

typealias Includer = IntArray
typealias Excluder = IntArray

/**
 * Creates a new empty includer.
 */
fun emptyIncluder(): Includer = intArrayOf()

/**
 * Creates a new includer with
 * specified slots.
 */
fun includerOf(vararg slots: Int): Includer = intArrayOf(*slots)

/**
 * Creates a new empty excluder.
 */
fun emptyExcluder(): Excluder = intArrayOf()

/**
 * Creates a new excluder with
 * specified slots.
 */
fun excluderOf(vararg slots: Int): Excluder = intArrayOf(*slots)

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
val DEFAULT_EXCLUDER = emptyExcluder()

/**
 * The defaults includer for all schematics.
 */
val DEFAULT_INCLUDER = emptyIncluder()

/**
 * A schematic represents how a [PaginatedGraphicalInterface]
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
   * The amount of a item will be mapped
   * between each other.
   */
  var jump: Int

  /**
   * A [IntArray] that works like a excluder,
   * excluding the specified slots to `NOT` map a item.
   */
  var exclude: Excluder

  /**
   * A [IntArray] that works like a includer,
   * including the specified slots to map a item.
   */
  var include: Includer
}

/**
 * Creates a schematic with this Int as start
 * and the specified [last] as last.
 */
infix fun Int.schematic(last: Int): Schematic {
  return DefaultSchematic(this, last)
}

/**
 * Sets the jump of this schematic with
 * the specified jump.
 */
infix fun Schematic.jump(jump: Int): Schematic = apply {
  this.jump = jump
}

/**
 * Includes all slots of the specified
 * includer to this schematic.
 */
infix fun Schematic.includes(includer: Includer): Schematic = apply {
  include += includer
}

/**
 * Includes all slots of the specified
 * includer to this schematic.
 */
infix fun Schematic.includes(includer: List<Int>): Schematic = apply {
  include += includer
}

/**
 * Includes a specified slot to this schematic.
 */
infix fun Schematic.includes(slot: Int): Schematic = apply {
  include += slot
}

/**
 * Excludes all slots of the specified
 * excluder to this schematic.
 */
infix fun Schematic.excludes(excluder: Excluder): Schematic = apply {
  exclude += excluder
}

/**
 * Excludes all slots of the specified
 * excluder to this schematic.
 */
infix fun Schematic.excludes(excluder: List<Int>): Schematic = apply {
  exclude += excluder
}

/**
 * Excludes a specified slot to this schematic.
 */
infix fun Schematic.excludes(slot: Int): Schematic = apply {
  exclude += slot
}

/**
 * Converts this [IntProgression] to a [Schematic]
 */
fun IntProgression.toSchematic(): Schematic = first schematic last jump step
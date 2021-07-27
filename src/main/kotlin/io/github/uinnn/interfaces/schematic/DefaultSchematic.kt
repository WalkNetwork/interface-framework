package io.github.uinnn.interfaces.schematic

import kotlin.math.max
import kotlin.math.min

abstract class AbstractSchematic(
  start: Int,
  last: Int,
  jump: Int = 1,
  override var exclude: Excluder = DEFAULT_EXCLUDER,
  override var include: Includer = DEFAULT_INCLUDER
) : Schematic {
  fun setsInRange(value: Int) = max(MINIMUM_SLOT, min(value, MAXIMUM_SLOT))

  override var start: Int = start
    set(value) {
      field = setsInRange(value)
    }

  override var last: Int = last
    set(value) {
      field = setsInRange(value)
    }

  override var jump: Int = jump
    set(value) {
      field = setsInRange(value)
    }
}

/**
 * The default schematic when mapping
 * a paginated graphical interface.
 */
class DefaultSchematic(start: Int = 10, last: Int = 35) : AbstractSchematic(start, last)
package io.github.uinnn.interfaces.schematic

/**
 * A empty schematic implementation when mapping
 * a scrollable graphical interface.
 */
class EmptySchematic(
  override var start: Int = 0,
  override var last: Int = 0,
  override var exclude: Excluder = emptyExcluder(),
  override var include: Includer = emptyIncluder()
) : Schematic
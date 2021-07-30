package io.github.uinnn.interfaces.schematic

/**
 * The default schematic implementation when mapping
 * a paginated graphical interface.
 */
class DefaultSchematic(
  override var start: Int = 10,
  override var last: Int = 34,
  override var exclude: Excluder = DEFAULT_EXCLUDER,
  override var include: Includer = DEFAULT_INCLUDER
) : Schematic
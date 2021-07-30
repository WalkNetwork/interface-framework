package io.github.uinnn.interfaces.mapper

import io.github.uinnn.interfaces.ScrollableEngines
import io.github.uinnn.interfaces.ScrollableGraphicalInterface
import io.github.uinnn.interfaces.Scrollers
import io.github.uinnn.interfaces.installPerScroll

/**
 * A singular mapper is a [Mapper] that will
 * map all scrollable engines one by one.
 */
object SingularMapper : Mapper {
  override fun map(graphical: ScrollableGraphicalInterface, engines: ScrollableEngines): Scrollers {
    return engines.windowed(graphical.installPerScroll, 1, true)
  }
}
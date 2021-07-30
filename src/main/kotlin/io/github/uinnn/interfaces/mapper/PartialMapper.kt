package io.github.uinnn.interfaces.mapper

import io.github.uinnn.interfaces.ScrollableEngines
import io.github.uinnn.interfaces.ScrollableGraphicalInterface
import io.github.uinnn.interfaces.Scrollers
import io.github.uinnn.interfaces.installPerScroll

/**
 * A partial mapper is a [Mapper] that will
 * map all scrollable engines in partial pages, this is
 * when scrolling up or down, the mapper will map only
 * [ScrollableGraphicalInterface.installPerScroll] engines.
 * This is the default mapper.
 */
object PartialMapper : Mapper {
  override fun map(graphical: ScrollableGraphicalInterface, engines: ScrollableEngines): Scrollers {
    return engines.chunked(graphical.installPerScroll)
  }
}
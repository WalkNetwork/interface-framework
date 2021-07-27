package io.github.uinnn.interfaces

import io.github.uinnn.interfaces.schematic.Schematic
import kotlin.math.max

interface PaginatedGraphicalInterface : GraphicalUserInterface {
  var previousPageEngine: Engine
  var nextPageEngine: Engine
  var schematic: Schematic
  var page: Int

  fun draw(drawPage: Int = 1) {
    pages = selectAllNonPersistents().chunked(amountPerPage)
    val pages = pages

    page = if (drawPage < 1) 1 else if (drawPage > pages.size) max(1, pages.size) else drawPage
    val page = if (pages.isNotEmpty()) pages[page - 1] else listOf()
    if (!hasDrawn) uninstallAllNonPersistents()

    var currentSlot = startSlot

    for (engine in page) {
      if (currentSlot == lastSlot) break
      if (schematic.include.contains(currentSlot)) {
        install(currentSlot, engine)
        continue
      }
      if (schematic.exclude.contains(currentSlot)) currentSlot += schematic.jump
      currentSlot += schematic.jump
      install(currentSlot, engine)
    }

    install(previousPageEngine)
    install(nextPageEngine)

    draws++
  }
}

/**
 * Returns all pages item of this
 * paginated graphical interface.
 */
inline var PaginatedGraphicalInterface.pages: List<List<Engine>>
  get() = locate("Pages") ?: listOf()
  set(value) {
    interject("Pages", value)
  }

/**
 * Returns if this paginated graphical interface
 * is in the first page.
 */
inline val PaginatedGraphicalInterface.isFirstPage get() = page <= 1

/**
 * Returns if this paginated graphical interface
 * is in the last page.
 */
inline val PaginatedGraphicalInterface.isLastPage get() = page >= pages.size

/**
 * Gets the amount of this paginated
 * graphical interface draws.
 */
inline var PaginatedGraphicalInterface.draws: Int
  get() = locate("Draws") ?: 0
  set(value) {
    interject("Draws", max(1, value))
  }

/**
 * Returns if this paginated graphical interface
 * never drewed.
 */
inline val PaginatedGraphicalInterface.hasDrawn get() = draws < 1

/**
 * Returns the amount of engines that a paginated
 * graphical interface can install per page.
 */
inline val PaginatedGraphicalInterface.amountPerPage
  get() = (schematic.last - schematic.start - schematic.exclude.size) + schematic.include.size

/**
 * Returns the last slot possible according with the
 * schematic of this paginated graphical interface.
 */
inline val PaginatedGraphicalInterface.lastSlot get() = schematic.last

/**
 * Returns the start slot possible according with the
 * schematic of this paginated graphical interface.
 */
inline val PaginatedGraphicalInterface.startSlot get() = schematic.start
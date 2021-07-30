package io.github.uinnn.interfaces.interfaces

import io.github.uinnn.interfaces.Engine
import io.github.uinnn.interfaces.ScrollableGraphicalInterface
import io.github.uinnn.interfaces.common.Action

typealias ScrollAction = Action<ScrollableGraphicalInterface>
typealias ScrollSet = HashSet<ScrollAction>

/**
 * Represents a scrollable object. This is, the object
 * can scroll and trigger scroll listeners with [onScroll].
 * Note that this is only can be used with [ScrollableGraphicalInterface],
 * but [Engine] also implements this interface, because the engine needs
 * to register and trigger all handlers when a [ScrollableGraphicalInterface] scrolls.
 */
interface Scrollable {

  /**
   * All registered scroll handlers
   * of this scrollable object.
   */
  var scrollers: ScrollSet

  /**
   * Triggers all registered handlers
   * of this scrollable object.
   */
  fun scroll()

  /**
   * Register a handler to be triggered when
   * a [ScrollableGraphicalInterface] scrolls.
   */
  fun onScroll(action: ScrollAction) = scrollers.add(action)
}
package walkmc.graphical.interfaces

import walkmc.graphical.*
import walkmc.graphical.common.*

typealias ScrollAction = Action<IScrollGraphical>
typealias ScrollSet = MutableSet<ScrollAction>

/**
 * Represents a scrollable object. This is, the object
 * can scroll and trigger scroll listeners with [onScroll].
 * Note that this is only can be used with [IScrollGraphical],
 * but [Engine] also implements this interface, because the engine needs
 * to register and trigger all handlers when a [IScrollGraphical] scrolls.
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
	 * a [IScrollGraphical] scrolls.
	 */
	fun onScroll(action: ScrollAction) = scrollers.add(action)
}

package walkmc.graphical.interfaces

import walkmc.graphical.*
import walkmc.graphical.common.*

typealias RenderAction = Action<IGraphical>
typealias RenderSet = MutableSet<RenderAction>

/**
 * Represents a renderable object for graphical interfaces or engines.
 * This is, the object will be able to renderize.
 */
interface Renderable {
	
	/**
	 * All registered renders of this renderable object.
	 */
	var renders: RenderSet
	
	/**
	 * Render this renderable
	 * object to the owner of the
	 * graphical interface.
	 */
	fun render()
	
	/**
	 * Register a handler to be
	 * triggered when render.
	 */
	fun onRender(action: RenderAction) = renders.add(action)
}

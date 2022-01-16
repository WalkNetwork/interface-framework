package walkmc.graphical.interfaces

import walkmc.graphical.*
import walkmc.graphical.common.*

typealias WorkerAction = SuspendableAction<IGraphical>
typealias WorkSet = MutableSet<WorkerAction>

/**
 * Represents a workable object for graphical user interfaces or engines.
 * This is, the object will be able to work.
 */
@Deprecated("Workers are now replaced by Tickable.")
interface Workable {
	
	/**
	 * All registered works of this workable object.
	 */
	@Deprecated("Workers are now replaced by Tickable.")
	var works: WorkSet
	
	/**
	 * Starts the work of this
	 * workable object.
	 */
	@Deprecated("Workers are now replaced by Tickable.")
	suspend fun work()
	
	/**
	 * Register a handler to be
	 * triggered when works. (update)
	 */
	@Deprecated("Workers are now replaced by Tickable.")
	fun onWork(action: WorkerAction) = works.add(action)
}

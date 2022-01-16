package walkmc.graphical.interfaces

import walkmc.graphical.*
import walkmc.graphical.common.*

typealias TickAction = Action<IGraphical>
typealias TickSet = MutableSet<TickAction>

/**
 * Represents an object that can tick.
 */
interface Tickable {
	
   /**
    * All registered ticks of this tickable object.
    */
   var tickers: TickSet
	
	/**
	 * The amount of ticks that's this tickable object has maden.
	 */
	var ticks: Int
   
   /**
    * The delay in this for ticking again.
    */
   var tickDelay: Int
   
   /**
    * If this tickable object is allow to tick.
    */
   var allowTick: Boolean
   
   /**
    * Starts the work of this tickable object.
    */
   fun tick()
   
   /**
    * Register a handler to be
    * triggered when ticks (update)
    */
   fun onTick(action: TickAction) = tickers.add(action)
}

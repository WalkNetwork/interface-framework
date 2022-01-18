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
    * If this tickable object is allow ticking.
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

/**
 * Adds a new tick listener that's will be executed every [amount] of ticks.
 */
inline fun Tickable.onTick(amount: Int, crossinline action: TickAction) =
   onTick { if (ticks % amount == 0) action() }

/**
 * Adds a new tick listener that's will be executed every tick in the specified [range].
 */
inline fun Tickable.onTick(range: IntRange, crossinline action: TickAction) =
   onTick { if (ticks in range) action() }

/**
 * Adds a new tick listener that's will be executed every tick in the specified range.
 */
inline fun Tickable.onTick(startRange: Int, endRange: Int, crossinline action: TickAction) =
   onTick(startRange..endRange, action)

/**
 * Adds a new tick listener that's will be executed in the first tick.
 */
inline fun Tickable.onFirstTick(crossinline action: TickAction) =
   onTick { if (ticks == 0) action() }

/**
 * Adds a new tick listener that's will be executed in the first [amount] of ticks.
 */
inline fun Tickable.onFirstTick(amount: Int, crossinline action: TickAction) =
   onTick { if (ticks == amount) action() }

/**
 * Adds a new tick listener that's will be executed every second, or 20 ticks.
 */
inline fun Tickable.onEverySecond(crossinline action: TickAction) =
   onTick { if (ticks % 20 == 0) action() }

/**
 * Adds a new tick listener that's will be executed [amount] of seconds.
 */
inline fun Tickable.onEverySecond(amount: Int, crossinline action: TickAction) =
   onTick { if (ticks % (amount * 20) == 0) action() }

/**
 * Adds a new tick listener that's will be executed in the first second.
 */
inline fun Tickable.onFirstSecond(crossinline action: TickAction) =
   onTick { if (ticks == 20) action() }

/**
 * Adds a new tick listener that's will be executed in the first [amount] of seconds.
 */
inline fun Tickable.onFirstSecond(amount: Int, crossinline action: TickAction) =
   onTick { if (ticks == amount * 20) action() }

/**
 * Adds a new tick listener that's will be executed every minute, or 60 ticks, or 1200 ticks.
 */
inline fun Tickable.onEveryMinute(crossinline action: TickAction) =
   onTick { if (ticks % 1200 == 0) action() }

/**
 * Adds a new tick listener that's will be executed [amount] of minutes.
 */
inline fun Tickable.onEveryMinute(amount: Int, crossinline action: TickAction) =
   onTick { if (ticks % (amount * 1200) == 0) action() }

/**
 * Adds a new tick listener that's will be executed in the first minute.
 */
inline fun Tickable.onFirstMinute(crossinline action: TickAction) =
   onTick { if (ticks == 1200) action() }

/**
 * Adds a new tick listener that's will be executed in the first [amount] of minutes.
 */
inline fun Tickable.onFirstMinute(amount: Int, crossinline action: TickAction) =
   onTick { if (ticks == amount * 1200) action() }

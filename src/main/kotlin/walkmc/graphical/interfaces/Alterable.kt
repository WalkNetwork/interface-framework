package walkmc.graphical.interfaces

import org.bukkit.inventory.*
import walkmc.graphical.*
import walkmc.graphical.common.*

typealias AlterAction = Action<Engine>

/**
 * Represents a alterable object for engines.
 * This is, the object will be able alter your state in a new one.
 */
interface Alterable {
	
	/**
	 * Alter the current engine with
	 * a updated new engine.
	 */
	fun alter(engine: Engine): Engine
	
	/**
	 * Alter the current engine with a updated new engine.
	 */
	fun alter(item: ItemStack): ItemStack
	
	/**
	 * Alter the current engine with
	 * a updated new engine with a action.
	 */
	fun alter(engine: Engine, action: AlterAction) = alter(engine.apply(action))
	
	/**
	 * Alter the current engine with
	 * a updated new engine with a action.
	 */
	fun alter(item: ItemStack, action: ItemStack.() -> Unit) = alter(item.apply(action))
}

/**
 * Alter the current engine with
 * changes made in the current engine,
 * this is, the engine will not be replaced
 * by another.
 */
fun Engine.alter(action: AlterAction): Engine = alter(this, action)

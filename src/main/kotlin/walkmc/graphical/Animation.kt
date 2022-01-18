package walkmc.graphical

import walkmc.graphical.interfaces.*

/**
 * AN animation interface to add some animations to graphicals.
 */
interface GraphicalAnimation : Function<IGraphical> {
	
	/**
	 * The animate function that this animation object will perform.
	 */
	fun animate(graphical: IGraphical)
}

/**
 * AN animation interface to add some animations to engines.
 */
interface EngineAnimation : Function<Engine> {
	
	/**
	 * The animate function that this animation object will perform.
	 */
	fun animate(graphical: IGraphical, engine: Engine)
}

/**
 * Adds an animation to this graphical.
 */
fun IGraphical.addAnimation(animation: GraphicalAnimation) {
	onTick {
		animation.animate(this)
	}
}

/**
 * Adds an animation to this engine.
 */
fun Engine.addAnimation(animation: EngineAnimation) {
	onTick {
		animation.animate(this, this@addAnimation)
	}
}

/**
 * Adds an animation to this graphical.
 */
fun IGraphical.addAnimation(tick: Int, animation: GraphicalAnimation) {
	onTick(tick) {
		animation.animate(this)
	}
}

/**
 * Adds an animation to this engine.
 */
fun Engine.addAnimation(tick: Int, animation: EngineAnimation) {
	onTick(tick) {
		animation.animate(this, this@addAnimation)
	}
}

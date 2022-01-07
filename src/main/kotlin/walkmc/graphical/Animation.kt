package walkmc.graphical

/**
 * AN animation interface to add some animations to graphicals.
 */
interface GraphicalAnimation : Function<IGraphical> {
	
	/**
	 * The animate function that this animation object will perform.
	 */
	suspend fun animate(graphical: IGraphical)
}

/**
 * AN animation interface to add some animations to engines.
 */
interface EngineAnimation : Function<Engine> {
	
	/**
	 * The animate function that this animation object will perform.
	 */
	suspend fun animate(graphical: IGraphical, engine: Engine)
}

/**
 * Adds an animation to this graphical.
 */
fun IGraphical.addAnimation(animation: GraphicalAnimation) {
	onWork {
		animation.animate(this)
	}
}

/**
 * Adds an animation to this engine.
 */
fun Engine.addAnimation(animation: EngineAnimation) {
	onWork {
		animation.animate(this, this@addAnimation)
	}
}

package io.github.uinnn.interfaces.common

import io.github.uinnn.interfaces.Engine

typealias AlterAction = Engine.() -> Unit

interface Alterable {
  /**
   * Alter the current engine with
   * a updated new engine.
   */
  fun alter(engine: Engine): Engine
  
  /**
   * Alter the current engine with
   * a updated new engine with a action.
   */
  fun alter(engine: Engine, action: AlterAction): Engine {
    return alter(engine.apply(action))
  }
}

/**
 * Alter the current engine with
 * changes made in the current engine,
 * this is, the engine will not be replaced
 * by another.
 */
fun Engine.alter(action: AlterAction): Engine = alter(this, action)

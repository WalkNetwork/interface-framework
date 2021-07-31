package io.github.uinnn.interfaces.common

import io.github.uinnn.interfaces.Engine
import io.github.uinnn.interfaces.EngineBuilder
import io.github.uinnn.interfaces.Engines
import org.bukkit.inventory.ItemStack

/**
 * Converts this ItemStack to a engine.
 */
fun ItemStack.asEngine(slot: Int = 0): Engine = Engines.from(this, slot)

/**
 * Creates a new engine builder from this item stack.
 */
fun ItemStack.asEngineBuilder(): EngineBuilder = EngineBuilder.from(this)

/**
 * Converts a immutable of this item stack to a engine.
 * Without reflecting the changes to this item stack.
 */
fun ItemStack.asImmutableEngine(slot: Int = 0): Engine = Engines.copy(asEngine(slot))

/**
 * Creates a new engine builder from this item stack.
 * Without reflecting the changes to this item stack.
 */
fun ItemStack.asImmutableEngineBuilder(): EngineBuilder = EngineBuilder.fromImmutable(asEngine())


@file:Suppress("NOTHING_TO_INLINE")

package walkmc.graphical

import org.bukkit.inventory.*
import walkmc.*
import walkmc.extensions.*
import walkmc.graphical.interfaces.*

/**
 * Creates a new engine by the specified type and amount.
 */
inline fun newEngine(type: Materials, amount: Int = 1) = Engine(type, amount)

/**
 * Creates a new engine by the specified type, name and amount.
 */
fun newEngine(type: Materials, name: String, amount: Int = 1) =
	Engine(type, amount).apply {
		name(name)
	}

/**
 * Creates a new engine by the specified type, lore and amount.
 */
fun newEngine(type: Materials, lore: List<String>, amount: Int = 1) =
	Engine(type, amount).apply {
		lore(lore)
	}

/**
 * Creates a new engine by the specified type, name, lore and amount.
 */
fun newEngine(type: Materials, name: String, lore: List<String>, amount: Int = 1) =
	Engine(type, amount).apply {
		name(name)
		lore(lore)
	}

/**
 * Creates a new engine by the specified type and amount.
 */
inline fun newEngine(model: ItemStack, amount: Int = 1) = Engine(model, amount)

/**
 * Creates a new engine by the specified type, name and amount.
 */
fun newEngine(model: ItemStack, name: String, amount: Int = 1) =
	Engine(model, amount).apply {
		name(name)
	}

/**
 * Creates a new engine by the specified type, lore and amount.
 */
fun newEngine(model: ItemStack, lore: List<String>, amount: Int = 1) =
	Engine(model, amount).apply {
		lore(lore)
	}

/**
 * Creates a new engine by the specified type, name, lore and amount.
 */
fun newEngine(model: ItemStack, name: String, lore: List<String>, amount: Int = 1) =
	Engine(model, amount).apply {
		name(name)
		lore(lore)
	}

/**
 * Builds a new engine by the specified type and amount.
 */
inline fun buildEngine(type: Materials, amount: Int = 1, builder: Engine.() -> Unit) =
	Engine(type, amount).apply(builder)

/**
 * Builds a new engine by the specified type, name and amount.
 */
inline fun buildEngine(type: Materials, name: String, amount: Int = 1, builder: Engine.() -> Unit) =
	Engine(type, amount).apply {
		name(name)
		builder()
	}

/**
 * Builds a new engine by the specified type, lore and amount.
 */
inline fun buildEngine(type: Materials, lore: List<String>, amount: Int = 1, builder: Engine.() -> Unit) =
	Engine(type, amount).apply {
		lore(lore)
		builder()
	}

/**
 * Builds a new engine by the specified type, name, lore and amount.
 */
inline fun buildEngine(
	type: Materials,
	name: String,
	lore: List<String>,
	amount: Int = 1,
	builder: Engine.() -> Unit,
) = Engine(type, amount).apply {
	name(name)
	lore(lore)
	builder()
}

/**
 * Creates a new engine by the specified type and amount.
 */
inline fun buildEngine(model: ItemStack, amount: Int = 1, builder: Engine.() -> Unit) =
	Engine(model, amount).apply(builder)

/**
 * Creates a new engine by the specified type, name and amount.
 */
inline fun buildEngine(model: ItemStack, name: String, amount: Int = 1, builder: Engine.() -> Unit) =
	Engine(model, amount).apply {
		name(name)
		builder()
	}

/**
 * Creates a new engine by the specified type, lore and amount.
 */
inline fun buildEngine(model: ItemStack, lore: List<String>, amount: Int = 1, builder: Engine.() -> Unit) =
	Engine(model, amount).apply {
		lore(lore)
		builder()
	}

/**
 * Creates a new engine by the specified type, name, lore and amount.
 */
inline fun buildEngine(
	model: ItemStack,
	name: String,
	lore: List<String>,
	amount: Int = 1,
	builder: Engine.() -> Unit,
) = Engine(model, amount).apply {
	name(name)
	lore(lore)
	builder()
}

/**
 * Builds the newly created empty engine with the specified [block].
 */
inline fun buildEngine(block: Engine.() -> Unit): Engine {
	return Engine(Materials.AIR, 1).apply(block)
}

/**
 * Applies the given renderization [block] that is used to be rendered.
 *
 * @param onRender if the renderization is applied on render
 * @param onWork if the renderization is applied on work
 */
fun Engine.applyRenderization(
	onRender: Boolean = true,
	onWork: Boolean = true,
	onPress: Boolean = false,
	block: Engine.() -> Unit
) {
	if (onRender) {
		onRender {
			alter(block)
		}
	}
	
	if (onWork) {
		onWork {
			alter(block)
		}
	}
	
	if (onPress) {
		onPress {
			alter(block)
		}
	}
}


/**
 * Creates a new graphical by the specified title and size.
 */
inline fun newGraphical(title: String, size: Int): IGraphical = StandardGraphical(title, size)

/**
 * Creates a new scroll graphical by the specified title and size.
 */
inline fun newScrollGraphical(title: String, size: Int): IScrollGraphical = StandardScrollGraphical(title, size)

/**
 * Builds a new graphical by the specified title and size.
 */
inline fun buildGraphical(title: String, size: Int, builder: IGraphical.() -> Unit): IGraphical =
	StandardGraphical(title, size).apply(builder)

/**
 * Builds a new scroll graphical by the specified title and size.
 */
inline fun buildScrollGraphical(title: String, size: Int, builder: IScrollGraphical.() -> Unit): IScrollGraphical =
	StandardScrollGraphical(title, size).apply(builder)

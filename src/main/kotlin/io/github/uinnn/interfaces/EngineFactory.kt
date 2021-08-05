package io.github.uinnn.interfaces

import io.github.uinnn.interfaces.interfaces.PressAction
import io.github.uinnn.interfaces.interfaces.RenderAction
import io.github.uinnn.interfaces.interfaces.ScrollAction
import io.github.uinnn.interfaces.interfaces.WorkerAction
import org.bukkit.Material
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.material.MaterialData

typealias EngineAction = Engine.() -> Unit
typealias ItemMetaAction = ItemMeta.() -> Unit

/**
 * A factory object to building engines to use
 * with graphical user interfaces.
 */
object EngineFactory {

  /**
   * Creates a empty engine with specified slot.
   */
  fun empty(slot: Int = 0): Engine {
    return create(Material.AIR, slot)
  }

  /**
   * Creates a engine with specified material and slot.
   */
  fun create(material: Material, slot: Int = 0): Engine {
    return Engine(material).apply {
      this.slot = slot
    }
  }

  /**
   * Creates a engine with specified material and slot.
   * Also with a [EngineAction] to build the engine.
   */
  inline fun create(material: Material, slot: Int = 0, action: EngineAction): Engine {
    return create(material, slot).apply(action)
  }

  /**
   * Converts a [ItemStack] to a engine and sets
   * specified slot to engine.
   */
  fun from(item: ItemStack, slot: Int = 0): Engine {
    return Engine(item).apply {
      this.slot = slot
    }
  }

  /**
   * Copies a engine. This is, the returned engine
   * will not be reflected when modifying them.
   */
  fun copy(engine: Engine): Engine {
    return engine.clone() as Engine
  }
}

/**
 * A utility class used to build easily new engines.
 * This supports transforming the engine and the meta of the engine.
 */
class EngineBuilder private constructor(private var model: Engine) {

  /**
   * Transforms the engine with a [EngineAction].
   */
  fun transform(action: EngineAction): EngineBuilder {
    model.apply(action)
    return this
  }

  /**
   * Transforms the engine meta with a [ItemMetaAction].
   */
  fun transformMeta(action: ItemMetaAction): EngineBuilder {
    val meta = model.itemMeta
    meta.apply(action)
    model.itemMeta = meta
    return this
  }

  /**
   * Transforms the engine adding a [onPress] event handler.
   */
  fun onPress(action: PressAction): EngineBuilder {
    return transform {
      onPress(action)
    }
  }

  /**
   * Transforms the engine adding a [onRender] event handler.
   */
  fun onRender(action: RenderAction): EngineBuilder {
    return transform {
      onRender(action)
    }
  }

  /**
   * Transforms the engine adding a [onWork] event handler.
   */
  fun onWork(action: WorkerAction): EngineBuilder {
    return transform {
      onWork(action)
    }
  }

  /**
   * Transforms the engine adding a [onScroll] event handler.
   */
  fun onScroll(action: ScrollAction): EngineBuilder {
    return transform {
      onScroll(action)
    }
  }

  /**
   * Transforms the engine display name with specified name.
   */
  fun name(name: String): EngineBuilder {
    return transformMeta {
      displayName = name
    }
  }

  /**
   * Transforms the flags of the engine with the
   * specified vararg of item flags.
   */
  fun flags(vararg flags: ItemFlag): EngineBuilder {
    return transformMeta {
      addItemFlags(*flags)
    }
  }

  /**
   * Transforms the engine lore with the specified lines.
   */
  fun lore(vararg lines: String): EngineBuilder {
    return transformMeta {
      lore = lines.toMutableList()
    }
  }

  /**
   * Transforms the engine lore with the specified lines.
   * This only can be used with string multi-line like:
   * ```kt
   * lore("""
   *  §7First line.
   *  §7Second line.
   * """)
   * ```
   */
  fun lore(lines: String): EngineBuilder {
    return transformMeta {
      lore = lines.lines()
    }
  }

  /**
   * Transform this engine in to a unbreakable
   * or a breakable item.
   */
  fun unbreakable(value: Boolean): EngineBuilder {
    return transformMeta {
      spigot().isUnbreakable = value
    }
  }

  /**
   * Alters the changes made in this engine builder
   * and sets to the graphical interface of the model engine.
   */
  fun alter(): EngineBuilder {
    model.alter(model)
    return this
  }

  /**
   * Build the actual engine.
   * ### Note:
   * This is a mutable build.
   * This only returns the model, not copying them,
   * this is, if you call this function, and changes the engine
   * with this engine builder, will be reflected to a already builded
   * engine. If you want a immutable build, use [buildImmutable].
   * Automatically alters the engine in the graphical interface.
   */
  fun build(): Engine = alter().model

  /**
   * Build the actual engine as copy.
   * ### Note:
   * This is a immutable build.
   * This will returns the model as copy, this is, you not
   * be able to change the builded model after calling this function.
   * If you want a mutable build, use [build].
   */
  fun buildImmutable(): Engine = EngineFactory.copy(model)

  /**
   * Companion object to build new instances
   * of [EngineBuilder].
   */
  companion object {

    /**
     * Creates a empty engine builder.
     */
    fun empty(): EngineBuilder {
      return EngineBuilder(EngineFactory.empty())
    }

    /**
     * Creates a engine builder with specified material.
     */
    fun of(material: Material): EngineBuilder {
      return EngineBuilder(EngineFactory.create(material))
    }

    /**
     * Creates a engine builder with specified data.
     */
    fun of(data: MaterialData): EngineBuilder {
      return EngineBuilder(Engine(data))
    }

    /**
     * Creates a engine builder from a ItemStack.
     */
    fun from(item: ItemStack): EngineBuilder {
      return EngineBuilder(EngineFactory.from(item))
    }

    /**
     * Creates a engine buider from a engine.
     * The returned model of the engined builder will
     * be mutable, this is, any changes in the builder
     * will be reflected in the specified engine.
     */
    fun from(engine: Engine): EngineBuilder {
      return EngineBuilder(engine)
    }

    /**
     * Creates a engine builder from a engine.
     * The returned model of the engine builder will
     * be immutable, this is, a copy of the specified engine.
     */
    fun fromImmutable(engine: Engine): EngineBuilder {
      return EngineBuilder(EngineFactory.copy(engine))
    }
  }
}
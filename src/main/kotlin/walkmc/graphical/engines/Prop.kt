package walkmc.graphical.engines

import walkmc.graphical.*
import kotlin.properties.*
import kotlin.reflect.*
import java.util.*

typealias PropCallback<T> = Engine.(T) -> Unit

/**
 * An interface for safely creating observable properties in engine clauses.
 */
interface IProp<T> : ReadWriteProperty<Engine, T> {
   var value: T
   var callback: PropCallback<T>?
}

/**
 * An abstract implementation of [IProp].
 *
 * This works like a skeletal model for any observable property.
 */
open class Prop<T>(override var value: T) : IProp<T> {
   override var callback: PropCallback<T>? = null
   
   override fun getValue(thisRef: Engine, property: KProperty<*>): T = value
   
   override fun setValue(thisRef: Engine, property: KProperty<*>, value: T) {
      this.value = value
      callback?.invoke(thisRef, value)
      thisRef.notifyChange()
   }
}


/**
 * Creates a new property.
 */
fun <T> Engine.prop(value: T) = Prop(value)

/**
 * Creates a new property.
 */
fun <T> Engine.prop(value: T, callback: PropCallback<T>) =
   Prop(value).also { it.callback = callback }

/**
 * Creates a new property of int.
 */
fun Engine.int(value: Int = 0) = Prop(value)

/**
 * Creates a new property of float.
 */
fun Engine.float(value: Float = 0f) = Prop(value)

/**
 * Creates a new property of double.
 */
fun Engine.double(value: Double = 0.0) = Prop(value)

/**
 * Creates a new property of string.
 */
fun Engine.string(value: String = "") = Prop(value)

/**
 * Creates a new property of boolean.
 */
fun Engine.boolean(value: Boolean = false) = Prop(value)

/**
 * Creates a new property of list.
 */
fun <T> Engine.list(value: MutableList<T> = LinkedList()) = Prop(value)

/**
 * Creates a new property of engine.
 */
fun Engine.engine(value: Engine = EmptyEngine) = Prop(value)

/**
 * Creates a new property of int.
 */
fun Engine.int(value: Int = 0, callback: PropCallback<Int>) =
   Prop(value).also { it.callback = callback }

/**
 * Creates a new property of int.
 */
fun Engine.float(value: Float = 0f, callback: PropCallback<Float>) =
   Prop(value).also { it.callback = callback }

/**
 * Creates a new property of int.
 */
fun Engine.double(value: Double = 0.0, callback: PropCallback<Double>) =
   Prop(value).also { it.callback = callback }

/**
 * Creates a new property of int.
 */
fun Engine.string(value: String = "", callback: PropCallback<String>) =
   Prop(value).also { it.callback = callback }

/**
 * Creates a new property of boolean.
 */
fun Engine.boolean(value: Boolean = false, callback: PropCallback<Boolean>) =
   Prop(value).also { it.callback = callback }

/**
 * Creates a new property of int.
 */
fun <T> Engine.list(value: MutableList<T> = LinkedList(), callback: PropCallback<MutableList<T>>) =
   Prop(value).also { it.callback = callback }

/**
 * Creates a new property of int.
 */
fun Engine.engine(value: Engine = EmptyEngine, callback: PropCallback<Engine>) =
   Prop(value).also { it.callback = callback }

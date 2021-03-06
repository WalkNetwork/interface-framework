package walkmc.graphical.interfaces

import java.util.*

/**
 * Represents an observable object for graphical user interfaces.
 * This is, the object will be able to observe a kind of observer.
 * Also known as events.
 */
interface Observable {
	
	/**
	 * All registered observers of this observable object.
	 */
	var observers: Observers
	
	/**
	 * Verify if this observable permits
	 * a observer kind to be triggered.
	 */
	fun permits(kind: ObserverKind): Boolean {
		return kind in observers
	}
	
	/**
	 * Allows a observer kind to be triggered.
	 */
	fun allow(kind: ObserverKind) {
		observers[kind] = true
	}
	
	/**
	 * Negates a observer kind to be triggered.
	 */
	fun negate(kind: ObserverKind) {
		observers.remove(kind)
	}
}

/**
 * All possible kind of observer can be trigerred.
 */
enum class ObserverKind {
	PRESS,
	PRESS_CANCELLABLE,
	UNCESS,
	ACCESS,
	PICKUP,
	DRAG,
	DAMAGE,
	TELEPORT,
	RECEIVE_MESSAGE
}

/**
 * A storage class to hold all observers.
 */
class Observers : EnumMap<ObserverKind, Boolean>(ObserverKind::class.java)

/**
 * Sets all observers of this observable to defaults.
 */
fun Observable.defaultObservers() = apply {
	allow(ObserverKind.PRESS)
	allow(ObserverKind.PRESS_CANCELLABLE)
	allow(ObserverKind.ACCESS)
	allow(ObserverKind.UNCESS)
	allow(ObserverKind.DAMAGE)
	allow(ObserverKind.RECEIVE_MESSAGE)
	negate(ObserverKind.DRAG)
	negate(ObserverKind.PICKUP)
	negate(ObserverKind.TELEPORT)
}

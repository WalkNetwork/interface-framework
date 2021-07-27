package io.github.uinnn.interfaces.common

import java.util.*

interface Observable {
  var observers: Observers

  /**
   * Verify if this observable permits
   * a observer kind to be triggered.
   */
  fun permits(kind: ObserverKind): Boolean {
    return kind in observers && observers[kind] == true
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
    observers[kind] = false
  }
}

/**
 * All possible kind of observer can be trigerred.
 */
enum class ObserverKind {
  PRESS, PRESS_CANCELLABLE, UNCCESS, ACCESS, PICKUP, DRAG,
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
  allow(ObserverKind.UNCCESS)
  negate(ObserverKind.DRAG)
  negate(ObserverKind.PICKUP)
}
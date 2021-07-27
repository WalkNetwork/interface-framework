package io.github.uinnn.interfaces.common

interface Visible {
  var isVisible: Boolean
  
  fun toggle() {
    isVisible = isVisible.not()
  }
  
  fun turn(value: Boolean) {
    isVisible = value
  }
  
  fun turnOn() = turn(true)
  fun turnOff() = turn(false)
}
package walkmc.graphical.interfaces

/**
 * Represents a visible object for engines.
 * That is, the object can be visible or not.
 */
interface Visible {
	
	/**
	 * Returns the actual visibility of the object.
	 */
	var isVisible: Boolean
	
	/**
	 * Toggles the visibility of the object.
	 */
	fun toggleVisibility() {
		isVisible = isVisible.not()
	}
	
	/**
	 * Turns the visibility of the object by
	 * the specified value.
	 */
	fun turnVisibility(value: Boolean) {
		isVisible = value
	}
	
	/**
	 * Turns the visibility of the object to visible.
	 */
	fun turnVisibilityOn() = turnVisibility(true)
	
	/**
	 * Turns the visibility of the object to invisible.
	 */
	fun turnVisibilityOff() = turnVisibility(false)
}

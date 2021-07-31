package io.github.uinnn.interfaces

typealias InterfaceAction = GraphicalInterface.() -> Unit
typealias ScrollableInterfaceAction = ScrollableGraphicalInterface.() -> Unit

/**
 * A utility class to creating new interfaces.
 */
object Interfaces {

  /**
   * Creates a empty [GraphicalInterface].
   */
  fun empty(): GraphicalInterface {
    return StandardGraphicalInterface("", 1)
  }

  /**
   * Creates a [GraphicalInterface] with
   * title and lines specified.
   */
  fun create(title: String, lines: Int): GraphicalInterface {
    return StandardGraphicalInterface(title, lines)
  }

  /**
   * Creates a [GraphicalInterface] with
   * title and lines specified. Also apply a [InterfaceAction]
   * to build this interface.
   */
  inline fun create(title: String, lines: Int, action: InterfaceAction): GraphicalInterface {
    return StandardGraphicalInterface(title, lines).apply(action)
  }

  /**
   * Creates a workable [GraphicalInterface]
   * with title and lines specified.
   */
  fun workable(title: String, lines: Int): GraphicalInterface {
    return StandardGraphicalInterface(title, lines).apply {
      worker.allow = true
    }
  }

  /**
   * Creates a workable [GraphicalInterface]
   * with title and lines specified. Also apply a [InterfaceAction]
   * to build this interface.
   */
  inline fun workable(title: String, lines: Int, action: InterfaceAction): GraphicalInterface {
    return workable(title, lines).apply(action)
  }

  /**
   * Creates a [GraphicalInterface] with
   * a parent, title and lines specified.
   */
  fun parent(title: String, lines: Int, parent: GraphicalInterface): GraphicalInterface {
    return StandardGraphicalInterface(title, lines).apply {
      this.parent = parent
    }
  }

  /**
   * Creates a [GraphicalInterface] with a parent,
   * title and lines specified. Also apply a [InterfaceAction]
   * to build this interface.
   */
  inline fun parent(
    title: String,
    lines: Int,
    parent: GraphicalInterface,
    action: InterfaceAction
  ): GraphicalInterface {
    return parent(title, lines, parent).apply(action)
  }

  /**
   * Creates a [ScrollableGraphicalInterface]
   * with title and lines specified.
   */
  fun scrollable(title: String, lines: Int): ScrollableGraphicalInterface {
    return StandardScrollableInterface(title, lines)
  }

  /**
   * Creates a [ScrollableGraphicalInterface]
   * with title and lines specified. Also apply a
   * [ScrollableInterfaceAction] to build this interface.
   */
  inline fun scrollable(title: String, lines: Int, action: ScrollableInterfaceAction): ScrollableGraphicalInterface {
    return StandardScrollableInterface(title, lines).apply(action)
  }
}

package io.github.uinnn.interfaces

import org.bukkit.entity.Player

typealias InterfaceAction = GraphicalUserInterface.() -> Unit
typealias PaginatedInterfaceAction = PaginatedGraphicalInterface.() -> Unit

object Interfaces {

  /**
   * Creates a empty graphical user interface.
   */
  fun empty(): GraphicalUserInterface {
    return SimpleGraphicalInterface("", 1)
  }

  /**
   * Creates a graphical user interface with
   * title and lines specified.
   */
  fun create(title: String, lines: Int): GraphicalUserInterface {
    return SimpleGraphicalInterface(title, lines)
  }

  /**
   * Creates a graphical user interface with
   * title and lines specified. Also apply a [InterfaceAction]
   * to build this interface.
   */
  inline fun create(title: String, lines: Int, action: InterfaceAction): GraphicalUserInterface {
    return SimpleGraphicalInterface(title, lines).apply(action)
  }

  /**
   * Creates a workable graphical user interface
   * with title and lines specified.
   */
  fun workable(title: String, lines: Int): GraphicalUserInterface {
    return SimpleGraphicalInterface(title, lines).apply {
      worker.allow = true
    }
  }

  /**
   * Creates a workable graphical user interface
   * with title and lines specified. Also apply a [InterfaceAction]
   * to build this interface.
   */
  inline fun workable(title: String, lines: Int, action: InterfaceAction): GraphicalUserInterface {
    return workable(title, lines).apply(action)
  }

  /**
   * Creates a ownerable graphical user interface
   * with title and lines specified.
   */
  fun ownerable(title: String, lines: Int, owner: Player): GraphicalUserInterface {
    return SimpleGraphicalInterface(title, lines).apply {
      this.owner = owner
    }
  }

  /**
   * Creates a ownerable graphical user interface
   * with title and lines specified. Also apply a [InterfaceAction]
   * to build this interface.
   */
  inline fun ownerable(title: String, lines: Int, owner: Player, action: InterfaceAction): GraphicalUserInterface {
    return ownerable(title, lines, owner).apply(action)
  }

  /**
   * Creates a graphical user interface with
   * a parent, title and lines specified.
   */
  fun parent(title: String, lines: Int, parent: GraphicalUserInterface): GraphicalUserInterface {
    return SimpleGraphicalInterface(title, lines).apply {
      this.parent = parent
    }
  }

  /**
   * Creates a graphical user interface with a parent,
   * title and lines specified. Also apply a [InterfaceAction]
   * to build this interface.
   */
  inline fun parent(
    title: String,
    lines: Int,
    parent: GraphicalUserInterface,
    action: InterfaceAction
  ): GraphicalUserInterface {
    return parent(title, lines, parent).apply(action)
  }

  fun paginated(title: String, lines: Int): PaginatedGraphicalInterface {
    return SimplePaginatedInterface(title, lines)
  }

  inline fun paginated(title: String, lines: Int, action: PaginatedInterfaceAction): PaginatedGraphicalInterface {
    return SimplePaginatedInterface(title, lines).apply(action)
  }
}

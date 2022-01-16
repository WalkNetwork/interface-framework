package walkmc.graphical.engines

import org.bukkit.event.inventory.*
import org.bukkit.inventory.*
import walkmc.*
import walkmc.graphical.*
import walkmc.graphical.common.*

/**
 * An implementation of [Engine] for easily creation of sorter engines.
 *
 * Note that sorter engine is only supported on [SortGraphical].
 */
open class SorterEngine : Engine {
   constructor(type: Materials, amount: Int = 1) : super(type, amount)
   constructor(stack: ItemStack) : super(stack)
   
   val graph get() = graphical as SortGraphical<*>
   
   init {
      isPersistent = true
   }
   
   /**
    * The header lore to be mapped.
    */
   open fun withHeader(): Iterable<String> =
      listOf("§7Ordene a seleção", "§7dos elementos.", "")
   
   /**
    * The footer lore to be mapped.
    */
   open fun withFooter(): Iterable<String> =
      listOf("", if (graph.isSorterDisabled) "§cOrdem desativada." else "§aClique para ordenar.")
   
   /**
    * The current sorter lore to be mapped.
    */
   open fun withCurrentSorter(text: String, index: Int): Iterable<String> =
      listOf(" §b➟ $text")
   
   /**
    * The background sorter lore to be mapped.
    */
   open fun withBackgroundSorter(text: String, index: Int): Iterable<String> =
      listOf("   §8$text")
   
   /**
    * The sorter shown text lore to be mapped.
    */
   open fun withSorter(): Iterable<String> {
      val current = graph.options.index
      
      return buildList {
         graph.texts.forEachIndexed { index, entry ->
            this += if (current == index) withCurrentSorter(entry, index) else withBackgroundSorter(entry, index)
         }
      }
   }
   
   override fun notifyChange() {
      alter(newWithLore(withHeader() + withSorter() + withFooter()))
   }
   
   override fun handleClick(event: InventoryClickEvent) {
      if (!graph.isSorterDisabled) {
         if (event.isLeftClick) graph.toNextSorter() else graph.toPreviousSorter()
         notifyChange()
      }
   }
   
   override fun handleRender() {
      notifyChange()
   }
}

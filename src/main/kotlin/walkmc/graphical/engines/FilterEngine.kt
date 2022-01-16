package walkmc.graphical.engines

import org.bukkit.event.inventory.*
import org.bukkit.inventory.*
import walkmc.*
import walkmc.graphical.*
import walkmc.graphical.common.*

/**
 * An implementation of [Engine] for easily creation of filter engines.
 *
 * Note that filter engine is only supported on [FilterGraphical].
 */
open class FilterEngine : Engine {
   constructor(type: Materials, amount: Int = 1) : super(type, amount)
   constructor(stack: ItemStack) : super(stack)
   
   val graph get() = graphical as FilterGraphical<*>
   
   init {
      isPersistent = true
   }
   
   /**
    * The header lore to be mapped.
    */
   open fun withHeader(): Iterable<String> =
      listOf("§7Filtre a seleção", "§7dos elementos.", "")
   
   /**
    * The footer lore to be mapped.
    */
   open fun withFooter(): Iterable<String> =
      listOf("", if (graph.isFilterDisabled) "§cFiltro desativado." else "§aClique para filtrar.")
   
   /**
    * The current filter lore to be mapped.
    */
   open fun withCurrentFilter(text: String, index: Int): Iterable<String> =
      listOf(" §b➟ $text")
   
   /**
    * The background filter lore to be mapped.
    */
   open fun withBackgroundFilter(text: String, index: Int): Iterable<String> =
      listOf("   §8$text")
   
   /**
    * The filter shown text lore to be mapped.
    */
   open fun withFilter(): Iterable<String> {
      val current = graph.options.index
      
      return buildList {
         graph.texts.forEachIndexed { index, entry ->
            this += if (current == index) withCurrentFilter(entry, index) else withBackgroundFilter(entry, index)
         }
      }
   }
   
   override fun notifyChange() {
      alter(newWithLore(withHeader() + withFilter() + withFooter()))
   }
   
   override fun handleClick(event: InventoryClickEvent) {
      if (!graph.isFilterDisabled) {
         if (event.isLeftClick) graph.toNextFilter() else graph.toPreviousFilter()
         notifyChange()
      }
   }
   
   override fun handleRender() {
      notifyChange()
   }
}

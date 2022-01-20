package walkmc.graphical.engines

import org.bukkit.event.inventory.*
import org.bukkit.inventory.*
import walkmc.*
import walkmc.graphical.*
import walkmc.graphical.common.*
import walkmc.graphical.interfaces.*

/**
 * An implementation of [Engine] for easily creation of filter engines.
 *
 * Note that filter engine is only supported on [FilterGraphical].
 */
open class FilterEngine : Engine {
   constructor(type: Materials, amount: Int = 1) : super(type, amount)
   constructor(stack: ItemStack) : super(stack)
   
   val filter get() = graphical as Filterable<*>
   
   protected open var header: LoreRequestor<FilterEngine> = {
      setOf("§7Filtre a seleção", "§7dos elementos.", "")
   }
   
   protected var footer: LoreRequestor<FilterEngine> = {
      setOf("", if (filter.isFilterDisabled) "§cFiltro desativado." else "§aClique para filtrar.")
   }
   
   protected var current: FilterEngine.(String, Int) -> Iterable<String> = { text, _ ->
      setOf(" §b➟ $text")
   }
   
   protected var background: FilterEngine.(String, Int) -> Iterable<String> = { text, _ ->
      setOf("   §8$text")
   }
   
   init {
      isPersistent = true
   }
   
   fun requestHeader(requestor: LoreRequestor<FilterEngine>) {
      header = requestor
   }
   
   fun requestFooter(requestor: LoreRequestor<FilterEngine>) {
      footer = requestor
   }
   
   fun requestHeader(requestor: Iterable<String>) {
      header = { requestor }
   }
   
   fun requestFooter(requestor: Iterable<String>) {
      footer = { requestor }
   }
   
   fun requestHeader(vararg requestor: String) {
      header = { requestor.toSet() }
   }
   
   fun requestFooter(vararg requestor: String) {
      footer = { requestor.toSet() }
   }
   
   fun requestCurrent(requestor: FilterEngine.(String, Int) -> Iterable<String>) {
      current = requestor
   }
   
   fun requestBackground(requestor: FilterEngine.(String, Int) -> Iterable<String>) {
      background = requestor
   }
   
   /**
    * The header lore to be mapped.
    */
   open fun withHeader() = header()
   
   /**
    * The footer lore to be mapped.
    */
   open fun withFooter() = footer()
   
   /**
    * The current filter lore to be mapped.
    */
   open fun withCurrentFilter(text: String, index: Int) = current(this, text, index)
   
   /**
    * The background filter lore to be mapped.
    */
   open fun withBackgroundFilter(text: String, index: Int) = background(this, text, index)
   
   /**
    * The filter shown text lore to be mapped.
    */
   open fun withFilter(): Iterable<String> {
      val current = filter.filterOptions.index
      
      return buildList {
         filter.filterTexts.forEachIndexed { index, entry ->
            this += if (current == index) withCurrentFilter(entry, index) else withBackgroundFilter(entry, index)
         }
      }
   }
   
   override fun notifyChange() {
      alter(copyWithLore(withHeader() + withFilter() + withFooter()))
   }
   
   override fun handleClick(event: InventoryClickEvent) {
      if (!filter.isFilterDisabled) {
         if (event.isLeftClick) filter.toNextFilter() else filter.toPreviousFilter()
         notifyChange()
      }
   }
   
   override fun handleRender() {
      notifyChange()
   }
}

package walkmc.graphical.engines

import org.bukkit.event.inventory.*
import org.bukkit.inventory.*
import walkmc.*
import walkmc.graphical.*
import walkmc.graphical.common.*
import walkmc.graphical.interfaces.*

typealias LoreRequestor<T> = T.() -> Iterable<String>

/**
 * An implementation of [Engine] for easily creation of sorter engines.
 *
 * Note that sorter engine is only supported on [SortGraphical].
 */
open class SorterEngine : Engine {
   constructor(type: Materials, amount: Int = 1) : super(type, amount)
   constructor(stack: ItemStack) : super(stack)
   
   val sort get() = graphical as Sortable<*>
   
   protected open var header: LoreRequestor<SorterEngine> = {
      setOf("§7Ordene a seleção", "§7dos elementos.", "")
   }
   
   protected var footer: LoreRequestor<SorterEngine> = {
      setOf("", if (sort.isSorterDisabled) "§cOrdem desativada." else "§aClique para ordenar.")
   }
   
   protected var current: SorterEngine.(String, Int) -> Iterable<String> = { text, _ ->
      setOf(" §b➟ $text")
   }
   
   protected var background: SorterEngine.(String, Int) -> Iterable<String> = { text, _ ->
      setOf("   §8$text")
   }
   
   init {
      isPersistent = true
   }
   
   fun requestHeader(requestor: LoreRequestor<SorterEngine>) {
      header = requestor
   }
   
   fun requestFooter(requestor: LoreRequestor<SorterEngine>) {
      footer = requestor
   }
   
   fun requestCurrent(requestor: SorterEngine.(String, Int) -> Iterable<String>) {
      current = requestor
   }
   
   fun requestBackground(requestor: SorterEngine.(String, Int) -> Iterable<String>) {
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
    * The current sorter lore to be mapped.
    */
   open fun withCurrentSorter(text: String, index: Int) = current(this, text, index)
   
   /**
    * The background sorter lore to be mapped.
    */
   open fun withBackgroundSorter(text: String, index: Int) = background(this, text, index)
   
   /**
    * The sorter shown text lore to be mapped.
    */
   open fun withSorter(): Iterable<String> {
      val current = sort.sorterOptions.index
      
      return buildList {
         sort.sorterTexts.forEachIndexed { index, entry ->
            this += if (current == index) withCurrentSorter(entry, index) else withBackgroundSorter(entry, index)
         }
      }
   }
   
   override fun notifyChange() {
      alter(copyWithLore(withHeader() + withSorter() + withFooter()))
   }
   
   override fun handleClick(event: InventoryClickEvent) {
      if (!sort.isSorterDisabled) {
         if (event.isLeftClick) sort.toNextSorter() else sort.toPreviousSorter()
         notifyChange()
      }
   }
   
   override fun handleRender() {
      notifyChange()
   }
}

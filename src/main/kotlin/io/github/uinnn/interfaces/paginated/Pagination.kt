package com.side.lib.graphical.pagination

/*
import com.side.lib.common.*
import com.side.lib.extension.*
import io.github.uinnn.interfaces.Engines
import com.side.lib.serializer.serial.PaginationSerializer
import kotlinx.serialization.Serializable
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.material.MaterialData

val nextPageHead by lazy {
  head(
    "http://textures.minecraft.net/texture/22d145c93e5eac48a661c6f27fdaff5922cf433dd627bf23eec378b9956197",
    "§aAvançar"
  )
}

val previousPageHead by lazy {
  head(
    "http://textures.minecraft.net/texture/5fde3bfce2d8cb724de8556e5ec21b7f15f584684ab785214add164be7624b",
    "§cVoltar"
  )
}

@Serializable(PaginationSerializer::class)
open class Pagination(var title: String, var rows: Int) : ArrayList<Page>() {
  val data = HashMap<String, Any>()
  var items = ArrayList<Engines>()
  var current = 0
  var slots = 2..8 step 1
  var lines = 2..4 step 1
  var maxOpeners = -1
  
  val filteredItems get() = findData<ArrayList<Engines>>("FILTERED")
  val lastAction get() = findData<Action<Engines>>("LAST_ACTION")
  val background get() = findData<MaterialData>("BACKGROUND")
  val hasPrevious get() = current > 0
  val hasNext get() = current < lastIndex
  
  fun putData(key: String, value: Any) = data.put(key, value)
  fun removeData(key: String) = data.remove(key)
  fun <T : Any> findData(key: String): T? = data[key] as? T
  fun <T : Any> getData(key: String): T = data[key]!! as T
  
  inline fun newPage(action: Action<Page>) = page(this, action).apply {
    maxOpeners = this@Pagination.maxOpeners
    if (background != null) fill(background!!)
  }
  
  fun newPage() = page(this) {
    maxOpeners = this@Pagination.maxOpeners
    if (background != null) fill(background!!)
  }
  
  inline fun pages(action: Action<Page>) = onEach(action)
  
  fun paginate(item: ArrayList<Engines> = items, action: Action<Engines>) {
    if (size > 0) clear()
    putData("LAST_ACTION", action)
    var page = newPage()
    val maxSlot = slotAt(lines.last, slots.last)
    var slot = slotAt(lines.first, slots.first)
    var currentLine = lines.first
    var currentSlot = slots.first - slots.step
    for (it in item) {
      if (slot == maxSlot) {
        currentLine = lines.first
        currentSlot = slots.first - slots.step
        add(page)
        page = newPage()
      }
      if (currentSlot == slots.last) {
        currentLine += lines.step
        currentSlot = slots.first - slots.step
      }
      currentSlot += slots.step
      slot = slotAt(currentLine, currentSlot)
      page.button(slot, it, action)
    }
    add(page)
  }
  
  fun paginate(item: ArrayList<Engines> = items) {
    val last = lastAction
    if (last != null) paginate(item, last)
    else paginate(item) {}
  }
  
  fun paginateFromFilter(act: Action<Engines>? = null) {
    val filtered = filteredItems ?: return
    val action = act ?: lastAction ?: return
    clear()
    paginate(filtered, action)
  }
  
  inline fun filterAndPaginate(filter: Predicate<Engines>, noinline action: Action<Engines>? = null) {
    filter(filter)
    paginateFromFilter(action)
  }
  
  inline fun filter(filter: Predicate<Engines>) = putData("FILTERED", items.filter(filter))
  fun filterByMaterial(material: Material) = filter { it.type == material }
  fun filterByName(name: String) = filter { it.name.contains(name, true) }
  
  fun open(player: Player, index: Int) {
    if (index in size..-1) return
    //get(current).close(player, false)
    current = index
    get(index).open(player)
  }
  
  fun openFirst(player: Player) = open(player, 0)
  fun openCurrent(player: Player) = open(player, current)
  fun openLast(player: Player) = open(player, lastIndex)
  
  fun openPrevious(player: Player) {
    if (!hasPrevious) return
    current--
    openCurrent(player)
  }
  
  fun openNext(player: Player) {
    if (!hasNext) return
    current++
    openCurrent(player)
  }
  
  var previousPage = io.github.uinnn.interfaces.Engines(previousPageHead).apply {
    slot = startSlot(rows)
  }
  
  var nextPage = io.github.uinnn.interfaces.Engines(nextPageHead).apply {
    slot = endSlot(rows)
  }
  
  /**
   * Companion object.
   */
  companion object : Placeholder<Pagination> {
    override fun inject(string: String, value: Pagination) = string
      .inject("{page}", value.current + 1)
      .inject("{previous}", if (value.hasPrevious) value.current else "")
      .inject("{next}", if (value.hasNext) value.current + 2 else "")
  }
}

 */
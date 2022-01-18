package walkmc.graphical.dsl

import org.bukkit.inventory.*
import walkmc.*
import walkmc.graphical.*
import walkmc.graphical.engines.*

//
// Delegate engines
//

/**
 * Install an engine in the specified [slot] applying [block].
 */
inline fun <T : Engine> IGraphical.makeEngine(slot: Int, engine: T, block: T.() -> Unit): T {
   install(slot, engine.apply(block))
   return engine
}

/**
 * Install an engine in the specified [slot].
 */
fun <T : Engine> IGraphical.makeEngine(slot: Int, engine: T): T {
   install(slot, engine)
   return engine
}

//
// Default Engines
//

/**
 * Installs a simple engine in the specified [slot] applying [block].
 */
inline fun IGraphical.engine(slot: Int, material: Materials, block: Engine.() -> Unit) =
   makeEngine(slot, Engine(material), block)

/**
 * Installs a simple engine in the specified [slot] applying [block].
 */
inline fun IGraphical.engine(slot: Int, model: ItemStack, block: Engine.() -> Unit) =
   makeEngine(slot, Engine(model), block)

/**
 * Installs a simple engine in the specified [slot].
 */
fun IGraphical.engine(slot: Int, material: Materials) = makeEngine(slot, Engine(material))

/**
 * Installs a simple engine in the specified [slot].
 */
fun IGraphical.engine(slot: Int, model: ItemStack) = makeEngine(slot, Engine(model))

//
// Cycles Engines
//

/**
 * Installs a cycle engine in the specified [slot] applying [block].
 */
inline fun IGraphical.cycleEngine(slot: Int, material: Materials, block: CycleEngine.() -> Unit) =
   makeEngine(slot, CycleEngine(material), block)

/**
 * Installs a cycle engine in the specified [slot] applying [block].
 */
inline fun IGraphical.cycleEngine(slot: Int, model: ItemStack, block: CycleEngine.() -> Unit) =
   makeEngine(slot, CycleEngine(model), block)

/**
 * Installs a cycle engine in the specified [slot].
 */
fun IGraphical.cycleEngine(slot: Int, material: Materials) =
   makeEngine(slot, CycleEngine(material))

/**
 * Installs a cycle engine in the specified [slot].
 */
fun IGraphical.cycleEngine(slot: Int, model: ItemStack) =
   makeEngine(slot, CycleEngine(model))

//
// Cycles Material Engines
//

/**
 * Installs a cycle material engine in the specified [slot] applying [block].
 */
inline fun IGraphical.cycleMaterialEngine(slot: Int, material: Materials, block: CycleMaterialEngine.() -> Unit) =
   makeEngine(slot, CycleMaterialEngine(material), block)

/**
 * Installs a cycle material engine in the specified [slot] applying [block].
 */
inline fun IGraphical.cycleMaterialEngine(slot: Int, model: ItemStack, block: CycleMaterialEngine.() -> Unit) =
   makeEngine(slot, CycleMaterialEngine(model), block)

/**
 * Installs a cycle material engine in the specified [slot].
 */
fun IGraphical.cycleMaterialEngine(slot: Int, material: Materials) =
   makeEngine(slot, CycleMaterialEngine(material))

/**
 * Installs a cycle material engine in the specified [slot].
 */
fun IGraphical.cycleMaterialEngine(slot: Int, model: ItemStack) =
   makeEngine(slot, CycleMaterialEngine(model))

//
// Toggle Engines
//

/**
 * Installs a toggle engine in the specified [slot] applying [block].
 */
inline fun IGraphical.toggleEngine(slot: Int, material: Materials, block: ToggleEngine.() -> Unit) =
   makeEngine(slot, ToggleEngine(material), block)

/**
 * Installs a toggle engine in the specified [slot] applying [block].
 */
inline fun IGraphical.toggleEngine(slot: Int, model: ItemStack, block: ToggleEngine.() -> Unit) =
   makeEngine(slot, ToggleEngine(model), block)

/**
 * Installs a toggle engine in the specified [slot].
 */
fun IGraphical.toggleEngine(slot: Int, material: Materials) =
   makeEngine(slot, ToggleEngine(material))

/**
 * Installs a toggle engine in the specified [slot].
 */
fun IGraphical.toggleEngine(slot: Int, model: ItemStack) =
   makeEngine(slot, ToggleEngine(model))

//
// Toggle Filter Engines
//

/**
 * Installs a toggle filter engine in the specified [slot] applying [block].
 */
inline fun FilterGraphical<*>.toggleFilterEngine(
   slot: Int, material: Materials, block: ToggleFilterEngine.() -> Unit
) = makeEngine(slot, ToggleFilterEngine(material), block)

/**
 * Installs a toggle filter engine in the specified [slot] applying [block].
 */
inline fun FilterGraphical<*>.toggleFilterEngine(
   slot: Int, model: ItemStack, block: ToggleFilterEngine.() -> Unit
) = makeEngine(slot, ToggleFilterEngine(model), block)

/**
 * Installs a toggle filter engine in the specified [slot].
 */
fun FilterGraphical<*>.toggleFilterEngine(slot: Int, material: Materials) =
   makeEngine(slot, ToggleFilterEngine(material))

/**
 * Installs a toggle filter engine in the specified [slot].
 */
fun FilterGraphical<*>.toggleFilterEngine(slot: Int, model: ItemStack) =
   makeEngine(slot, ToggleFilterEngine(model))

//
// Toggle Sorter Engines
//

/**
 * Installs a toggle sorter engine in the specified [slot] applying [block].
 */
inline fun SortGraphical<*>.toggleSorterEngine(
   slot: Int, material: Materials, block: ToggleSorterEngine.() -> Unit
) = makeEngine(slot, ToggleSorterEngine(material), block)

/**
 * Installs a toggle sorter engine in the specified [slot] applying [block].
 */
inline fun SortGraphical<*>.toggleSorterEngine(
   slot: Int, model: ItemStack, block: ToggleSorterEngine.() -> Unit
) = makeEngine(slot, ToggleSorterEngine(model), block)

/**
 * Installs a toggle sorter engine in the specified [slot].
 */
fun SortGraphical<*>.toggleSorterEngine(slot: Int, material: Materials) =
   makeEngine(slot, ToggleSorterEngine(material))

/**
 * Installs a toggle sorter engine in the specified [slot].
 */
fun SortGraphical<*>.toggleSorterEngine(slot: Int, model: ItemStack) =
   makeEngine(slot, ToggleSorterEngine(model))

//
// Requirement Engines
//

/**
 * Installs a requirement engine in the specified [slot] applying [block].
 */
inline fun IGraphical.reqEngine(slot: Int, material: Materials, block: ReqEngine.() -> Unit) =
   makeEngine(slot, ReqEngine(material), block)

/**
 * Installs a requirement engine in the specified [slot] applying [block].
 */
inline fun IGraphical.reqEngine(slot: Int, model: ItemStack, block: ReqEngine.() -> Unit) =
   makeEngine(slot, ReqEngine(model), block)

/**
 * Installs a requirement engine in the specified [slot].
 */
fun IGraphical.reqEngine(slot: Int, material: Materials) =
   makeEngine(slot, ReqEngine(material))

/**
 * Installs a requirement engine in the specified [slot].
 */
fun IGraphical.reqEngine(slot: Int, model: ItemStack) =
   makeEngine(slot, ReqEngine(model))

//
// Item Requirement Engines
//

/**
 * Installs a item requirement engine in the specified [slot] applying [block].
 */
inline fun IGraphical.reqItemEngine(slot: Int, material: Materials, block: ItemReqEngine.() -> Unit) =
   makeEngine(slot, ItemReqEngine(material), block)

/**
 * Installs a item requirement engine in the specified [slot] applying [block].
 */
inline fun IGraphical.reqItemEngine(slot: Int, model: ItemStack, block: ItemReqEngine.() -> Unit) =
   makeEngine(slot, ItemReqEngine(model), block)

/**
 * Installs a item requirement engine in the specified [slot].
 */
fun IGraphical.reqItemEngine(slot: Int, material: Materials) =
   makeEngine(slot, ItemReqEngine(material))

/**
 * Installs a item requirement engine in the specified [slot].
 */
fun IGraphical.reqItemEngine(slot: Int, model: ItemStack) =
   makeEngine(slot, ItemReqEngine(model))

//
// Count Engines
//

/**
 * Installs a count engine in the specified [slot] applying [block].
 */
inline fun IGraphical.countEngine(slot: Int, material: Materials, block: CountEngine.() -> Unit) =
   makeEngine(slot, CountEngine(material), block)

/**
 * Installs a count engine in the specified [slot] applying [block].
 */
inline fun IGraphical.countEngine(slot: Int, model: ItemStack, block: CountEngine.() -> Unit) =
   makeEngine(slot, CountEngine(model), block)

/**
 * Installs a count engine in the specified [slot].
 */
fun IGraphical.countEngine(slot: Int, material: Materials) =
   makeEngine(slot, CountEngine(material))

/**
 * Installs a count engine in the specified [slot].
 */
fun IGraphical.countEngine(slot: Int, model: ItemStack) =
   makeEngine(slot, CountEngine(model))

//
// Amount Count Engines
//

/**
 * Installs an amount count engine in the specified [slot] applying [block].
 */
inline fun IGraphical.amountCountEngine(slot: Int, material: Materials, block: AmountCountEngine.() -> Unit) =
   makeEngine(slot, AmountCountEngine(material), block)

/**
 * Installs an amount count engine in the specified [slot] applying [block].
 */
inline fun IGraphical.amountCountEngine(slot: Int, model: ItemStack, block: AmountCountEngine.() -> Unit) =
   makeEngine(slot, AmountCountEngine(model), block)

/**
 * Installs an amount count engine in the specified [slot].
 */
fun IGraphical.amountCountEngine(slot: Int, material: Materials) =
   makeEngine(slot, AmountCountEngine(material))

/**
 * Installs an amount count engine in the specified [slot].
 */
fun IGraphical.amountCountEngine(slot: Int, model: ItemStack) =
   makeEngine(slot, AmountCountEngine(model))

//
// Processor Engines
//

/**
 * Installs a processor engine in the specified [slot] applying [block].
 */
inline fun IGraphical.processorEngine(slot: Int, material: Materials, block: ProcessorEngine.() -> Unit) =
   makeEngine(slot, ProcessorEngine(material), block)

/**
 * Installs a processor engine in the specified [slot] applying [block].
 */
inline fun IGraphical.processorEngine(slot: Int, model: ItemStack, block: ProcessorEngine.() -> Unit) =
   makeEngine(slot, ProcessorEngine(model), block)

/**
 * Installs a processor engine in the specified [slot].
 */
fun IGraphical.processorEngine(slot: Int, material: Materials) =
   makeEngine(slot, ProcessorEngine(material))

/**
 * Installs a processor engine in the specified [slot].
 */
fun IGraphical.processorEngine(slot: Int, model: ItemStack) =
   makeEngine(slot, ProcessorEngine(model))

//
// Filter Engines
//

/**
 * Installs a filter engine in the specified [slot] applying [block].
 */
inline fun FilterGraphical<*>.filterEngine(slot: Int, material: Materials, block: FilterEngine.() -> Unit) =
   makeEngine(slot, FilterEngine(material), block)

/**
 * Installs a filter engine in the specified [slot] applying [block].
 */
inline fun FilterGraphical<*>.filterEngine(slot: Int, model: ItemStack, block: FilterEngine.() -> Unit) =
   makeEngine(slot, FilterEngine(model), block)

/**
 * Installs a filter engine in the specified [slot].
 */
fun FilterGraphical<*>.filterEngine(slot: Int, material: Materials) =
   makeEngine(slot, FilterEngine(material))

/**
 * Installs a filter engine in the specified [slot].
 */
fun FilterGraphical<*>.filterEngine(slot: Int, model: ItemStack) =
   makeEngine(slot, FilterEngine(model))

//
// Sorter Engines
//

/**
 * Installs a sorter engine in the specified [slot] applying [block].
 */
inline fun SortGraphical<*>.sorterEngine(slot: Int, material: Materials, block: SorterEngine.() -> Unit) =
   makeEngine(slot, SorterEngine(material), block)

/**
 * Installs a sorter engine in the specified [slot] applying [block].
 */
inline fun SortGraphical<*>.sorterEngine(slot: Int, model: ItemStack, block: SorterEngine.() -> Unit) =
   makeEngine(slot, SorterEngine(model), block)

/**
 * Installs a sorter engine in the specified [slot].
 */
fun SortGraphical<*>.sorterEngine(slot: Int, material: Materials) =
   makeEngine(slot, SorterEngine(material))

/**
 * Installs a sorter engine in the specified [slot].
 */
fun SortGraphical<*>.sorterEngine(slot: Int, model: ItemStack) =
   makeEngine(slot, SorterEngine(model))

//
// Access Engines
//

/**
 * Installs an access engine in the specified [slot] applying [block].
 */
fun IGraphical.accessEngine(slot: Int, material: Materials, block: AccessEngine.() -> Unit) =
   makeEngine(slot, AccessEngine(material), block)

/**
 * Installs an access engine in the specified [slot] applying [block].
 */
inline fun IGraphical.accessEngine(slot: Int, model: ItemStack, block: AccessEngine.() -> Unit) =
   makeEngine(slot, AccessEngine(model), block)

/**
 * Installs an access engine in the specified [slot].
 */
fun IGraphical.accessEngine(slot: Int, material: Materials) =
   makeEngine(slot, AccessEngine(material))

/**
 * Installs an access engine in the specified [slot].
 */
fun IGraphical.accessEngine(slot: Int, model: ItemStack) =
   makeEngine(slot, AccessEngine(model))

package walkmc.graphical.dsl

import org.bukkit.inventory.*
import walkmc.*
import walkmc.graphical.*
import walkmc.graphical.common.*
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
 * Install an engine in the specified [slot] applying [block].
 */
inline fun <T : Engine> IGraphical.makeEngine(x: Int, y: Int, engine: T, block: T.() -> Unit): T {
   install(slotAt(x, y), engine.apply(block))
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
 * Installs a simple engine in the specified [slot] applying [block].
 */
inline fun IGraphical.engine(x: Int, y: Int, material: Materials, block: Engine.() -> Unit) =
   makeEngine(x, y, Engine(material), block)

/**
 * Installs a simple engine in the specified [slot] applying [block].
 */
inline fun IGraphical.engine(x: Int, y: Int, model: ItemStack, block: Engine.() -> Unit) =
   makeEngine(x, y, Engine(model), block)

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
 * Installs a cycle engine in the specified [slot] applying [block].
 */
inline fun IGraphical.cycleEngine(x: Int, y: Int, material: Materials, block: CycleEngine.() -> Unit) =
   makeEngine(x, y, CycleEngine(material), block)

/**
 * Installs a cycle engine in the specified [slot] applying [block].
 */
inline fun IGraphical.cycleEngine(x: Int, y: Int, model: ItemStack, block: CycleEngine.() -> Unit) =
   makeEngine(x, y, CycleEngine(model), block)

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
 * Installs a cycle material engine in the specified [slot] applying [block].
 */
inline fun IGraphical.cycleMaterialEngine(x: Int, y: Int, material: Materials, block: CycleMaterialEngine.() -> Unit) =
   makeEngine(slotAt(x, y), CycleMaterialEngine(material), block)

/**
 * Installs a cycle material engine in the specified [slot] applying [block].
 */
inline fun IGraphical.cycleMaterialEngine(x: Int, y: Int, model: ItemStack, block: CycleMaterialEngine.() -> Unit) =
   makeEngine(slotAt(x, y), CycleMaterialEngine(model), block)

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
 * Installs a toggle engine in the specified [slot] applying [block].
 */
inline fun IGraphical.toggleEngine(x: Int, y: Int, material: Materials, block: ToggleEngine.() -> Unit) =
   makeEngine(x, y, ToggleEngine(material), block)

/**
 * Installs a toggle engine in the specified [slot] applying [block].
 */
inline fun IGraphical.toggleEngine(x: Int, y: Int, model: ItemStack, block: ToggleEngine.() -> Unit) =
   makeEngine(x, y, ToggleEngine(model), block)

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
 * Installs a toggle filter engine in the specified [slot] applying [block].
 */
inline fun FilterGraphical<*>.toggleFilterEngine(
   x: Int, y: Int, material: Materials, block: ToggleFilterEngine.() -> Unit
) = makeEngine(x, y, ToggleFilterEngine(material), block)

/**
 * Installs a toggle filter engine in the specified [slot] applying [block].
 */
inline fun FilterGraphical<*>.toggleFilterEngine(
   x: Int, y: Int, model: ItemStack, block: ToggleFilterEngine.() -> Unit
) = makeEngine(x, y, ToggleFilterEngine(model), block)

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
 * Installs a toggle sorter engine in the specified [slot] applying [block].
 */
inline fun SortGraphical<*>.toggleSorterEngine(
   x: Int, y: Int, material: Materials, block: ToggleSorterEngine.() -> Unit
) = makeEngine(x, y, ToggleSorterEngine(material), block)

/**
 * Installs a toggle sorter engine in the specified [slot] applying [block].
 */
inline fun SortGraphical<*>.toggleSorterEngine(x: Int, y: Int, model: ItemStack, block: ToggleSorterEngine.() -> Unit) =
   makeEngine(x, y, ToggleSorterEngine(model), block)

//
// Requirement Engines
//

/**
 * Installs a requirement engine in the specified [slot] applying [block].
 */
inline fun IGraphical.reqEngine(slot: Int, material: Materials, block: RequirementEngine.() -> Unit) =
   makeEngine(slot, RequirementEngine(material), block)

/**
 * Installs a requirement engine in the specified [slot] applying [block].
 */
inline fun IGraphical.reqEngine(slot: Int, model: ItemStack, block: RequirementEngine.() -> Unit) =
   makeEngine(slot, RequirementEngine(model), block)

/**
 * Installs a requirement engine in the specified [slot] applying [block].
 */
inline fun IGraphical.reqEngine(x: Int, y: Int, material: Materials, block: RequirementEngine.() -> Unit) =
   makeEngine(x, y, RequirementEngine(material), block)

/**
 * Installs a requirement engine in the specified [slot] applying [block].
 */
inline fun IGraphical.reqEngine(x: Int, y: Int, model: ItemStack, block: RequirementEngine.() -> Unit) =
   makeEngine(x, y, RequirementEngine(model), block)

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
 * Installs a count engine in the specified [slot] applying [block].
 */
inline fun IGraphical.countEngine(x: Int, y: Int, material: Materials, block: CountEngine.() -> Unit) =
   makeEngine(x, y, CountEngine(material), block)

/**
 * Installs a count engine in the specified [slot] applying [block].
 */
inline fun IGraphical.countEngine(x: Int, y: Int, model: ItemStack, block: CountEngine.() -> Unit) =
   makeEngine(x, y, CountEngine(model), block)

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
 * Installs an amount count engine in the specified [slot] applying [block].
 */
inline fun IGraphical.amountCountEngine(x: Int, y: Int, material: Materials, block: AmountCountEngine.() -> Unit) =
   makeEngine(x, y, AmountCountEngine(material), block)

/**
 * Installs an amount count engine in the specified [slot] applying [block].
 */
inline fun IGraphical.amountCountEngine(x: Int, y: Int, model: ItemStack, block: AmountCountEngine.() -> Unit) =
   makeEngine(x, y, AmountCountEngine(model), block)

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
 * Installs a processor engine in the specified [slot] applying [block].
 */
inline fun IGraphical.processorEngine(x: Int, y: Int, material: Materials, block: ProcessorEngine.() -> Unit) =
   makeEngine(x, y, ProcessorEngine(material), block)

/**
 * Installs a processor engine in the specified [slot] applying [block].
 */
inline fun IGraphical.processorEngine(x: Int, y: Int, model: ItemStack, block: ProcessorEngine.() -> Unit) =
   makeEngine(x, y, ProcessorEngine(model), block)

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
 * Installs a filter engine in the specified [slot] applying [block].
 */
inline fun FilterGraphical<*>.filterEngine(
   x: Int, y: Int, material: Materials, block: FilterEngine.() -> Unit
) = makeEngine(x, y, FilterEngine(material), block)

/**
 * Installs a filter engine in the specified [slot] applying [block].
 */
inline fun FilterGraphical<*>.filterEngine(
   x: Int, y: Int, model: ItemStack, block: FilterEngine.() -> Unit
) = makeEngine(x, y, FilterEngine(model), block)

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
 * Installs a sorter engine in the specified [slot] applying [block].
 */
inline fun SortGraphical<*>.sorterEngine(
   x: Int, y: Int, material: Materials, block: SorterEngine.() -> Unit
) = makeEngine(x, y, SorterEngine(material), block)

/**
 * Installs a sorter engine in the specified [slot] applying [block].
 */
inline fun SortGraphical<*>.sorterEngine(
   x: Int, y: Int, model: ItemStack, block: SorterEngine.() -> Unit
) = makeEngine(x, y, SorterEngine(model), block)

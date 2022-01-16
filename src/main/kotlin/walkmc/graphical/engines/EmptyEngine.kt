package walkmc.graphical.engines

import net.minecraft.server.*
import org.bukkit.Material
import org.bukkit.event.inventory.*
import org.bukkit.inventory.ItemStack
import walkmc.*
import walkmc.graphical.*
import walkmc.graphical.interfaces.*
import walkmc.item.*

object EmptyEngine : Engine(Materials.AIR) {
   override var graphical: IGraphical? = null; set(value) = Unit
   
   override var slot = 0; set(value) = Unit
   override var isVisible = false; set(value) = Unit
   
   override fun getTag() = null
   override fun hasTag() = false
   override fun setTag(tag: NBTTagCompound?) = Unit
   
   override fun getCustomItem() = null
   override fun hasCustomItem() = false
   override fun setCustomItem(customItem: IItem?) = Unit
   
   override fun setMaterial(material: Materials?) = Unit
   override fun getMaterial() = Materials.AIR
   
   override fun getType() = Material.AIR
   override fun setType(type: Material?) = Unit
   
   override fun getAmount() = 0
   override fun setAmount(amount: Int) = Unit
   
   override fun setName(name: String?) = Unit
   override fun getName() = ""
   override fun hasName() = false
   
   override fun setLore(lore: MutableList<String>?) = Unit
   override fun getLore() = emptyList<String>()
   override fun hasLore() = false
   
   override fun press(event: InventoryClickEvent) = Unit
   override fun render() = Unit
   override suspend fun work() = Unit
   override fun scroll() = Unit
   
   override fun onPress(action: PressAction) = false
   override fun onRender(action: RenderAction) = false
   override fun onWork(action: WorkerAction) = false
   override fun onScroll(action: ScrollAction) = false
   
   override fun notifyChange() = Unit
   
   override fun alter(engine: Engine) = this
   override fun alter(item: ItemStack) = this
   override fun alter(engine: Engine, action: AlterAction) = this
   override fun alter(item: ItemStack, action: ItemStack.() -> Unit) = this
}

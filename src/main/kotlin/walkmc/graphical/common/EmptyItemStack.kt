package walkmc.graphical.common

import net.minecraft.server.*
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import walkmc.*
import walkmc.item.*

object EmptyItemStack : ItemStack(Materials.AIR) {
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
}

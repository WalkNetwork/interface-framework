package walkmc.graphical.common

import net.minecraft.server.*
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.*
import org.bukkit.material.*
import walkmc.*
import walkmc.item.*

object EmptyItemStack : ItemStack(Materials.AIR) {
   override fun getCraftItem() = null
   override fun getHandler() = null
   
   override fun getData() = null
   override fun setData(data: MaterialData?) = Unit
   
   override fun getTypeId() = 0
   override fun setTypeId(type: Int) = Unit
   
   override fun getDurability() = 0.toShort()
   override fun setDurability(durability: Short) = Unit
   
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
   
   override fun getMaxStackSize() = 0
   override fun getAmount() = 0
   override fun setAmount(amount: Int) = Unit
   
   override fun setName(name: String?) = Unit
   override fun getName() = ""
   override fun hasName() = false
   
   override fun setLore(lore: MutableList<String>?) = Unit
   override fun getLore() = null
   override fun hasLore() = false
   
   override fun getItemMeta() = null
   override fun setItemMeta(itemMeta: ItemMeta?) = false
   override fun hasItemMeta() = false
   override fun updateMeta(item: net.minecraft.server.ItemStack?) = Unit
   
   override fun getEnchantmentLevel(ench: Enchantment?) = 0
   override fun addEnchantments(enchantments: MutableMap<Enchantment, Int>?) = Unit
   override fun getEnchantments() = emptyMap<Enchantment, Int>()
   override fun addEnchantment(ench: Enchantment?, level: Int) = Unit
   override fun addUnsafeEnchantments(enchantments: MutableMap<Enchantment, Int>?) = Unit
   override fun addUnsafeEnchantment(ench: Enchantment?, level: Int) = Unit
   override fun containsEnchantment(ench: Enchantment?) = false
   override fun removeEnchantment(ench: Enchantment?) = 0
   
   override fun clone() = EmptyItemStack
   
   override fun equals(other: Any?) = other is EmptyItemStack
   override fun isSimilar(stack: ItemStack?) = stack is EmptyItemStack
}


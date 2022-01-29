package walkmc.graphical

import net.minecraft.server.*
import net.minecraft.server.ItemStack
import org.bukkit.entity.*
import org.bukkit.inventory.*
import walkmc.graphical.common.*

typealias OpenInvPacket = PacketPlayOutOpenWindow

/**
 * Represents a container for any inventory created with `interface-framework`.
 *
 * The purpose of this class is to provide better tick execution and Spigot compatibility.
 */
open class Container(
   val graphical: IGraphical,
   playerInventory: IInventory,
   inventory: IInventory,
   player: EntityHuman,
) : ContainerChest(playerInventory, inventory, player) {
   
   constructor(graphical: IGraphical, player: Player, inventory: Inventory) :
      this(graphical, player.inventory.handler, inventory.handler, player.handler)
   
   /**
    * Opens this container to [player].
    *
    * Since NMS don't have a method to open an already
    * created container we have to make a shadow copy.
    */
   fun open(player: Player) {
      val handler = player.handler
      val id = handler.nextContainerCounter()
      
      windowId = id
      addSlotListener(handler)
      checkReachable = false
      
      handler.playerConnection.sendPacket(OpenInvPacket(id, "minecraft:chest", ChatMessage(graphical.title), graphical.size))
      handler.activeContainer = this
   }
   
   // walkmc support
   override fun tickFor(player: EntityPlayer) {
      if (tickCount++ % graphical.tickDelay != 0) return
      
      graphical.tick()
      
      for (i in 0 until c.size) {
         val item = c[i].item
         var other = b[i]
         
         if (!ItemStack.fastMatches(other, item) || !ItemStack.tagMatches(other, item)) {
            other = item?.cloneItemStack()
            b[i] = other
            for (listener in listeners) listener.a(this, i, other)
         }
      }
   }
   
   // spigot support
   override fun b() {
      if (tickCount++ % graphical.tickDelay != 0) return
      
      graphical.tick()
      
      for (i in 0 until c.size) {
         val item = c[i].item
         var other = b[i]
         
         if (!ItemStack.fastMatches(other, item) || !ItemStack.matches(other, item)) {
            other = item?.cloneItemStack()
            b[i] = other
            for (listener in listeners) listener.a(this, i, other)
         }
      }
   }
}

package walkmc.graphical.serializer

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import org.bukkit.*
import org.bukkit.material.*

object MaterialDataSerializer : KSerializer<MaterialData> {
	override val descriptor = PrimitiveSerialDescriptor("MaterialData", PrimitiveKind.STRING)
	
	override fun deserialize(decoder: Decoder): MaterialData = runCatching {
		val split = decoder.decodeString().split(':', limit = 2)
		MaterialData(Material.getMaterial(split[0].toInt()), split[1].toByte())
	}.getOrDefault(MaterialData(Material.AIR))
	
	
	override fun serialize(encoder: Encoder, value: MaterialData) {
		encoder.encodeString("${value.itemType.id}:${value.data}")
	}
}
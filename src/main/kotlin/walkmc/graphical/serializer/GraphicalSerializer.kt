package walkmc.graphical.serializer

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import org.bukkit.*
import org.bukkit.material.*
import walkmc.graphical.*
import walkmc.graphical.common.*

object GraphicalSerializer : KSerializer<IGraphical> {
	override val descriptor: SerialDescriptor = buildClassSerialDescriptor("interface") {
		element<String>("title")
		element<Int>("size")
		element<Boolean>("allow-worker")
		element<Boolean>("enable-background")
		element("background", MaterialDataSerializer.descriptor)
		element("observers", ObserverSerializer.descriptor)
	}
	
	override fun deserialize(decoder: Decoder) = decoder.decodeStructure(descriptor) {
		val graphical = newGraphical("", 1)
		var enableBackground = false
		lateinit var background: MaterialData
		
		while (true) {
			when (val index = decodeElementIndex(descriptor)) {
				0 -> graphical.setTitle(decodeStringElement(descriptor, index))
				1 -> graphical.setSize(decodeIntElement(descriptor, index))
				2 -> graphical.worker.allow = decodeBooleanElement(descriptor, index)
				3 -> enableBackground = decodeBooleanElement(descriptor, index)
				4 -> background = decodeSerializableElement(descriptor, 4, MaterialDataSerializer)
				5 -> graphical.observers = decodeSerializableElement(descriptor, index, ObserverSerializer)
				else -> break
			}
		}
		
		graphical.apply {
			if (enableBackground) fill(background)
		}
	}
	
	override fun serialize(encoder: Encoder, value: IGraphical) = encoder.encodeStructure(descriptor) {
		encodeStringElement(descriptor, 0, value.title)
		encodeIntElement(descriptor, 1, value.size / 9)
		encodeBooleanElement(descriptor, 2, value.worker.allow)
		encodeBooleanElement(descriptor, 3, false)
		encodeSerializableElement(descriptor, 4, MaterialDataSerializer, MaterialData(Material.STAINED_GLASS_PANE, 7))
		encodeSerializableElement(descriptor, 5, ObserverSerializer, value.observers)
	}
}
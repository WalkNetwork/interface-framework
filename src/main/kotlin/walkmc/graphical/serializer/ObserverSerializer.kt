package walkmc.graphical.serializer

import kotlinx.serialization.*
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*
import walkmc.graphical.interfaces.*

object ObserverSerializer : KSerializer<Observers> {
	override val descriptor: SerialDescriptor = buildClassSerialDescriptor("OBSERVER") {
		element<Boolean>("enable-press")
		element<Boolean>("enable-press-cancellable")
		element<Boolean>("enable-access")
		element<Boolean>("enable-unccess")
		element<Boolean>("enable-pickup")
		element<Boolean>("enable-drag")
	}
	
	override fun deserialize(decoder: Decoder) = decoder.decodeStructure(descriptor) {
		val observers = Observers()
		while (true) {
			when (val index = decodeElementIndex(descriptor)) {
				0 -> observers[ObserverKind.PRESS] = decodeBooleanElement(descriptor, index)
				1 -> observers[ObserverKind.PRESS_CANCELLABLE] = decodeBooleanElement(descriptor, index)
				2 -> observers[ObserverKind.ACCESS] = decodeBooleanElement(descriptor, index)
				3 -> observers[ObserverKind.UNCESS] = decodeBooleanElement(descriptor, index)
				4 -> observers[ObserverKind.PICKUP] = decodeBooleanElement(descriptor, index)
				5 -> observers[ObserverKind.DRAG] = decodeBooleanElement(descriptor, index)
				else -> break
			}
		}
		observers
	}
	
	override fun serialize(encoder: Encoder, value: Observers) {
		encoder.encodeStructure(descriptor) {
			encodeBooleanElement(descriptor, 0, value.getOrDefault(ObserverKind.PRESS, true))
			encodeBooleanElement(descriptor, 1, value.getOrDefault(ObserverKind.PRESS_CANCELLABLE, true))
			encodeBooleanElement(descriptor, 2, value.getOrDefault(ObserverKind.ACCESS, true))
			encodeBooleanElement(descriptor, 3, value.getOrDefault(ObserverKind.UNCESS, true))
			encodeBooleanElement(descriptor, 4, value.getOrDefault(ObserverKind.PICKUP, false))
			encodeBooleanElement(descriptor, 5, value.getOrDefault(ObserverKind.DRAG, false))
		}
	}
}
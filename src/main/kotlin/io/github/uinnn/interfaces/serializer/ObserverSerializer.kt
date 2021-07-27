package io.github.uinnn.interfaces.serializer

import io.github.uinnn.interfaces.common.ObserverKind
import io.github.uinnn.interfaces.common.Observers
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure

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
        3 -> observers[ObserverKind.UNCCESS] = decodeBooleanElement(descriptor, index)
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
      encodeBooleanElement(descriptor, 3, value.getOrDefault(ObserverKind.UNCCESS, true))
      encodeBooleanElement(descriptor, 4, value.getOrDefault(ObserverKind.PICKUP, false))
      encodeBooleanElement(descriptor, 5, value.getOrDefault(ObserverKind.DRAG, false))
    }
  }
}
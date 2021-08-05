package io.github.uinnn.interfaces.serializer

import io.github.uinnn.interfaces.GraphicalInterface
import io.github.uinnn.interfaces.InterfaceFactory
import io.github.uinnn.interfaces.common.fill
import io.github.uinnn.interfaces.setSize
import io.github.uinnn.interfaces.setTitle
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
import org.bukkit.Material
import org.bukkit.material.MaterialData

object GraphicalInterfaceSerializer : KSerializer<GraphicalInterface> {
  override val descriptor: SerialDescriptor = buildClassSerialDescriptor("interface") {
    element<String>("title")
    element<Int>("size")
    element<Boolean>("allow-worker")
    element<Boolean>("enable-background")
    element("background", MaterialDataSerializer.descriptor)
    element("observers", ObserverSerializer.descriptor)
  }
  
  override fun deserialize(decoder: Decoder) = decoder.decodeStructure(descriptor) {
    val graphical = InterfaceFactory.empty()
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
  
  override fun serialize(encoder: Encoder, value: GraphicalInterface) = encoder.encodeStructure(descriptor) {
    encodeStringElement(descriptor, 0, value.title)
    encodeIntElement(descriptor, 1, value.size / 9)
    encodeBooleanElement(descriptor, 2, value.worker.allow)
    encodeBooleanElement(descriptor, 3, false)
    encodeSerializableElement(descriptor, 4, MaterialDataSerializer, MaterialData(Material.STAINED_GLASS_PANE, 7))
    encodeSerializableElement(descriptor, 5, ObserverSerializer, value.observers)
  }
}
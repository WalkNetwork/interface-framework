package walkmc.graphical

import java.util.*

class Source(
   val graphical: IScrollGraphical,
   private val delegate: MutableList<Engine> = LinkedList()
) : MutableList<Engine> by delegate {
   
   override fun add(element: Engine): Boolean {
      graphical.isDirty = true
      return delegate.add(element)
   }
   
   override fun add(index: Int, element: Engine) {
      graphical.isDirty = true
      delegate.add(index, element)
   }
   
   override fun remove(element: Engine): Boolean {
      graphical.isDirty = true
      return delegate.remove(element)
   }
   
   override fun removeAt(index: Int): Engine {
      graphical.isDirty = true
      return delegate.removeAt(index)
   }
   
   override fun clear() {
      graphical.isDirty = true
      delegate.clear()
   }
   
   override fun addAll(elements: Collection<Engine>): Boolean {
      graphical.isDirty = true
      return delegate.addAll(elements)
   }
}

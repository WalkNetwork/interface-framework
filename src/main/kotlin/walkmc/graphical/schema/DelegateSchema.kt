package walkmc.graphical.schema

open class DelegateSchema(delegate: MutableSet<Int>) : Schema by delegate

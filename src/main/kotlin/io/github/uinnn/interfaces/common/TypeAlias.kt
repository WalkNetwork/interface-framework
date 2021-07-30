package io.github.uinnn.interfaces.common

typealias Filter<T> = (T) -> Boolean
typealias Action<T> = T.() -> Unit
typealias SuspendableAction<T> = suspend T.() -> Unit
typealias MultiAction<T, E> = T.(E) -> Unit
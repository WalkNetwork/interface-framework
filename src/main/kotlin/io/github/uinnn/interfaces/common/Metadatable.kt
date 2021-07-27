package io.github.uinnn.interfaces.common

typealias Storage = HashMap<String, Any>

interface Metadatable {
  var storage: Storage

  /**
   * Interject a metadata, this is,
   * put a data or overwrite a existent data.
   */
  fun interject(key: String, value: Any) = storage.put(key, value)

  /**
   * Introduce a metadata, this is,
   * put a data only if not exists in the storage.
   */
  fun introduce(key: String, value: Any) = storage.putIfAbsent(key, value)

  /**
   * Invalidates a metadata, this is,
   * remove a existent data in the storage.
   */
  fun invalidate(key: String) = storage.remove(key)

  /**
   * Try locates a existent metadata, or
   * null if the data not exists in the storage.
   */
  fun <T> locate(key: String): T? = storage[key] as? T

  /**
   * Acquire a existent metadata. If
   * not exists in the storage a NPE will be throw.
   */
  fun <T> acquire(key: String): T = storage[key] as T
}
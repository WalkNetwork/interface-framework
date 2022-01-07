package walkmc.graphical.interfaces

typealias Storage = MutableMap<String, Any>

/**
 * Represents a metadatable object for graphical user interfaces or engines.
 * This is, the object will be able to store metadata of [Any] (type-safe) type
 * with a [String] as key.
 */
interface Metadatable {
	
	/**
	 * All registered metadata of this metadatable object.
	 */
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
	 * Returns if the specified key is in the storage
	 * of this metadata object.
	 */
	fun includes(key: String) = storage.containsKey(key)
	
	/**
	 * Invalidates a metadata, this is,
	 * remove a existent metadata in the storage.
	 */
	fun invalidate(key: String) = storage.remove(key)
	
	/**
	 * Try locates a existent metadata, or
	 * null if the metadata not exists in the storage.
	 */
	fun <T> locate(key: String): T? = storage[key] as? T
	
	/**
	 * Acquire a existent metadata as a [Result] of [T].
	 * Supporting this was safe try-catch use.
	 */
	fun <T> acquire(key: String): Result<T> = runCatching {
		storage[key] as T
	}
}

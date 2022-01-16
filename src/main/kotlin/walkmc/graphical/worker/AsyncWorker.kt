package walkmc.graphical.worker

import kotlinx.coroutines.*
import walkmc.extensions.*
import walkmc.graphical.*
import walkmc.graphical.interfaces.*
import kotlin.time.*
import kotlin.time.Duration.Companion.seconds
import java.util.concurrent.*

/**
 * A worker default class. This class works
 * everything in async mode. By default a
 * [IGraphical] NOT allows a worker to work
 * just because that consumes more server processing.
 * You can change if a worker is allowed to work changing
 * the [allow] property. Also by defaults, the interval
 * for work is one second ([interval]).
 *
 * ### Note:
 * If you want for example run a sync task you need
 * to execute the default bukkit scheduler sync task.
 */
@Deprecated("Workers are now replaced by Tickable.")
class AsyncWorker(
	override val graphical: IGraphical,
	override var allow: Boolean = false,
	override var interval: Duration = 1.seconds,
) : Worker {
	override var job: Job = WorkerScope.create(this)
	override var storage: Storage = ConcurrentHashMap()
	
	override fun start() {
		if (!canLaunch) return
		if (!isFirstLaunch) reconstruct()
		logWarning("§e[interface-framework] Workers are deprecated, please replace by Tickable interface.")
		job.start()
		launchs++
	}
	
	override fun cancel() {
		if (!isWorking) return
		job.cancel()
	}
}

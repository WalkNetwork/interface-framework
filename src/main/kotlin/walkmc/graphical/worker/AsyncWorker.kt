package walkmc.graphical.worker

import kotlinx.coroutines.*
import walkmc.graphical.*
import walkmc.graphical.interfaces.*
import kotlin.time.*
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
class AsyncWorker(
	override val graphical: IGraphical,
	override var allow: Boolean = false,
	override var interval: Duration = Duration.seconds(1),
) : Worker {
	override var job: Job = WorkerScope.create(this)
	override var storage: Storage = ConcurrentHashMap()
	
	override fun start() {
		if (!canLaunch) return
		if (!isFirstLaunch) reconstruct()
		job.start()
		launchs++
	}
	
	override fun cancel() {
		if (!isWorking) return
		job.cancel()
	}
}

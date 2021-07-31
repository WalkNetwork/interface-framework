package io.github.uinnn.interfaces.worker

import io.github.uinnn.interfaces.GraphicalInterface
import io.github.uinnn.interfaces.interfaces.Storage
import kotlinx.coroutines.Job
import kotlin.time.Duration

/**
 * A worker default class. This class works
 * everything in async mode. By default a
 * [GraphicalInterface] NOT allows a worker to work
 * just because that consumes more server processing.
 * You can change if a worker is allowed to work changing
 * the [allow] property. Also by defaults, the interval
 * for work is one second ([interval]).
 *
 * ### Note:
 * If you want for example run a sync task you need
 * to execute the default bukkit scheduler sync task.
 */
class AsynchronousWorker(
  override val graphical: GraphicalInterface,
  override var allow: Boolean = false,
  override var interval: Duration = Duration.seconds(1)
) : Worker {
  override var job: Job = WorkerScope.create(this)
  override var storage: Storage = Storage()

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
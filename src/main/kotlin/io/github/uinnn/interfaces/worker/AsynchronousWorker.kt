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
  override var storage: Storage = Storage()
  override var job: Job? = null

  override fun launch() {
    if (!allow) return
    if (job == null)
      job = WorkerScope.start(this)
    if (isWorking) return
    job!!.start()
  }

  override fun resume() {
    if (job == null) return
    if (!isWorking) return
    job!!.cancel()
    job = null
  }
}
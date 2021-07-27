package io.github.uinnn.interfaces.worker

import io.github.uinnn.interfaces.GraphicalUserInterface
import kotlinx.coroutines.Job
import kotlin.time.Duration

/**
 * A worker default class. This class works
 * everything in async mode. By default a
 * [GraphicalUserInterface] NOT allows a worker to work
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
  graphical: GraphicalUserInterface,
  allow: Boolean = false,
  interval: Duration = Duration.seconds(1)
) : AbstractWorker(graphical, allow, interval) {
  override var job: Job = WorkerScope.launch(this)
}


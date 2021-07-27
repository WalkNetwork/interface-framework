package io.github.uinnn.interfaces.worker

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * A worker scope for creating jobs with kotlin coroutines.
 * @see CoroutineScope
 */
object WorkerScope : CoroutineScope {
  override val coroutineContext: CoroutineContext = EmptyCoroutineContext

  /**
   * Creates a new coroutine with start as [CoroutineStart.LAZY]
   * running indefinitely, this is, will run forever until a
   * cancellament of the coroutine. You can changes the delay changing
   * the interval of the worker. This is used in [AsynchronousWorker].
   */
  fun launch(worker: Worker): Job {
    return launch(start = CoroutineStart.LAZY) {
      while (true) {
        delay(worker.interval)
        worker.graphical.work()
        worker.workedAmount++
      }
    }
  }

  /**
   * Creates a new coroutine with start as [CoroutineStart.LAZY]
   * running at a limited amount of times, specified by [times].
   * You can changes the delay changing the interval of the worker.
   */
  fun launch(worker: Worker, times: Int): Job {
    return launch(start = CoroutineStart.LAZY) {
      repeat(times) {
        delay(worker.interval)
        worker.graphical.work()
        worker.workedAmount++
      }
    }
  }
}
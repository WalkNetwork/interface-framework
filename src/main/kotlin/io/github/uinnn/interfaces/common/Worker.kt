package io.github.uinnn.interfaces.common

import io.github.uinnn.interfaces.GraphicalUserInterface
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.math.max
import kotlin.time.Duration

typealias WorkerAction = suspend GraphicalUserInterface.() -> Unit
typealias WorkSet = HashSet<WorkerAction>

interface Workable {
  var works: WorkSet

  /**
   * Starts the work of this
   * workable object.
   */
  suspend fun work()

  /**
   * Register a handler to be
   * triggered when works. (update)
   */
  fun onWork(action: WorkerAction) = works.add(action)
}

/**
 * A worker is a updater for all's graphical users interfaces.
 * Any [GraphicalUserInterface] has a [AsynchronousWorker]
 * by default worker.
 */
interface Worker : Metadatable {

  /**
   * The job of this worker instance.
   */
  var job: Job

  /**
   * The graphical user interface owner of this worker.
   */
  val graphical: GraphicalUserInterface

  /**
   * If this worker is able to work.
   */
  var allow: Boolean

  /**
   * The delay of this worker before he's works again.
   */
  var interval: Duration

  /**
   * Launchs the worker. This is, starts
   * the worker to work and updates this [graphical].
   */
  fun launch()

  /**
   * Cancels the worker. This is, if the worker
   * is started, will be stopped.
   */
  fun resume()
}

/**
 * A worker default class. This class works
 * everything in async mode. By default a
 * [GraphicalUserInterface] NOT allows a worker to work
 * just because that consumes more server processing.
 * You can change if a worker is allowed to work changing
 * the [allow] property. Also by defaults, the interval
 * for work is one second ([interval]).
 */
class AsynchronousWorker(
  override val graphical: GraphicalUserInterface,
  override var allow: Boolean = false,
  override var interval: Duration = Duration.seconds(1)
) : Worker {
  override var job: Job = WorkerScope.launch(graphical, interval)
  override var storage: Storage = Storage()

  override fun launch() {
    if (!allow) return
    if (job.isActive) return
    job.start()
    launchs++
  }

  override fun resume() {
    if (!job.isActive) return
    job.cancel()
    resumes++
  }
}

/**
 * A worker scope for creating jobs with kotlin coroutines.
 * @see CoroutineScope
 */
object WorkerScope : CoroutineScope {
  override val coroutineContext: CoroutineContext = EmptyCoroutineContext

  /**
   * Creates a new coroutine with start as [CoroutineStart.LAZY]
   * running indefinitely, this is, will run forever until a
   * cancellament of the coroutine. You can changes the delay with [interval]
   */
  fun launch(graphical: GraphicalUserInterface, interval: Duration): Job {
    return launch(start = CoroutineStart.LAZY) {
      while (true) {
        delay(interval)
        graphical.work()
      }
    }
  }
}

/**
 * Resumes and denies this worker the possibility of working.
 */
fun Worker.resumeAndDeny() {
  resume()
  allow = false
}

/**
 * Reconstructs this work. This is, replaces
 * the old job with a new never used job.
 * This is a internal API, but you can use in some cases.
 * Don't use it if you don't know what you're doing.
 */
fun Worker.reconstruct() {
  job = WorkerScope.launch(graphical, interval)
  reconstructions++
}

/**
 * Gets the amount of this worker launcheds.
 */
inline var Worker.launchs: Int
  get() = locate("Launchs") ?: 0
  set(value) {
    interject("Launchs", max(1, value))
  }

/**
 * Gets the amount of this worker resumes.
 */
inline var Worker.resumes: Int
  get() = locate("Resumes") ?: 0
  set(value) {
    interject("Resumes", max(1, value))
  }

/**
 * Gets the amount of this worker reconstructions.
 */
inline var Worker.reconstructions: Int
  get() = locate("Reconstructions") ?: 0
  set(value) {
    interject("Reconstructions", max(1, value))
  }
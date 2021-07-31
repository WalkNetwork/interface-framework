package io.github.uinnn.interfaces.worker

import io.github.uinnn.interfaces.GraphicalInterface
import io.github.uinnn.interfaces.interfaces.Metadatable
import kotlinx.coroutines.Job
import kotlin.math.max
import kotlin.time.Duration

/**
 * A worker is a updater for all's graphical users interfaces.
 * Any [GraphicalInterface] has a [AsynchronousWorker]
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
  val graphical: GraphicalInterface

  /**
   * If this worker is able to work.
   */
  var allow: Boolean

  /**
   * The delay of this worker before he's works again.
   */
  var interval: Duration

  /**
   * Starts the worker. This is, starts
   * the worker to work and updates this [graphical].
   */
  fun start()

  /**
   * Cancels the worker. This is, if the worker
   * is started, will be stopped.
   */
  fun cancel()
}

/**
 * Resumes and denies this worker the possibility of working.
 */
fun Worker.cancelAndDeny() {
  cancel()
  allow = false
}

/**
 * Reconstructs this work. This is, replaces
 * the old job with a new never used job.
 * This is a internal API, but you can use in some cases.
 * Don't use it if you don't know what you're doing.
 */
fun Worker.reconstruct() {
  job = WorkerScope.create(this)
  reconstructions++
}

/**
 * Gets the amount of this worker launcheds.
 */
var Worker.launchs: Int
  get() = locate("Launchs") ?: 0
  internal set(value) {
    interject("Launchs", max(1, value))
  }

/**
 * Returns if this worker never launcheds.
 */
inline val Worker.isFirstLaunch: Boolean get() = launchs < 1

/**
 * Gets the amount of this worker resumes.
 */
var Worker.resumes: Int
  get() = locate("Resumes") ?: 0
  internal set(value) {
    interject("Resumes", max(1, value))
  }

/**
 * Returns if this worker never resumeds.
 */
inline val Worker.isFirstResume: Boolean get() = resumes < 1

/**
 * Gets the amount of this worker reconstructions.
 */
var Worker.reconstructions: Int
  get() = locate("Reconstructions") ?: 0
  internal set(value) {
    interject("Reconstructions", max(1, value))
  }

/**
 * Returns if this worker never reconstructed.
 */
inline val Worker.isFirstReconstruct: Boolean get() = reconstructions < 1

/**
 * Verifies if this worker can launch. This is equals a
 * ```kt
 * allow || !job.isActive
 * ```
 */
inline val Worker.canLaunch: Boolean get() = allow || !job.isActive

/**
 * Verifies if this worker is working.
 */
inline val Worker.isWorking: Boolean get() = job.isActive

/**
 * Returns the amount of works this worker worked.
 */
var Worker.workedAmount: Int
  get() = locate("WorkedAmount") ?: 0
  internal set(value) {
    interject("WorkedAmount", max(1, value))
  }
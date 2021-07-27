package io.github.uinnn.interfaces.worker

import io.github.uinnn.interfaces.GraphicalUserInterface
import io.github.uinnn.interfaces.interfaces.Storage
import kotlin.time.Duration

/**
 * Abstract instance of a [Worker]. Only used to build news workers.
 */
abstract class AbstractWorker(
  override val graphical: GraphicalUserInterface,
  override var allow: Boolean = false,
  override var interval: Duration = Duration.seconds(1)
) : Worker {
  override var storage: Storage = Storage()

  override fun launch() {
    if (!canLaunch) return
    if (!isFirstLaunch) reconstruct()
    job.start()
    launchs++
  }

  override fun resume() {
    if (!isWorking) return
    job.cancel()
    resumes++
  }
}
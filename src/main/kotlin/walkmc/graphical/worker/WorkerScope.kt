package walkmc.graphical.worker

import kotlinx.coroutines.*
import kotlin.coroutines.*

/**
 * A worker scope for creating jobs with kotlin coroutines.
 * @see CoroutineScope
 */
object WorkerScope : CoroutineScope {
	override val coroutineContext: CoroutineContext = EmptyCoroutineContext
	
	/**
	 * Creates a new coroutine with start as [CoroutineStart.LAZY]
	 * running indefinitely, this is, will run forever until a
	 * cancellament of the coroutine.
	 *
	 * You can change the delay changing the interval of the worker.
	 * This is used in [AsyncWorker].
	 */
	fun create(worker: Worker): Job {
		return async(start = CoroutineStart.LAZY) {
			while (isActive) {
				delay(worker.interval)
				worker.graphical.work()
				worker.workedAmount++
			}
		}
	}
	
	/**
	 * Creates a new coroutine with start as [CoroutineStart.LAZY]
	 * running at a limited amount of times, specified by [times].
	 * You can change the delay changing the interval of the worker.
	 */
	fun create(worker: Worker, times: Int): Job {
		return async(start = CoroutineStart.LAZY) {
			repeat(times) {
				delay(worker.interval)
				worker.graphical.work()
				worker.workedAmount++
			}
		}
	}
}

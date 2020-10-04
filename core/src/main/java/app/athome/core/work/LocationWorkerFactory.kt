package app.athome.core.work

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import app.athome.core.repository.LocationRepository

class LocationWorkerFactory(
    private val locationRepository: LocationRepository
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {

        return when (workerClassName) {
            LocationWorker::class.java.name ->
                LocationWorker(appContext, workerParameters, locationRepository)
            else ->
                null // Return null, so that the base class can delegate to the default WorkerFactory
        }
    }
}

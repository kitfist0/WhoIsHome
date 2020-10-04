package app.athome.core.work

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.services.gmail.Gmail

class PostalWorkerFactory(
    private val credential: GoogleAccountCredential,
    private val gmail: Gmail
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {

        return when (workerClassName) {
            PostalWorker::class.java.name ->
                PostalWorker(appContext, workerParameters, credential, gmail)
            else ->
                null // Return null, so that the base class can delegate to the default WorkerFactory
        }
    }
}

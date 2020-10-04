package app.athome.core.work

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.work.*
import app.athome.core.repository.LocationRepository

import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class LocationWorker(
    context: Context,
    params: WorkerParameters,
    private val locationRepository: LocationRepository
) : CoroutineWorker(context, params) {

    companion object {
        private const val TAG = "LOCATION_WORKER"
        private const val WORK_UNIQUE_ID = "location_worker"
        private const val REPEAT_INTERVAL = 15L
        private const val FLEX_INTERVAL = 10L

        fun schedule(context: Context) {
            Log.d(TAG, "schedule")
            val workRequest = PeriodicWorkRequest.Builder(
                LocationWorker::class.java,
                REPEAT_INTERVAL, TimeUnit.MINUTES,
                FLEX_INTERVAL, TimeUnit.MINUTES
            ).build()
            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                WORK_UNIQUE_ID, ExistingPeriodicWorkPolicy.KEEP, workRequest
            )
        }

        fun cancel(context: Context) {
            Log.d(TAG, "cancel")
            WorkManager.getInstance(context).cancelUniqueWork(WORK_UNIQUE_ID)
        }

        private fun Context.permissionGranted(permission: String) =
            ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED

        private fun Context.hasLocationPermissions() = when {
            android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q ->
                permissionGranted(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                        && permissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)
            else -> permissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)
        }

        @SuppressLint("MissingPermission")
        private fun Context.getLastKnownLocation() =
            LocationServices.getFusedLocationProviderClient(applicationContext).lastLocation
    }

    override suspend fun doWork(): Result {
        Log.d(TAG, "doWork")
        return withContext(Dispatchers.IO) {
            try {
                applicationContext.getLastKnownLocation().await()
                    ?.let {
                        locationRepository.insertLocation(it)
                        Log.d(TAG, "SUCCESS, lat: ${it.latitude}, lon: ${it.longitude}")
                        Result.success()
                    }
                    ?: let {
                        Log.d(TAG, "FAILURE, location is null").also { Result.failure() }
                        Result.failure()
                    }
            } catch (e: Exception) {
                Log.d(TAG, "FAILURE, ${e.message}")
                Result.failure()
            }
        }
    }
}

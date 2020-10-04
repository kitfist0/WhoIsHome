package app.athome.core.repository

import android.location.Location
import app.athome.core.database.dao.LocationDao
import app.athome.core.database.entity.LocationData
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationRepository @Inject constructor(
    private val dao: LocationDao
) {

    companion object {
        private fun Location.toLocationData() = LocationData(
            time = this.time,
            latitude = this.latitude,
            longitude = this.longitude,
            altitude = this.altitude,
            accuracy = this.accuracy
        )

        private val dayAgoMillis
            get() = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(1)
    }

    suspend fun getLocations() = dao.getLocations()

    suspend fun getLastLocation() = dao.getLastLocation()

    suspend fun insertLocation(location: Location) = dao.insertLocation(location.toLocationData())

    suspend fun deleteOldLocations() = dao.deleteLocationsWithTimeLessThan(dayAgoMillis)
}

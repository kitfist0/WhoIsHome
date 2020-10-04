package app.athome.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import app.athome.core.database.entity.LocationData

@Dao
interface LocationDao {

    @Query("SELECT * FROM locations ORDER BY time DESC")
    suspend fun getLocations(): List<LocationData>

    @Query("SELECT * FROM locations ORDER BY time DESC LIMIT 1")
    suspend fun getLastLocation(): LocationData

    @Insert
    suspend fun insertLocation(location: LocationData)

    @Delete
    suspend fun deleteLocations(locations: List<LocationData>)

    @Query("DELETE FROM locations WHERE time < :threshold")
    suspend fun deleteLocationsWithTimeLessThan(threshold: Long)
}

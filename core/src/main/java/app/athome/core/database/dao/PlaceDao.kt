package app.athome.core.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import app.athome.core.database.entity.Place
import app.athome.core.database.entity.PlaceWithRecipients

@Dao
interface PlaceDao {

    @Query("SELECT * FROM places")
    fun getPlaceWithRecipients(): LiveData<List<PlaceWithRecipients>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlace(place: Place): Long

    @Delete
    suspend fun deletePlace(place: Place)
}
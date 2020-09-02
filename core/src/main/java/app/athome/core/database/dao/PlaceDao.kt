package app.athome.core.database.dao

import androidx.room.*
import app.athome.core.database.entity.Place
import app.athome.core.database.entity.PlaceWithRecipients
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaceDao {

    @Transaction
    @Query("SELECT * FROM places")
    fun getPlacesWithRecipients(): Flow<List<PlaceWithRecipients>>

    @Query("SELECT * FROM places WHERE id = :placeId")
    suspend fun getPlaceWithRecipients(placeId: Long): PlaceWithRecipients

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlace(place: Place): Long

    @Delete
    suspend fun deletePlace(place: Place)
}

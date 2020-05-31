package app.athome.core.repository

import androidx.lifecycle.LiveData
import app.athome.core.database.dao.PlaceDao
import app.athome.core.database.entity.Place
import app.athome.core.database.entity.PlaceWithRecipients
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlaceRepository @Inject constructor(
    private val dao: PlaceDao
) {

    fun getPlacesWithRecipients(): LiveData<List<PlaceWithRecipients>> = dao.getPlaceWithRecipients()

    suspend fun insertPlace(place: Place): Long = dao.insertPlace(place)
}
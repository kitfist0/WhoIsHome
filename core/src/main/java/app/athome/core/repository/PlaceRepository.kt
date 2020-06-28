package app.athome.core.repository

import app.athome.core.database.dao.PlaceDao
import app.athome.core.database.entity.Place
import app.athome.core.database.entity.PlaceWithRecipients
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlaceRepository @Inject constructor(
    private val dao: PlaceDao
) {

    fun getPlacesWithRecipients(): Flow<List<PlaceWithRecipients>> = dao.getPlacesWithRecipients()

    suspend fun getPlaceWithRecipients(placeId: Long): PlaceWithRecipients =
        dao.getPlaceWithRecipients(placeId)

    suspend fun insertPlace(place: Place) = dao.insertPlace(place)
}
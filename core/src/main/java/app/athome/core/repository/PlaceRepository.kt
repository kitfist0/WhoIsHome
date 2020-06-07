package app.athome.core.repository

import app.athome.core.database.dao.PlaceDao
import app.athome.core.database.entity.Place
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlaceRepository @Inject constructor(
    private val dao: PlaceDao
) {

    fun getPlaceWithRecipients() = dao.getPlaceWithRecipients()

    suspend fun insertPlace(place: Place) = dao.insertPlace(place)
}
package app.athome.place.ui

import androidx.lifecycle.*
import app.athome.core.database.entity.PlaceWithRecipients
import app.athome.core.repository.PlaceRepository
import javax.inject.Inject


class PlaceViewModel @Inject constructor(
    private val placeRepository: PlaceRepository
) : ViewModel() {

    companion object {
        private const val TAG = "PLACE_VIEW_MODEL"
    }

    private val id: MutableLiveData<Long?> = MutableLiveData()

    val placeWithRecipients: LiveData<PlaceWithRecipients?> =
        Transformations.switchMap(id) { getPlaceWithId(it) }

    fun setPlaceId(placeId: Long?) {
        id.value = placeId
    }

    private fun getPlaceWithId(placeId: Long?) = liveData {
        val data = placeId?.let { placeRepository.getPlaceWithRecipients(it) }
        emit(data)
    }
}

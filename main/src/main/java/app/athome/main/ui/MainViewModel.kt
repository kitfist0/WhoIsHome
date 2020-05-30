package app.athome.main.ui

import android.util.Log
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.athome.core.database.entity.Place
import app.athome.core.database.entity.Recipient
import app.athome.core.repository.PlaceRepository
import app.athome.core.repository.RecipientRepository
import app.athome.core.util.SingleLiveEvent
import app.athome.main.BuildConfig
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

class MainViewModel @Inject constructor(
    private val placeRepository: PlaceRepository,
    private val recipientRepository: RecipientRepository
) : ViewModel() {

    companion object {
        private const val tag = "MAIN_VIEW_MODEL"
    }

    val emptyListEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()

    val places = Transformations.map(placeRepository.getPlacesWithRecipients()) {
        if (it.isNullOrEmpty()) {
            emptyListEvent.value = true
        }
        return@map it
    }

    fun insertRandomPlaceWithRecipients() {
        viewModelScope.launch {
            val placeName = "Place#${Random.nextInt(1000)}"
            val latitude = Random.nextDouble(-90.00000, 90.00000)
            val longitude = Random.nextDouble(-180.00000, 180.00000)
            val place = Place(placeName, latitude, longitude)
            logging("insert place, $placeName")
            val placeId = placeRepository.insertPlace(place)
            val recipients: MutableList<Recipient> = mutableListOf()
            for (i in 1..Random.nextInt(2, 4)) {
                recipients.add(Recipient(
                    placeId,
                    "$placeName recipient $i",
                    "p${placeId}rec${i}box@gmail.com",
                    true
                ))
            }
            logging("insert recipients, size is ${recipients.size}")
            recipientRepository.insertRecipients(recipients)
        }
    }

    fun updatePlaceRecipient(recipient: Recipient) {
        viewModelScope.launch {
            recipientRepository.updateRecipient(recipient)
        }
    }

    private fun logging(message: String) {
        if (BuildConfig.DEBUG) Log.d(tag, message)
    }
}

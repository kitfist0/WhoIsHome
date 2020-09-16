package app.athome.core.database.entity

import androidx.room.Embedded
import androidx.room.Relation

data class PlaceWithRecipients(
    @Embedded
    val place: Place,
    @Relation(parentColumn = "id", entityColumn = "placeId")
    var recipients: List<Recipient>
)

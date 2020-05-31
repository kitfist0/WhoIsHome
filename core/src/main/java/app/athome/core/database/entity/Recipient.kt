package app.athome.core.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "recipients",
    indices = [Index(value = ["id", "placeId"])],
    foreignKeys = [ForeignKey(
        entity = Place::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("placeId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class Recipient(
    var placeId: Long,
    var name: String,
    var email: String,
    var notify: Boolean
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
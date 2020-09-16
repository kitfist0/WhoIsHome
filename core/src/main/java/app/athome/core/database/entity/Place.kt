package app.athome.core.database.entity

import androidx.room.*

@Entity(
    tableName = "places",
    indices = [Index(value = ["id"])]
)
data class Place(
    val title: String,
    val image: String,
    val lat: Double,
    val lon: Double
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    constructor(title: String, lat: Double, lon: Double):this(
        title, "", lat, lon
    )
}

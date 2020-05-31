package app.athome.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import app.athome.core.database.dao.PlaceDao
import app.athome.core.database.dao.RecipientDao
import app.athome.core.database.entity.Place
import app.athome.core.database.entity.Recipient

@Database(entities = [Place::class, Recipient::class], version = 1, exportSchema = false)
abstract class CoreDatabase : RoomDatabase() {

    abstract fun placeDao(): PlaceDao

    abstract fun recipientDao(): RecipientDao
}

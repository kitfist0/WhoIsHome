package app.athome.core.database.dao

import androidx.room.*
import app.athome.core.database.entity.Recipient

@Dao
interface RecipientDao {

    @Query("SELECT * FROM recipients")
    suspend fun getRecipients(): List<Recipient>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipients(recipient: List<Recipient>)

    @Update
    suspend fun updateRecipient(recipient: Recipient)

    @Delete
    suspend fun deleteRecipient(recipient: Recipient)
}

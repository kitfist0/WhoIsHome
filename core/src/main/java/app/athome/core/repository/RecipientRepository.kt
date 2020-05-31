package app.athome.core.repository

import androidx.lifecycle.LiveData
import app.athome.core.database.dao.RecipientDao
import app.athome.core.database.entity.Recipient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipientRepository @Inject constructor(
    private val dao: RecipientDao
) {

    fun getRecipients(): LiveData<List<Recipient>> = dao.getRecipients()

    suspend fun insertRecipients(recipients: List<Recipient>) = dao.insertRecipients(recipients)

    suspend fun updateRecipient(recipient: Recipient) = dao.updateRecipient(recipient)
}
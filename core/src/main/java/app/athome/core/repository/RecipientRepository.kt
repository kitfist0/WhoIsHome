package app.athome.core.repository

import app.athome.core.database.dao.RecipientDao
import app.athome.core.database.entity.Recipient
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipientRepository @Inject constructor(
    private val dao: RecipientDao
) {

    suspend fun getRecipients() = dao.getRecipients()

    suspend fun insertRecipients(recipients: List<Recipient>) = dao.insertRecipients(recipients)

    suspend fun updateRecipient(recipient: Recipient) = dao.updateRecipient(recipient)
}

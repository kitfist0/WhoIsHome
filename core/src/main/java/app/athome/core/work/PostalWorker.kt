package app.athome.core.work

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.work.*
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.services.gmail.Gmail
import com.google.api.services.gmail.model.Message
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.util.*
import javax.mail.Message.RecipientType
import javax.mail.MessagingException
import javax.mail.Session
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class PostalWorker(
    context: Context,
    private val params: WorkerParameters,
    private val credential: GoogleAccountCredential,
    private val gmail: Gmail
) : CoroutineWorker(context, params) {

    companion object {
        private const val TAG = "POSTAL_WORKER"
        private const val RECIPIENT_ADDRESSES_KEY = "addresses"
        private const val TEXT_MESSAGE_KEY = "text_message"
        private const val ME = "me"

        fun schedule(context: Context, recipientAddresses: List<String>, textMessage: String) {
            Log.d(TAG, "schedule")
            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
            val inputData = Data.Builder()
                .putStringArray(RECIPIENT_ADDRESSES_KEY, recipientAddresses.toTypedArray())
                .putString(TEXT_MESSAGE_KEY, textMessage)
                .build()
            val workRequest = OneTimeWorkRequest.Builder(PostalWorker::class.java)
                .setConstraints(constraints)
                .setInputData(inputData)
                .build()
            WorkManager.getInstance(context).enqueue(workRequest)
        }

        private fun getEmailContent(from: String, recipients: Array<String>, text: String) =
            MimeMessage(Session.getDefaultInstance(Properties())).apply {
                setFrom(InternetAddress(from))
                addRecipients(RecipientType.TO, recipients.map { InternetAddress(it) }.toTypedArray())
                setText(text)
            }

        /**
         * Create a message from an email.
         *
         * @param emailContent Email to be set to raw of message
         * @return a message containing a base64url encoded email
         * @throws IOException
         * @throws MessagingException
         */
        private fun createMessageWithEmail(emailContent: MimeMessage): Message {
            val buffer = ByteArrayOutputStream()
            emailContent.writeTo(buffer)
            return Message().apply {
                raw = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    Base64.getUrlEncoder().encodeToString(buffer.toByteArray())
                } else {
                    android.util.Base64.encodeToString(
                        buffer.toByteArray(),
                        android.util.Base64.URL_SAFE
                    )
                }
            }
        }
    }

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            try {
                val content = getEmailContent(
                    requireNotNull(credential.selectedAccountName),
                    requireNotNull(params.inputData.getStringArray(RECIPIENT_ADDRESSES_KEY)),
                    requireNotNull(params.inputData.getString(TEXT_MESSAGE_KEY))
                )
                val message = createMessageWithEmail(content)
                gmail.users().messages().send(ME, message).execute()
                Log.d(TAG, "SUCCESS")
                Result.success()
            } catch (e: Exception) {
                Log.d(TAG, "FAILURE, ${e.message}")
                Result.failure()
            }
        }
    }
}

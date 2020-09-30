package app.athome.core.util

import android.os.Build
import com.google.api.services.gmail.Gmail
import com.google.api.services.gmail.model.Message
import java.io.ByteArrayOutputStream
import java.util.*
import javax.mail.Session
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class Postman(
    private val selectedAccountName: String,
    private val gmail: Gmail
) {

    companion object {

        private const val ME = "me"

        private fun getEmailContent(selectedAccountName: String, userEmail: String, text: String) =
            MimeMessage(Session.getDefaultInstance(Properties())).apply {
                setFrom(InternetAddress(selectedAccountName))
                addRecipient(javax.mail.Message.RecipientType.TO, InternetAddress(userEmail))
                setText(text)
            }
    }

    /**
     * Send an email from the user's mailbox to its recipient.
     *
     * @param userId User's email address. The special value "me"
     * can be used to indicate the authenticated user.
     * @param text Email text message to be sent.
     * @return The sent message
     * @throws MessagingException
     * @throws IOException
     */
    fun sendEmail(userId: String, text: String): Message {
        val emailContent = getEmailContent(selectedAccountName, userId, text)
        val message = createMessageWithEmail(emailContent)
        return gmail.users().messages().send(ME, message).execute()
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
                android.util.Base64.encodeToString(buffer.toByteArray(), android.util.Base64.URL_SAFE)
            }
        }
    }
}

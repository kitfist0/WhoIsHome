package app.athome.util

import java.math.BigInteger
import java.security.MessageDigest

class HashUtil {

    fun stringToMd5(string: String) {
        val messageDigest: MessageDigest
        var digest = ByteArray(0)
        try {
            messageDigest = MessageDigest.getInstance("MD5")
            messageDigest.reset()
            messageDigest.update(string.toByteArray())
            digest = messageDigest.digest()
        } catch (e:java.security.NoSuchAlgorithmException) {
            e.printStackTrace()
        }

        val bigInt = BigInteger(1, digest)
        val md5Hex = StringBuilder(bigInt.toString(16))
        while (md5Hex.length < 32) {
            md5Hex.insert(0, "0")
        }
    }
}
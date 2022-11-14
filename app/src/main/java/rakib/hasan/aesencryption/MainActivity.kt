package rakib.hasan.aesencryption

import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

class MainActivity : AppCompatActivity() {
    private var _className: String = javaClass.simpleName

    private val SECRET_KEY = "aesEncryptionKey"
    private val INIT_VECTOR = "encryptionIntVec"

    var mainText = "THIS MESSAGE WILL BE ENCRYPTED!";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.v(_className, "main message : $mainText")

        val encryptedText = encrypt(mainText);
        Log.v(_className, "encrypt message : $encryptedText")

        val decryptedText = decrypt(encryptedText);
        Log.v(_className, "decrypted message : $decryptedText")

    }

    private fun encrypt(value: String): String? {
        try {
            val iv = IvParameterSpec(INIT_VECTOR.toByteArray(charset("UTF-8")))
            val skeySpec = SecretKeySpec(SECRET_KEY.toByteArray(charset("UTF-8")), "AES")
            val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv)
            val encrypted = cipher.doFinal(value.toByteArray())
            return Base64.encodeToString(encrypted, Base64.DEFAULT)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return null
    }

    private fun decrypt(value: String?): String? {
        try {
            val iv = IvParameterSpec(INIT_VECTOR.toByteArray(charset("UTF-8")))
            val skeySpec = SecretKeySpec(SECRET_KEY.toByteArray(charset("UTF-8")), "AES")
            val cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING")
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv)
            val original = cipher.doFinal(Base64.decode(value, Base64.DEFAULT))
            return String(original)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return null
    }

}
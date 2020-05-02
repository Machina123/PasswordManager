package net.machina.passman.security

import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

abstract class Enigma(password: String, salt: ByteArray? = null, iv: ByteArray? = null) {
    internal var salt: ByteArray
    internal var iv: ByteArray
    internal val encryptAlgorithm = "AES/CBC/PKCS5Padding"

    protected val persistor = EnigmaParameterPersistor

    private lateinit var key: SecretKeySpec
    private val keyFactoryAlgorithm = "PBKDF2WithHmacSHA256"
    private val keyAlgorithm = "AES"
    private val iterations = 65536
    private val keyLength = 256

    init {
        if (salt != null && iv != null) {
            this.salt = salt
            this.iv = iv
        } else {
            this.salt = ByteArray(8)
            this.iv = ByteArray(16)
            val random = SecureRandom()
            random.nextBytes(this.salt)
            random.nextBytes(this.iv)
        }
        if (persistor.salt == null) persistor.salt = this.salt
        if (persistor.iv == null) persistor.iv = this.iv
        generateKey(password)
    }

    private fun generateKey(password: String) {
        this.salt = persistor.salt!!
        val keyFactory = SecretKeyFactory.getInstance(keyFactoryAlgorithm)
        val keySpec = PBEKeySpec(password.toCharArray(), salt, iterations, keyLength)
        val secret = keyFactory.generateSecret(keySpec)
        val secretSpec = SecretKeySpec(secret.encoded, keyAlgorithm)
        if (persistor.key == null) persistor.key = secretSpec
        this.key = persistor.key!!
    }

    abstract fun translate(input: ByteArray): ByteArray

}


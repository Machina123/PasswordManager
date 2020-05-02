package net.machina.passman.security

import java.security.GeneralSecurityException
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec

class EnigmaEncryptor(password: String, salt: ByteArray? = null, iv: ByteArray? = null) : Enigma(password, salt, iv) {

    @Throws(GeneralSecurityException::class)
    override fun translate(input: ByteArray): ByteArray {
        val cipher = Cipher.getInstance(encryptAlgorithm)
        cipher.init(Cipher.ENCRYPT_MODE, persistor.key!!, IvParameterSpec(persistor.iv!!))
        return cipher.doFinal(input)
    }
}
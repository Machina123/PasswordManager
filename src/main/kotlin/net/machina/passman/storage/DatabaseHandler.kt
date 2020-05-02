package net.machina.passman.storage

import net.machina.passman.security.Enigma
import net.machina.passman.security.EnigmaDecryptor
import net.machina.passman.security.EnigmaEncryptor
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import java.nio.charset.StandardCharsets
import java.security.SecureRandom
import java.util.*
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

object DatabaseHandler {
    var dbPath : String? = null

    private var dbFileHandle: File? = null
    private var salt = ByteArray(8)
    private var iv = ByteArray(16)

    private lateinit var encryptor:Enigma
    private lateinit var decryptor:Enigma

    fun initHandler(password: String, path:String) {
        dbPath = path
        dbFileHandle = File(dbPath!!)
        if(!dbFileHandle!!.exists()) {
            encryptor = EnigmaEncryptor(password)
            salt = encryptor.salt
            iv = encryptor.iv
            decryptor = EnigmaDecryptor(password, salt, iv)
        } else {
            val fileStream = dbFileHandle!!.inputStream()
            val fileData = String(fileStream.readBytes()).split("$")
            salt = Base64.getDecoder().decode(fileData[0])
            iv = Base64.getDecoder().decode(fileData[1])
            fileStream.close()
            encryptor = EnigmaEncryptor(password, salt, iv)
            decryptor = EnigmaDecryptor(password, salt, iv)
        }
    }

    fun read() : String {
        val fileStream = dbFileHandle!!.inputStream()
        val fileBase64Contents = String(fileStream.readBytes()).split("$")[2]
        val fileBinContents = Base64.getDecoder().decode(fileBase64Contents.toByteArray())
        var decrypted: ByteArray? = null
        try {
            decrypted = decryptor.translate(fileBinContents)
        } catch (e : Exception) {
            e.printStackTrace()
        }
        return String(decrypted!!)
    }

    fun write(data:String) {
        if(!dbFileHandle!!.exists()) dbFileHandle!!.createNewFile()
        val fileStream = FileOutputStream(dbFileHandle!!, false)
        val encrypted = encryptor.translate(data.toByteArray(StandardCharsets.UTF_8))
        fileStream.write(Base64.getEncoder().encode(salt))
        fileStream.write("$".toByteArray(StandardCharsets.UTF_8))
        fileStream.write(Base64.getEncoder().encode(iv))
        fileStream.write("$".toByteArray(StandardCharsets.UTF_8))
        fileStream.write(Base64.getEncoder().encode(encrypted))
        fileStream.flush()
        fileStream.close()
    }
}
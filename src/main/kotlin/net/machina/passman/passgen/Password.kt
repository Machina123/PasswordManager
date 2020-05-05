package net.machina.passman.passgen

import java.security.SecureRandom

class Password(val length: Int, val useLetters: Boolean, val useDigits: Boolean, val useSpecialChars: Boolean) {
    private var password: String

    init {
        password = generate()
    }

    override fun toString(): String {
        return password
    }

    fun generate() : String {
        var password = ""
        val random = SecureRandom()
        while(password.length < length) {
            when(random.nextInt(3)) {
                0 -> if(useLetters) password += lettersAlphabet[random.nextInt(lettersAlphabet.length)]
                1 -> if(useDigits) password += digitsAlphabet[random.nextInt(digitsAlphabet.length)]
                2 -> if(useSpecialChars) password += specialCharsAlphabet[random.nextInt(specialCharsAlphabet.length)]
            }
        }
        return password
    }

    companion object {
        val lettersAlphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val digitsAlphabet = "0123456789"
        val specialCharsAlphabet = "!@#$%^&*()-=_+[]{};:|,.<>?"
    }
}
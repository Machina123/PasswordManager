package net.machina.passman.passgen

class PasswordBuilder {
    var length: Int = 8
    var useDigits = false
    var useLetters = false
    var useSpecialChars = false

    fun build() : Password {
        return Password(length, useLetters, useDigits, useSpecialChars)
    }
}
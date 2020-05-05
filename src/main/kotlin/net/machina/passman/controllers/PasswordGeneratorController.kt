package net.machina.passman.controllers

import javafx.scene.control.Alert
import net.machina.passman.passgen.PasswordBuilder
import net.machina.passman.views.PasswordGeneratorFragment
import tornadofx.Controller
import tornadofx.alert

class PasswordGeneratorController : Controller() {
    var context: PasswordGeneratorFragment? = null

    fun generatePassword() {
        if(context != null) {
            val letters = context!!.lettersSelector.isSelected
            val digits = context!!.digitsSelector.isSelected
            val specialChars = context!!.specialCharsSelector.isSelected
            val length = Integer.parseInt(context!!.lengthField.text)

            if(length < 1 || (!letters && !digits && !specialChars)) {
                alert(Alert.AlertType.WARNING, "Warning", "Cannot generate password with supplied parameters!")
            } else {
                val builder = PasswordBuilder()
                builder.length = length
                builder.useDigits = digits
                builder.useLetters = letters
                builder.useSpecialChars = specialChars
                val pass = builder.build()
                context!!.outputField.text = pass.toString()
            }
        }
    }
}
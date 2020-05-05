package net.machina.passman.views

import javafx.scene.control.CheckBox
import javafx.scene.control.TextField
import javafx.scene.text.Font
import net.machina.passman.controllers.PasswordGeneratorController
import tornadofx.*
import tornadofx.Form

class PasswordGeneratorFragment : Fragment("Generate password") {
    val controller: PasswordGeneratorController by inject()
    var lengthField: TextField by singleAssign()
    var outputField: TextField by singleAssign()
    var digitsSelector: CheckBox by singleAssign()
    var lettersSelector: CheckBox by singleAssign()
    var specialCharsSelector: CheckBox by singleAssign()

    override val root = form {
        fieldset("Generate password") {
            field("Length") {
                lengthField = textfield {
                    text = "8"
                    filterInput { it.controlNewText.isInt() }
                }
            }
            field("Options") {
                lettersSelector = checkbox("Use letters")
                digitsSelector = checkbox("Use digits")
                specialCharsSelector = checkbox("Use special characters")
            }
            field("Output") {
                outputField = textfield {
                    font = Font.font(java.awt.Font.MONOSPACED)
                    isEditable = false
                }
            }
            button("Generate") {
                action {
                    controller.generatePassword()
                }
            }
        }
    }

    init {
        controller.context = this
    }
}

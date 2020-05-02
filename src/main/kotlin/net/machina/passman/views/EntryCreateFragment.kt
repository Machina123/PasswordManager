package net.machina.passman.views

import javafx.geometry.Orientation
import javafx.scene.control.PasswordField
import javafx.scene.control.TextField
import tornadofx.*

class EntryCreateFragment : Fragment("New entry") {
    var newName : TextField by singleAssign()
    var newLogin: TextField by singleAssign()
    var newPass: PasswordField by singleAssign()
    val context: EntriesFragment by param()

    override val root = form {
        fieldset("New entry") {
            field("Name", Orientation.HORIZONTAL) {
                newName = textfield()
            }
            field("Login", Orientation.HORIZONTAL) {
                newLogin = textfield()
            }
            field("Password", Orientation.HORIZONTAL) {
                newPass = passwordfield()
            }
            button("Save") {
                action {
                    context.controller.addEntry(newName.text, newLogin.text, newPass.text)
                    close()
                }
            }
        }
    }
}
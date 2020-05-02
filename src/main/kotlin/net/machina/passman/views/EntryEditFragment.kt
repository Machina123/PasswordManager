package net.machina.passman.views

import javafx.geometry.Orientation
import javafx.scene.control.PasswordField
import javafx.scene.control.TextField
import net.machina.passman.models.PasswordEntry
import tornadofx.*

class EntryEditFragment : Fragment("Edit") {
    val entry: PasswordEntry by param()
    val context: EntriesFragment by param()
    var newName : TextField by singleAssign()
    var newLogin: TextField by singleAssign()
    var newPass: PasswordField by singleAssign()

    override val root = form {
        fieldset("Edit entry") {
            field("Name", Orientation.HORIZONTAL) {
                newName = textfield(entry.name)
            }
            field("Login", Orientation.HORIZONTAL) {
                newLogin = textfield(entry.login)
            }
            field("Password", Orientation.HORIZONTAL) {
                newPass = passwordfield(entry.password)
            }
            button("Save") {
                action {
                    context.controller.modifyEntry(entry, newName.text, newLogin.text, newPass.text)
                    close()
                }
            }
        }
    }
}

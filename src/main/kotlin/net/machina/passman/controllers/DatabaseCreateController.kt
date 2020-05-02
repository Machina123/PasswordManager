package net.machina.passman.controllers

import javafx.scene.control.Alert
import javafx.stage.FileChooser.ExtensionFilter
import net.machina.passman.storage.DatabaseHandler
import tornadofx.Controller
import tornadofx.alert
import tornadofx.chooseDirectory
import java.io.File

class DatabaseCreateController : Controller() {
    var dbFilePath : String = ""

    fun chooseDbLocation() {
        val dir = chooseDirectory("Choose database location")
        if(dir != null && dir.exists() && dir.isDirectory) dbFilePath = dir.absolutePath
    }

    fun createDb(name:String, password: String) : Boolean {
        when {
            dbFilePath.isEmpty() -> {
                alert(Alert.AlertType.WARNING, "Warning", "Please select database file first")
                return false
            }
            name.isEmpty() -> {
                alert(Alert.AlertType.WARNING, "Warning", "Database name not specified!")
                return false
            }
            password.isEmpty() -> {
                alert(Alert.AlertType.WARNING, "Warning", "Password cannot be blank")
                return false
            }
            else -> {
                val path = dbFilePath + File.separator + name + ".pmdb"
                DatabaseHandler.initHandler(password, path)
                return try {
                    DatabaseHandler.write("[]")
                    true
                } catch (e: Exception) {     // wrong password
                    alert(Alert.AlertType.ERROR, "Error", e.message)
                    false
                }
            }
        }

    }
}
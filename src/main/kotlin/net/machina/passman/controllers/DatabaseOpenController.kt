package net.machina.passman.controllers

import javafx.scene.control.Alert
import javafx.stage.FileChooser.ExtensionFilter
import net.machina.passman.storage.DatabaseHandler
import tornadofx.Controller
import tornadofx.alert
import tornadofx.chooseFile

class DatabaseOpenController : Controller() {
    var dbFilePath : String = ""
    var extFilter  = arrayOf(ExtensionFilter("PassMan database (*.pmdb)", "*.pmdb"))

    fun chooseDbFile() {
        val files = chooseFile("Choose existing database", filters = extFilter)
        if(files.isNotEmpty()) dbFilePath = files[0].absolutePath
    }

    fun openDb(password: String) : Boolean {
        if(dbFilePath.isEmpty()) {
            alert(Alert.AlertType.WARNING, "Warning", "Please select database file first")
            return false
        }

        DatabaseHandler.initHandler(password, dbFilePath)
        return try {
            DatabaseHandler.read()
            true
        } catch (e : Exception) {     // wrong password
            alert(Alert.AlertType.ERROR, "Error", "An error occurred during opening database: " + e.localizedMessage)
            false
        }
    }
}
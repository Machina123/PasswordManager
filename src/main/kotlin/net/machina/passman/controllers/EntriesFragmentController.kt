package net.machina.passman.controllers

import javafx.scene.control.Alert
import javafx.scene.control.ButtonBar
import javafx.scene.control.ButtonType
import kotlinx.serialization.builtins.list
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import net.machina.passman.models.PasswordEntry
import net.machina.passman.storage.DatabaseHandler
import net.machina.passman.views.EntriesFragment
import tornadofx.*


class EntriesFragmentController : Controller() {
    var context: EntriesFragment? = null
    var entryList = listOf<PasswordEntry>().toMutableList()
    var currentEntry: PasswordEntry? = null
        set(entry) {
            System.err.println("Setter for currentEntry called for object $entry")
            field = entry
            if(entry != null) {
                context!!.entryName.text = entry.name
                context!!.entryLogin.text = entry.login
                if (isPasswordDisplayed) context!!.entryPassword.text = entry.password
                else context!!.entryPassword.text = getDots(entry.password.length)
                context!!.editButton.isDisable = false
                context!!.deleteButton.isDisable = false
            }
        }
    var isPasswordDisplayed = false
        set(display) {
            if (currentEntry != null) {
                if (display) {
                    context!!.entryPassword.text = currentEntry!!.password
                } else if (!display) {
                    context!!.entryPassword.text = getDots(currentEntry!!.password.length)
                }
            }
            field = display
        }

    fun getEntries() {
        try {
            val dataJson = DatabaseHandler.read()
            val json = Json(JsonConfiguration.Stable)
            entryList = json.parse(PasswordEntry.serializer().list, dataJson).toMutableList()
            invalidateView()
            context!!.statusLabel.text = "Database successfully loaded"
            context!!.dbPathLabel.text = DatabaseHandler.dbPath
        } catch (e: Exception) {
            e.printStackTrace()
            alert(Alert.AlertType.ERROR, "Error", "An error occurred during data deserialization: " + e.message)
        }
    }

    fun saveDatabase(message: String = "Database successfully updated") {
        try {
            invalidateView()
            val json = Json(JsonConfiguration.Stable)
            val exported = json.stringify(PasswordEntry.serializer().list, entryList.toList())
            DatabaseHandler.write(exported)
            context!!.statusLabel.text = message
        } catch (e: Exception) {
            e.printStackTrace()
            alert(Alert.AlertType.ERROR, "Error", "An error occurred during database saving: " + e.message)
        }
    }

    fun modifyEntry(name: String, newName: String, login: String, password: String) {
        val element: PasswordEntry = entryList.first { it.name == name }
        modifyEntry(element, newName, login, password)
    }

    fun modifyEntry(obj: PasswordEntry, newName: String, login: String, password: String) {
        val checkExistingName = entryList.firstOrNull { it.name == newName }
        if (checkExistingName == null || obj.name == newName) {
            obj.name = newName
            obj.login = login
            obj.password = password
            saveDatabase("Entry successfully updated")
        } else {
            alert(Alert.AlertType.WARNING, "Warning", "Name needs to be unique")
        }
    }

    fun addEntry(name: String, login: String, password: String) {
        val checkExistingName = entryList.firstOrNull { it.name == name }
        if (checkExistingName == null) {
            val element = PasswordEntry(name, login, password)
            entryList.add(element)
            System.err.println("addEntry(): list length = " + entryList.size)
            saveDatabase("New entry successfully added")
        } else {
            alert(Alert.AlertType.WARNING, "Warning", "Name needs to be unique")
        }
    }

    fun deleteEntry(obj: PasswordEntry) {
        System.err.println("Deleting object $obj")
        System.err.println("Deletion successful? " + entryList.remove(obj))
        saveDatabase("Entry successfully deleted")
    }

    fun getDots(count: Int): String {
        val dotChar = "\u2022"
        var dotted = ""
        for (i in 1..count) {
            dotted += dotChar
        }
        return dotted
    }

    fun invalidateView() {
        System.err.println("invalidateView() called")
        currentEntry = null
        context!!.entryList.selectionModel.clearSelection()
        context!!.entryName.text = ""
        context!!.entryLogin.text = ""
        context!!.entryPassword.text = ""
        context!!.editButton.isDisable = true
        context!!.deleteButton.isDisable = true
        context!!.entryList.items = null
        context!!.entryList.refresh()
        context!!.entryList.items = entryList.asObservable()
        context!!.entryList.refresh()
    }
}
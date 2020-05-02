package net.machina.passman.viewmodels

import javafx.beans.property.SimpleStringProperty
import tornadofx.ViewModel

class DatabaseChooserModel: ViewModel() {
    val dbFilePath = bind {SimpleStringProperty()}
    val dbPassword = bind {SimpleStringProperty()}
}
package net.machina.passman.viewmodels

import javafx.beans.property.SimpleStringProperty
import tornadofx.ViewModel

class DatabaseCreatorModel : ViewModel() {
    val dbPath = bind { SimpleStringProperty() }
    val dbName = bind { SimpleStringProperty() }
    val dbPassword = bind { SimpleStringProperty() }
}
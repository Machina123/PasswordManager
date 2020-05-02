package net.machina.passman.views

import javafx.geometry.Orientation
import net.machina.passman.controllers.DatabaseCreateController
import net.machina.passman.controllers.MainViewController
import net.machina.passman.viewmodels.DatabaseCreatorModel
import tornadofx.*
import java.util.*

class CreateDatabaseFragment : Fragment("Open existing database") {
    val controller: DatabaseCreateController by inject()
    val model = DatabaseCreatorModel()

    override val root = vbox {
        form {
            fieldset("Create new database", labelPosition = Orientation.VERTICAL) {
                field("Choose a directory") {
                    hbox(10) {
                        button("Choose...") {
                            action {
                                controller.chooseDbLocation()
                                model.dbPath.value = controller.dbFilePath
                            }
                        }

                        label(model.dbPath) {
                            prefWidth = 300.0
                            maxWidth = 300.0
                        }
                    }
                }
                field("Database name") {
                    textfield(model.dbName)
                }
                field("Password") {
                    passwordfield(model.dbPassword)
                }
            }
            fieldset{
                isFillWidth = true
                button("Open") {
                    action {
                        model.commit()
                        val res = controller.createDb(model.dbName.value ?: Date().time.toString(), model.dbPassword.value ?: "")
                        if(res) {
                            close()
                            find<MainViewController>().createDatabaseView()
                        }
                    }
                }
            }
        }
    }
}

package net.machina.passman.views

import javafx.geometry.Orientation
import net.machina.passman.controllers.DatabaseOpenController
import net.machina.passman.controllers.MainViewController
import net.machina.passman.viewmodels.DatabaseChooserModel
import tornadofx.*

class OpenDatabaseFragment : Fragment("Open existing database") {
    val controller: DatabaseOpenController by inject()
    val model = DatabaseChooserModel()

    override val root = vbox {
        form {
            fieldset("Open existing database", labelPosition = Orientation.VERTICAL) {
                hbox(10) {
                    button("Choose...") {
                        action {
                            controller.chooseDbFile()
                            model.dbFilePath.value = controller.dbFilePath
                        }
                    }

                    label(model.dbFilePath) {
                        prefWidth = 300.0
                        maxWidth = 300.0
                    }
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
                        val res = controller.openDb(model.dbPassword.value ?: "")
                        if(res) {
                            find<MainViewController>().createDatabaseView()
                            close()
                        }
                    }
                }
            }
        }
    }
}

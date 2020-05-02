package net.machina.passman.views

import net.machina.passman.controllers.MainViewController
import tornadofx.*

class MainView : View("PasswordManager") {
    val controller: MainViewController by inject()

    override val root = borderpane {
        paddingAll = 8.0
        top {
            hbox {
                spacing = 8.0
                paddingBottom = 8.0
                button("New database") {
                    action {
                        openInternalWindow<CreateDatabaseFragment>()
                    }
                }
                button("Open database") {
                    action {
                        openInternalWindow<OpenDatabaseFragment>()
                    }
                }
            }
        }

        center {
            prefHeight = 600.0
            prefWidth = 800.0
        }
    }
}

package net.machina.passman.controllers

import net.machina.passman.views.EntriesFragment
import net.machina.passman.views.MainView
import tornadofx.Controller

class MainViewController : Controller() {
    fun createDatabaseView() {
        find<MainView>().root.center = EntriesFragment().root
    }
}
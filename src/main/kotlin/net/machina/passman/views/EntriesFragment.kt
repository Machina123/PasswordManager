package net.machina.passman.views

import javafx.geometry.Orientation
import javafx.scene.control.*
import javafx.scene.input.Clipboard
import javafx.scene.layout.Priority
import javafx.scene.text.Font
import net.machina.passman.controllers.EntriesFragmentController
import net.machina.passman.models.PasswordEntry
import tornadofx.*

class EntriesFragment : Fragment("My View") {
    val controller: EntriesFragmentController by inject()

    var entryName: Label by singleAssign()
    var entryLogin: Label by singleAssign()
    var entryPassword: Label by singleAssign()
    var editButton: Button by singleAssign()
    var deleteButton: Button by singleAssign()
    var entryList: ListView<PasswordEntry> by singleAssign()
    var statusLabel: Label by singleAssign()
    var dbPathLabel: Label by singleAssign()

    override val root = borderpane {
        center {
            maxWidth = Double.MAX_VALUE
            hgrow = Priority.ALWAYS
            vgrow = Priority.ALWAYS
            entryList = listview(controller.entryList.asObservable()) {
                onUserSelect { controller.currentEntry = it }
            }
        }
        right {
            form {
                maxWidth = 450.0
                prefWidth = 450.0
                fieldset("Details") {
                    field("Name", Orientation.HORIZONTAL) {
                        entryName = label {
                            hgrow = Priority.ALWAYS
                            font = Font.font(java.awt.Font.MONOSPACED)
                        }
                    }
                    field("Login", Orientation.HORIZONTAL) {
                        entryLogin = label {
                            hgrow = Priority.ALWAYS
                            font = Font.font(java.awt.Font.MONOSPACED)
                        }
                    }
                    field("Password", Orientation.HORIZONTAL) {
                        entryPassword = label {
                            hgrow = Priority.ALWAYS
                            maxWidth = Double.MAX_VALUE
                            font = Font.font(java.awt.Font.MONOSPACED)
                        }
                        togglebutton("Show") {
                            isSelected = false
                            prefWidth = 60.0
                            action {
                                if (isSelected) {
                                    text = "Hide"
                                    System.err.println("Showing password")
                                } else {
                                    text = "Show"
                                    System.err.println("Hiding password")
                                }
                                controller.isPasswordDisplayed = isSelected
                            }
                        }
                        button("Copy") {
                            prefWidth = 60.0
                            action {
                                System.err.println("Copying to clipboard")
                                Clipboard.getSystemClipboard().putString(controller.currentEntry!!.password)
                                statusLabel.text = "Password copied to clipboard"
                            }
                        }
                    }
                }
                fieldset("Actions") {
                    hgrow = Priority.ALWAYS
                    spacing = 8.0
                    button("Password generator") {
                        maxWidth = Double.MAX_VALUE
                        hgrow = Priority.ALWAYS
                        action {
                            find<PasswordGeneratorFragment>().openWindow()
                        }
                    }
                    button("New entry") {
                        maxWidth = Double.MAX_VALUE
                        hgrow = Priority.ALWAYS
                        action {
                            find<EntryCreateFragment>(
                                mapOf(
                                    EntryCreateFragment::context to this@EntriesFragment
                                )
                            ).openWindow()
                        }
                    }
                    editButton = button("Edit selected") {
                        maxWidth = Double.MAX_VALUE
                        hgrow = Priority.ALWAYS
                        isDisable = true
                        action {
                            find<EntryEditFragment>(
                                mapOf(
                                    EntryEditFragment::entry to controller.currentEntry,
                                    EntryEditFragment::context to this@EntriesFragment
                                )
                            ).openWindow()
                        }
                    }
                    deleteButton = button("Delete selected") {
                        maxWidth = Double.MAX_VALUE
                        hgrow = Priority.ALWAYS
                        isDisable = true
                        action {
                            controller.deleteEntry(controller.currentEntry!!)
                        }
                    }
                    label("Double-click on entry to select")
                }
                fieldset("Database") {
                    dbPathLabel = label()
                }
            }
        }
        bottom {
            hbox {
                statusLabel = label("PasswordManager ready")
            }
        }
    }

    init {
        controller.context = this
        controller.getEntries()
    }
}


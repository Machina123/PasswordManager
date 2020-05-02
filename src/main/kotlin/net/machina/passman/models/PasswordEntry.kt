package net.machina.passman.models

import kotlinx.serialization.Serializable

@Serializable
data class PasswordEntry(var name:String, var login:String, var password:String) {
    override fun toString(): String = name
}
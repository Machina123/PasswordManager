package net.machina.passman.security

import javax.crypto.spec.SecretKeySpec

object EnigmaParameterPersistor {
    internal var salt : ByteArray? = null
    internal var iv : ByteArray? = null
    internal var key : SecretKeySpec? = null
}
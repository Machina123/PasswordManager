package net.machina.passman.storage

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.io.File
import java.util.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class DatabaseHandlerTest {

    var testPath = "E:\\passman-test"
    var testFilename = ""
    var password = ""
    var testData = "Testowe dane Zażółć Gęślą Jaźń"

    @BeforeAll
    fun setUp() {
        testFilename = "db-" + Date().time + ".pmdb"
        password = UUID.randomUUID().toString().substring(0,8)
        assert(testFilename.length > 8)
    }

    @Test
    fun testEncryption() {
        DatabaseHandler.initHandler(password, testPath + File.separator + testFilename)
        DatabaseHandler.write(testData)
        val res = DatabaseHandler.read()
        System.err.println(res + " " + res.length)
        assert(res == testData)
    }
}
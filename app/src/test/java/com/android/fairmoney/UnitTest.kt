package com.android.fairmoney

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@ExperimentalCoroutinesApi
class UnitTest {

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @Test
    fun testUnitConversion(){
        val expected  = "Kolawole Adewale"
        val fullName = StringBuilder()
                .append("Kolawole")
                .append(" ")
                .append("Adewale").toString()
        assertEquals(expected, fullName)
    }
}
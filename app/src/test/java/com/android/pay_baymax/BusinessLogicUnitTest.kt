package com.android.pay_baymax

import com.android.pay_baymax.business.BusinessLogic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import java.lang.Math.round
import kotlin.math.floor
import kotlin.math.roundToInt

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@ExperimentalCoroutinesApi
class BusinessLogicUnitTest {

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    @Test
    fun testUnitConversion() = runBlocking {
        val result  = BusinessLogic().convert(100.0,156.85,410.0)
        val expected  = 38
        assertEquals(expected, result.roundToInt())
    }

    @Test
    fun testUnitConversionUnSecure() {
        val result  = BusinessLogic().convertUnSecure(100.0,156.85,410.0)
        val expected  = 38
        assertEquals(expected, result.roundToInt())
    }

    @Test
    fun testUnitConversionLocal() = runBlocking {
        val result  = BusinessLogic().convertLocal(100.0,156.85,410.0)
        val expected  = 0
        assertEquals(expected, result.roundToInt())
    }
}
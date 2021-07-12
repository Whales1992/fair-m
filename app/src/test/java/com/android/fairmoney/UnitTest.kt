package com.android.fairmoney

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@ExperimentalCoroutinesApi
class BusinessLogicUnitTest {

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()
//
//    @Test
//    fun testUnitConversion() = runBlocking {
//        val result  = BusinessLogic().convert(100.0,156.85,410.0)
//        val expected  = 38
//        assertEquals(expected, result.roundToInt())
//    }
//
//    @Test
//    fun testUnitConversionUnSecure() {
//        val result  = BusinessLogic().convertUnSecure(100.0,156.85,410.0)
//        val expected  = 38
//        assertEquals(expected, result.roundToInt())
//    }
//
//    @Test
//    fun testUnitConversionLocal() = runBlocking {
//        val result  = BusinessLogic().convertLocal(100.0,156.85,410.0)
//        val expected  = 0
//        assertEquals(expected, result.roundToInt())
//    }
}
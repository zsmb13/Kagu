package co.zsmb.weblib.core.dom

import co.zsmb.weblib.core.util.Date
import kotlin.test.Test
import kotlin.test.assertEquals

class DateTest {

    //region Test objects
    val nativeDate = js("new Date(1234567890000)")
    val date = Date(1234567890000L)
    //endregion

    //region Getters
    @Test
    fun testGetTime() {
        assertEquals(nativeDate.getTime(), date.getTime(), "")
    }

    @Test
    fun testGetDate() {
        assertEquals(nativeDate.getDate(), date.getDate(), "getDate didn't match")
    }

    @Test
    fun testGetDay() {
        assertEquals(nativeDate.getDay(), date.getDay(), "getDay didn't match")
    }

    @Test
    fun testGetFullYear() {
        assertEquals(nativeDate.getFullYear(), date.getFullYear(), "getFullYear didn't match")
    }

    @Test
    fun testGetHours() {
        assertEquals(nativeDate.getHours(), date.getHours(), "getHours didn't match")
    }

    @Test
    fun testGetMilliseconds() {
        assertEquals(nativeDate.getMilliseconds(), date.getMilliseconds(), "getMilliseconds didn't match")
    }

    @Test
    fun testGetMinutes() {
        assertEquals(nativeDate.getMinutes(), date.getMinutes(), "getMinutes didn't match")
    }

    @Test
    fun testGetMonth() {
        assertEquals(nativeDate.getMonth(), date.getMonth(), "getMonth didn't match")
    }

    @Test
    fun testGetSeconds() {
        assertEquals(nativeDate.getSeconds(), date.getSeconds(), "getSeconds didn't match")
    }
    //endregion

    //region UTC getters
    @Test
    fun testGetUTCDate() {
        assertEquals(nativeDate.getUTCDate(), date.getUTCDate(), "getUTCDate didn't match")
    }

    @Test
    fun testGetUTCDay() {
        assertEquals(nativeDate.getUTCDay(), date.getUTCDay(), "getUTCDay didn't match")
    }

    @Test
    fun testGetUTCFullYear() {
        assertEquals(nativeDate.getUTCFullYear(), date.getUTCFullYear(), "getUTCFullYear didn't match")
    }

    @Test
    fun testGetUTCHours() {
        assertEquals(nativeDate.getUTCHours(), date.getUTCHours(), "getUTCHours didn't match")
    }

    @Test
    fun testGetUTCMilliseconds() {
        assertEquals(nativeDate.getUTCMilliseconds(), date.getUTCMilliseconds(), "getUTCMilliseconds didn't match")
    }

    @Test
    fun testGetUTCMinutes() {
        assertEquals(nativeDate.getUTCMinutes(), date.getUTCMinutes(), "getUTCMinutes didn't match")
    }

    @Test
    fun testGetUTCMonth() {
        assertEquals(nativeDate.getUTCMonth(), date.getUTCMonth(), "getUTCMonth didn't match")
    }

    @Test
    fun testGetUTCSeconds() {
        assertEquals(nativeDate.getUTCSeconds(), date.getUTCSeconds(), "getUTCSeconds didn't match")
    }
    //endregion

    //region Setters
    @Test
    fun testSetDate() {
        assertEquals(nativeDate.setDate(10), date.setDate(10), "setDate didn't match")
        assertEquals(nativeDate.setDate(20), date.setDate(20), "setDate didn't match")
    }

    @Test
    fun testSetFullYear() {
        assertEquals(nativeDate.setFullYear(1900), date.setFullYear(1900), "setFullYear didn't match")
        assertEquals(nativeDate.setFullYear(2000), date.setFullYear(2000), "setFullYear didn't match")
    }

    @Test
    fun testSetHours() {
        assertEquals(nativeDate.setHours(10), date.setHours(10), "setHours didn't match")
        assertEquals(nativeDate.setHours(20), date.setHours(20), "setHours didn't match")
    }

    @Test
    fun testSetMilliseconds() {
        assertEquals(nativeDate.setMilliseconds(111), date.setMilliseconds(111), "setMilliseconds didn't match")
        assertEquals(nativeDate.setMilliseconds(222), date.setMilliseconds(222), "setMilliseconds didn't match")
    }

    @Test
    fun testSetMinutes() {
        assertEquals(nativeDate.setMinutes(10), date.setMinutes(10), "setMinutes didn't match")
        assertEquals(nativeDate.setMinutes(20), date.setMinutes(20), "setMinutes didn't match")
    }

    @Test
    fun testSetMonth() {
        assertEquals(nativeDate.setMonth(1), date.setMonth(1), "setMonth didn't match")
        assertEquals(nativeDate.setMonth(2), date.setMonth(2), "setMonth didn't match")
    }

    @Test
    fun testSetSeconds() {
        assertEquals(nativeDate.setSeconds(10), date.setSeconds(10), "setSeconds didn't match")
        assertEquals(nativeDate.setSeconds(20), date.setSeconds(20), "setSeconds didn't match")
    }

    @Test
    fun testSetTime() {
        assertEquals(nativeDate.setTime(987654321), date.setTime(987654321), "setTime didn't match")
        assertEquals(nativeDate.setTime(123456789), date.setTime(123456789), "setTime didn't match")
    }
    //endregion

    //region UTC setters
    @Test
    fun testSetUTCDate() {
        assertEquals(nativeDate.setUTCDate(10), date.setUTCDate(10), "setUTCDate didn't match")
        assertEquals(nativeDate.setUTCDate(20), date.setUTCDate(20), "setUTCDate didn't match")
    }

    @Test
    fun testSetUTCFullYear() {
        assertEquals(nativeDate.setUTCFullYear(1900), date.setUTCFullYear(1900), "setUTCFullYear didn't match")
        assertEquals(nativeDate.setUTCFullYear(2000), date.setUTCFullYear(2000), "setUTCFullYear didn't match")
    }

    @Test
    fun testSetUTCHours() {
        assertEquals(nativeDate.setUTCHours(10), date.setUTCHours(10), "setUTCHours didn't match")
        assertEquals(nativeDate.setUTCHours(20), date.setUTCHours(20), "setUTCHours didn't match")
    }

    @Test
    fun testSetUTCMilliseconds() {
        assertEquals(nativeDate.setUTCMilliseconds(100), date.setUTCMilliseconds(100), "setUTCMilliseconds didn't match")
        assertEquals(nativeDate.setUTCMilliseconds(200), date.setUTCMilliseconds(200), "setUTCMilliseconds didn't match")
    }

    @Test
    fun testSetUTCMinutes() {
        assertEquals(nativeDate.setUTCMinutes(10), date.setUTCMinutes(10), "setUTCMinutes didn't match")
        assertEquals(nativeDate.setUTCMinutes(20), date.setUTCMinutes(20), "setUTCMinutes didn't match")
    }

    @Test
    fun testSetUTCMonth() {
        assertEquals(nativeDate.setUTCMonth(1), date.setUTCMonth(1), "setUTCMonth didn't match")
        assertEquals(nativeDate.setUTCMonth(2), date.setUTCMonth(2), "setUTCMonth didn't match")
    }

    @Test
    fun testSetUTCSeconds() {
        assertEquals(nativeDate.setUTCSeconds(10), date.setUTCSeconds(10), "setUTCSeconds didn't match")
        assertEquals(nativeDate.setUTCSeconds(20), date.setUTCSeconds(20), "setUTCSeconds didn't match")
    }
    //endregion

    //region Conversion getters
    @Test
    fun testToDateString() {
        assertEquals(nativeDate.toDateString(), date.toDateString(), "toDateString didn't match")
        assertEquals(nativeDate.toDateString(), date.toDateString(), "toDateString didn't match")
    }

    @Test
    fun testToISOString() {
        assertEquals(nativeDate.toISOString(), date.toISOString(), "toISOString didn't match")
        assertEquals(nativeDate.toISOString(), date.toISOString(), "toISOString didn't match")
    }

    @Test
    fun testToJSON() {
        assertEquals(nativeDate.toJSON(), date.toJSON(), "toJSON didn't match")
    }

    @Test
    fun testToGMTString() {
        assertEquals(nativeDate.toGMTString(), date.toGMTString(), "toGMTString didn't match")
    }

    @Test
    fun testToLocaleDateString() {
        assertEquals(nativeDate.toLocaleDateString(), date.toLocaleDateString(), "toLocaleDateString didn't match")
    }

    @Test
    fun testToLocaleString() {
        assertEquals(nativeDate.toLocaleString(), date.toLocaleString(), "toLocaleString didn't match")
    }

    @Test
    fun testToLocaleTimeString() {
        assertEquals(nativeDate.toLocaleTimeString(), date.toLocaleTimeString(), "toLocaleTimeString didn't match")
    }

    @Test
    fun testToString() {
        assertEquals(nativeDate.toString(), date.toString(), "toString didn't match")
    }

    @Test
    fun testToTimeString() {
        assertEquals(nativeDate.toTimeString(), date.toTimeString(), "toTimeString didn't match")
    }

    @Test
    fun testToUTCString() {
        assertEquals(nativeDate.toUTCString(), date.toUTCString(), "toUTCString didn't match")
    }

    @Test
    fun testValueOf() {
        assertEquals(nativeDate.valueOf(), date.valueOf(), "valueOf didn't match")
    }
    //endregion

}

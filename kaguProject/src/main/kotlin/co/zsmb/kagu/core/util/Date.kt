package co.zsmb.kagu.core.util

external class Date {

    constructor()

    constructor(value: Long)

    constructor(dateString: String)

    constructor(
            year: Int,
            month: Int,
            date: Int,
            hours: Int = definedExternally,
            minutes: Int = definedExternally,
            seconds: Int = definedExternally,
            milliseconds: Int? = definedExternally)

    // getters
    fun getTime(): Double

    fun getDate(): String
    fun getDay(): String
    fun getFullYear(): String
    fun getHours(): String
    fun getMilliseconds(): String
    fun getMinutes(): String
    fun getMonth(): String
    fun getSeconds(): String

    fun getTimezoneOffset(): String
    fun getUTCDate(): String
    fun getUTCDay(): String
    fun getUTCFullYear(): String
    fun getUTCHours(): String
    fun getUTCMilliseconds(): String
    fun getUTCMinutes(): String
    fun getUTCMonth(): String
    fun getUTCSeconds(): String

    // setters
    fun setDate(dayValue: Int): Long

    fun setFullYear(yearValue: Int,
                    monthValue: Int = definedExternally,
                    dayValue: Int = definedExternally): Long

    fun setHours(hoursValue: Int,
                 minutesValue: Int = definedExternally,
                 secondsValue: Int = definedExternally,
                 msValue: Int = definedExternally): Long

    fun setMilliseconds(millisecondsValue: Int): Long
    fun setMinutes(minutesValue: Int,
                   secondsValue: Int = definedExternally,
                   msValue: Int = definedExternally): Long

    fun setMonth(monthValue: Int,
                 dayValue: Int = definedExternally): Long

    fun setSeconds(secondsValue: Int,
                   msValue: Int = definedExternally): Long

    fun setTime(timeValueMs: Long): Long
    fun setUTCDate(dayValue: Int): Long
    fun setUTCFullYear(yearValue: Int,
                       monthValue: Int = definedExternally,
                       dayValue: Int = definedExternally): Long

    fun setUTCHours(hoursValue: Int,
                    minutesValue: Int = definedExternally,
                    secondsValue: Int = definedExternally,
                    msValue: Int = definedExternally): Long

    fun setUTCMilliseconds(millisecondsValue: Int): Long
    fun setUTCMinutes(minutesValue: Int,
                      secondsValue: Int = definedExternally,
                      msValue: Int = definedExternally): Long

    fun setUTCMonth(monthValue: Int,
                    dayValue: Int = definedExternally): Long

    fun setUTCSeconds(secondsValue: Int,
                      msValue: Int = definedExternally): Long

    // conversion getters
    fun toDateString(): String

    fun toISOString(): String
    fun toJSON(): String
    fun toGMTString(): String
    fun toLocaleDateString(): String
    fun toLocaleString(): String
    fun toLocaleTimeString(): String
    override fun toString(): String
    fun toTimeString(): String
    fun toUTCString(): String
    fun valueOf(): Long

    companion object {
        fun now(): Long
        fun parse(dateString: String): Date
    }

}

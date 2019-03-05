package com.shrikantbadwaik.searchtweets.domain.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    const val yyyyMMdd = "yyyy-MM-dd"
    const val eeee = "EEEE"

    fun stringToDate(pattern: String, dateString: String?): Date? {
        return try {
            val formatter = SimpleDateFormat(pattern, Locale.getDefault())
            formatter.parse(dateString)
        } catch (e: ParseException) {
            e.printStackTrace()
            null
        }
    }

    fun dateToString(pattern: String, date: Date?): String {
        val formatter = SimpleDateFormat(pattern, Locale.getDefault())
        return formatter.format(date)
    }
}
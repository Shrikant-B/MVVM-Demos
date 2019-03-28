package com.shrikantbadwaik.newsheadlines.domain.util

import android.text.format.DateUtils
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    const val yyyyMMddHHmmss = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    const val MMMddyyyy = "MMM dd. yyyy"

    fun stringToDate(pattern: String, dateString: String?): Date? {
        return try {
            val formatter = SimpleDateFormat(pattern, Locale.getDefault())
            formatter.parse(dateString)
        } catch (e: ParseException) {
            e.printStackTrace()
            null
        }
    }

    fun dateToString(pattern: String, date: Date?): String? {
        return date?.let {
            val formatter = SimpleDateFormat(pattern, Locale.getDefault())
            return formatter.format(it)
        }
    }

    fun durationAgo(date: Date?): String? {
        return date?.let {
            DateUtils.getRelativeTimeSpanString(
                it.time, System.currentTimeMillis(),
                DateUtils.MINUTE_IN_MILLIS, DateUtils.FORMAT_ABBREV_RELATIVE
            ).toString()
        }
    }
}
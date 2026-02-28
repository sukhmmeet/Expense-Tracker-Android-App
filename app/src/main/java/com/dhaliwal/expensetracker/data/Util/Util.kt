package com.dhaliwal.expensetracker.data.Util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Util {
    fun convertMillisToDate(
        millis: Long?,
        pattern: String = "dd MMM yyyy"
    ): String {

        if (millis == null) return ""

        val formatter = SimpleDateFormat(pattern, Locale.getDefault())
        return formatter.format(Date(millis))
    }
}
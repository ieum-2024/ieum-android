package com.jeongg.ieum.presentation._util

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import java.time.format.DateTimeFormatter


object DateConverter {

    @RequiresApi(Build.VERSION_CODES.O)
    fun stringToDateString(prev: String): String {
        try {
            val date = LocalDateTime.parse(prev)
                .toJavaLocalDateTime()
                .format(DateTimeFormatter.ISO_DATE) ?: ""
            return date
        } catch(e: Exception) {
            return ""
        }
    }

}
package com.rizqi.todo.presentation.task_list

import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
fun formatLongToDate(timestamp: Long): String {
    Log.d(TAG, "timestamp:${timestamp}")
    val timeStampString = DateTimeFormatter.ISO_INSTANT.format(Instant.ofEpochSecond(timestamp))
    val originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.ENGLISH)
    val targetFormat = SimpleDateFormat("EEE, d MMM yyyy HH:mm")
    val date = originalFormat.parse(timeStampString)
    val formattedDate = targetFormat.format(date);
    return formattedDate
}

fun getPercentageTask(){

}
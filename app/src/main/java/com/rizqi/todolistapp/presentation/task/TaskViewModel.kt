package com.rizqi.todolistapp.presentation.task

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rizqi.todolistapp.data.model.Subtask
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*

class TaskViewModel(
    date: String = "",
    subtask: List<Subtask> = emptyList()
): ViewModel() {
    private val _time = MutableLiveData(date)
    var time: LiveData<String> = _time
    @RequiresApi(Build.VERSION_CODES.O)
    private val _stringTime = MutableLiveData(if(date == "") "" else timeFormatter(date))
    @RequiresApi(Build.VERSION_CODES.O)
    var stringTime: LiveData<String> = _stringTime

    @RequiresApi(Build.VERSION_CODES.O)
    fun selectDateTime(context: Context) {
        var time = ""
        val currentDateTime = Calendar.getInstance()
        val startYear = currentDateTime.get(Calendar.YEAR)
        val startMonth = currentDateTime.get(Calendar.MONTH)
        val startDay = currentDateTime.get(Calendar.DAY_OF_MONTH)
        val startHour = currentDateTime.get(Calendar.HOUR_OF_DAY)
        val startMinute = currentDateTime.get(Calendar.MINUTE)

        DatePickerDialog(context, { _, year, month, day ->
            TimePickerDialog(context, { _, hour, minute ->
                val pickedDateTime = Calendar.getInstance()
                pickedDateTime.set(year, month, day, hour, minute)
                val monthStr: String
                if ((month + 1).toString().length == 1) {
                    monthStr = "0${month + 1}"
                } else {
                    monthStr = month.toString()
                }
                time = "$day-$monthStr-${year}T$hour:$minute"
                updateDateTime(time, timeFormatter(time))
            }, startHour, startMinute, true).show()
        }, startYear, startMonth, startDay).show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateDateTime(dateTime: String, stringTime: String) {
        _time.value = dateTime
        _stringTime.value = stringTime
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun timeFormatter(time: String): String{
        val formatter = DateTimeFormatter.ofPattern("d-MM-yyyy'T'H:mm")
        val dateTime = LocalDateTime.parse(time, formatter)
        val formatter2 = DateTimeFormatter.ofPattern("EEEE, MMM d");
        dateTime.format(formatter2)
        val timeString = "${dateTime.dayOfMonth} ${dateTime.month.getDisplayName(TextStyle.FULL_STANDALONE, Locale.ENGLISH)} ${dateTime.year}   ${dateTime.hour}:${dateTime.minute}"
        return timeString
    }
}
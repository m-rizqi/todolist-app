package com.rizqi.todolistapp.callback

import java.lang.Exception

interface UserDatabaseCallbackFailed {
    fun onCallback(exception: Exception)
}
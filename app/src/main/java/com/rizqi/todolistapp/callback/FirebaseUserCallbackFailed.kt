package com.rizqi.todolistapp.callback

import java.lang.Exception

interface FirebaseUserCallbackFailed {
    fun onCallback(exception: Exception)
}
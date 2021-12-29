package com.rizqi.todolistapp.callback

import java.lang.Exception

interface FirebaseAuthCallbackFailed {
    fun onCallback(exception: Exception)
}
package com.rizqi.todolistapp.callback

import com.rizqi.todolistapp.repository.model.User

interface FirebaseUserCallbackSuccess {
    fun onCallback(user: User)
}
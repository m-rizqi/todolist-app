package com.rizqi.todolistapp.callback

import com.rizqi.todolistapp.repository.model.User

interface FirebaseAuthCallbackSuccess {
    fun onCallback(user: User)
}
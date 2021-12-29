package com.rizqi.todolistapp.callback

import com.rizqi.todolistapp.repository.model.User

interface UserDatabaseCallbackSuccess {
    fun onCallback(data: User)
}
package com.rizqi.todolistapp.callback

import com.google.firebase.auth.FirebaseUser

interface FirebaseUserCallbackSuccess {
    fun onCallback(firebaseUser: FirebaseUser)
}
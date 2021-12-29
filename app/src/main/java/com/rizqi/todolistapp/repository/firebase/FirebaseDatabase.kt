package com.rizqi.todolistapp.repository.firebase

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.rizqi.todolistapp.repository.model.User

fun addNewUser(user: User, firebase: Firebase){
    val database = firebase.database.reference
    user.id?.let {
        database.child("users").child(it).setValue(user)
    }
}
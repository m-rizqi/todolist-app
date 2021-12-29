package com.rizqi.todolistapp.repository.firebase

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.rizqi.todolistapp.callback.UserDatabaseCallbackFailed
import com.rizqi.todolistapp.callback.UserDatabaseCallbackSuccess
import com.rizqi.todolistapp.repository.model.User

fun addNewUser(user: User, firebase: Firebase){
    val database = firebase.database.reference
    user.id?.let {
        database.child("users").child(it).setValue(user)
    }
}

fun getCurrentUser(id:String, firebase: Firebase, userDatabaseCallbackSuccess: UserDatabaseCallbackSuccess, userDatabaseCallbackFailed: UserDatabaseCallbackFailed){
    val database = firebase.database.reference
    val databaseListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val user = snapshot.getValue<User>()
            user?.let { userDatabaseCallbackSuccess.onCallback(it) }
        }
        override fun onCancelled(error: DatabaseError) {
            userDatabaseCallbackFailed.onCallback(error.toException())
        }
    }
    database.child(id).addValueEventListener(databaseListener)
}
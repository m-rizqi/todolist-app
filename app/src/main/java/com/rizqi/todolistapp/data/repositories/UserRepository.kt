package com.rizqi.todolistapp.data.repositories

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.rizqi.todolistapp.data.model.User
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository
@Inject
constructor(
    private val userList: DatabaseReference
    ){

    fun createNewUser(user: User){
        try {
            val id = userList.push().key?: UUID.randomUUID().toString()
            userList.child(id).setValue(
                User(id,user.name, user.email, user.photo, user.tasks)
            )
        }catch (e: Exception){
            Log.d(TAG,"failed:${e.message}")
            e.printStackTrace()
        }
    }
}
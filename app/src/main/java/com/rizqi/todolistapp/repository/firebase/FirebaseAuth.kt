package com.rizqi.todolistapp.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.rizqi.todolistapp.callback.FirebaseAuthCallbackFailed
import com.rizqi.todolistapp.callback.FirebaseAuthCallbackSuccess
import com.rizqi.todolistapp.repository.firebase.addNewUser
import com.rizqi.todolistapp.repository.model.User

fun registerEmailPassword(name: String, email: String, password: String, confirmPassword: String, firebase: Firebase, firebaseAuthCallbackSuccess: FirebaseAuthCallbackSuccess, firebaseAuthCallbackFailed: FirebaseAuthCallbackFailed){
    val auth = firebase.auth
    // validation
    val valid = (name.isNotBlank()) and (email.isNotBlank()) and (password.isNotBlank()) and (password == confirmPassword)
    if (valid){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{ task->
                if (task.isSuccessful){
                    val firebaseUser = auth.currentUser
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = User(
                        firebaseUser?.uid,
                        name,
                        email,
                        "https://firebasestorage.googleapis.com/v0/b/todolistapp-44316.appspot.com/o/default_user.png?alt=media&token=e98ea01f-c86e-402c-a3cb-09a07c3f304e",
                    )

                    //save to database
                    addNewUser(user, firebase)
                    firebaseAuthCallbackSuccess.onCallback(user)
                }else{
                    Log.d(TAG, "signInWithEmail:failure", task.exception)
                    task.exception?.let { firebaseAuthCallbackFailed.onCallback(exception = it) }
                }
            }
    }else{
        val exception = Exception("Please! fill form properly")
        firebaseAuthCallbackFailed.onCallback(exception = exception)
    }
}

fun loginEmailPassword(email: String, password:String, firebase: Firebase, firebaseAuthCallbackSuccess: FirebaseAuthCallbackSuccess, firebaseAuthCallbackFailed: FirebaseAuthCallbackFailed){
    val auth = firebase.auth
    // validation
    val valid = (email.isNotBlank()) and (password.isNotBlank())
    if(valid){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    Log.d(TAG, "signInWithEmail:success")
                    val firebaseUser = auth.currentUser
                    val user = User(
                        firebaseUser?.uid,
                        firebaseUser?.displayName,
                        firebaseUser?.email,
                        firebaseUser?.photoUrl.toString(),
                    )
                    firebaseAuthCallbackSuccess.onCallback(user)
                }else{
                    Log.d(TAG, "signInWithEmail:failure", task.exception)
                    task.exception?.let { firebaseAuthCallbackFailed.onCallback(exception = it) }
                }
            }
    }else{
        val exception = Exception("Email or Password Not Valid")
        firebaseAuthCallbackFailed.onCallback(exception = exception)
    }
}
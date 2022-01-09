package com.rizqi.todolistapp.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.rizqi.todolistapp.data.model.User
import com.rizqi.todolistapp.utils.LoadingState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthViewModel : ViewModel() {

    val loadingState = MutableStateFlow(LoadingState.IDLE)

    fun signInWithEmailAndPassword(email: String, password: String) = viewModelScope.launch {
        try {
            loadingState.emit(LoadingState.LOADING)
            Firebase.auth.signInWithEmailAndPassword(email, password).await()
            loadingState.emit(LoadingState.LOADED)
        } catch (e: Exception) {
            loadingState.emit(LoadingState.error(e.localizedMessage))
        }
    }

    fun signWithCredential(credential: AuthCredential) = viewModelScope.launch {
        try {
            loadingState.emit(LoadingState.LOADING)
            Firebase.auth.signInWithCredential(credential).await()
            loadingState.emit(LoadingState.LOADED)
        } catch (e: Exception) {
            loadingState.emit(LoadingState.error(e.localizedMessage))
        }
    }

    fun createUserWithEmailAndPassword(name:String, email: String, password: String) = viewModelScope.launch {
        try {
            loadingState.emit(LoadingState.LOADING)
            Firebase.auth.createUserWithEmailAndPassword(email, password).await()
            val userAuth = Firebase.auth.currentUser
            if(userAuth == null){
                loadingState.emit(LoadingState.error("Can't authenticate user"))
            }else{
                val newUser = User(userAuth.uid,name, email,
                    "https://firebasestorage.googleapis.com/v0/b/todolistapp-44316.appspot.com/o/default_user.png?alt=media&token=e98ea01f-c86e-402c-a3cb-09a07c3f304e",
                    emptyList())
                val database = Firebase.database.reference.child("users").child(newUser.id).setValue(newUser)
            }
            loadingState.emit(LoadingState.LOADED)
        }catch (e: Exception){
            loadingState.emit(LoadingState.error(e.localizedMessage))
        }
    }
}

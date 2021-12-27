package com.rizqi.todolist.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.rizqi.todolist.repository.DataStoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DataStoreViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = DataStoreRepository(application)

    val isLogin = repository.isLogin.asLiveData()

    fun setLogin(login : Boolean) = viewModelScope.launch(Dispatchers.IO){
        repository.setLogin(login)
    }

    val getUserId = repository.getUserId.asLiveData()

    fun setUserId(userId : String) = viewModelScope.launch(Dispatchers.IO){
        repository.setUserId(userId)
    }
}
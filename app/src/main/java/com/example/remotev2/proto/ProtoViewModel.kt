package com.example.remotev2.proto

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProtoViewModel(application: Application): AndroidViewModel(application) {

    private val repository = ProtoRepository(application)

    val keyUrl = repository.readProto.asLiveData()

    fun updateData(idTv: String) = viewModelScope.launch(Dispatchers.IO){
        repository.updateID(idTV = idTv)
    }
}
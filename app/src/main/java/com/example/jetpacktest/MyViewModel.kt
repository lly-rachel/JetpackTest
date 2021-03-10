package com.example.jetpacktest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {

    private val refreshLiveData = MutableLiveData<Any?>()

    val refreshResult = Transformations.switchMap(refreshLiveData){
       Repository.refresh()
    }

    fun refresh(){
        refreshLiveData.value = refreshLiveData.value
    }

}



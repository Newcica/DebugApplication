package com.example.myfirstapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    // Activity到Fragment的消息
    private val _activityMessage = MutableLiveData<String>()
    val activityMessage: LiveData<String> = _activityMessage
    
    // Fragment到Activity的消息
    private val _fragmentMessage = MutableLiveData<String>()
    val fragmentMessage: LiveData<String> = _fragmentMessage
    
    // Activity向Fragment发送消息
    fun sendActivityMessage(message: String) {
        _activityMessage.value = message
    }
    
    // Fragment向Activity发送消息
    fun sendFragmentMessage(message: String) {
        _fragmentMessage.value = message
    }
}
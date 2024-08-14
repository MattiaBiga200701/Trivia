package com.example.trivia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingsViewModel: ViewModel() {

    private val _themeState = MutableLiveData<Boolean>()
    val themeState: LiveData<Boolean> get() = _themeState

    private val _soundState = MutableLiveData<Boolean>()
    val soundState: LiveData<Boolean> get() = _soundState

    private val _notificationsState = MutableLiveData<Boolean>()
    val notificationsState: LiveData<Boolean> get() = _notificationsState


    fun setThemeState(themeState: Boolean){
        _themeState.postValue(themeState)
    }

    fun setSoundState(soundState: Boolean){
        _soundState.postValue(soundState)
    }

    fun setNotificationsState(notificationsState: Boolean){
        _notificationsState.postValue(notificationsState)
    }
}
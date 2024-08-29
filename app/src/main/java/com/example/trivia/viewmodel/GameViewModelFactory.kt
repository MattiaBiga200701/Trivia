package com.example.trivia.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GameViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameSessionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GameSessionViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
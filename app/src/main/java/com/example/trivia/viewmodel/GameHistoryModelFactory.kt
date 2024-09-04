package com.example.trivia.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.trivia.db.Repository

class GameHistoryModelFactory(private val rep: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameHistoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GameHistoryViewModel(rep) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

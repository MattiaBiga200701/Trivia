package com.example.trivia.viewmodel

import androidx.lifecycle.*
import com.example.trivia.db.GameHistory
import com.example.trivia.db.Repository
import kotlinx.coroutines.launch


class GameHistoryViewModel(private val rep: Repository) : ViewModel() {


    private val _gameHistoryList = MutableLiveData<List<GameHistory>>()
    val gameHistoryList: LiveData<List<GameHistory>> get() = _gameHistoryList


    fun insertGameHistory(score: Int, time: Long, category: String, difficulty: String, date: String) {
        val gameHistory = GameHistory(
            score = score,
            time = time,
            category = category,
            difficulty = difficulty,
            date = date
        )
        viewModelScope.launch {
            rep.insertGameHistory(gameHistory)
        }
    }
}
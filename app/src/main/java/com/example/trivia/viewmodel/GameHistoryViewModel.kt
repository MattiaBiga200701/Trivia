package com.example.trivia.viewmodel

import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.*
import com.example.trivia.db.GameHistory
import com.example.trivia.db.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class GameHistoryViewModel(private val rep: Repository) : ViewModel() {


    private val _gameHistoryList = MutableLiveData<List<GameHistory>>()
    val gameHistoryList: LiveData<List<GameHistory>> get() = _gameHistoryList


    fun insertGameHistory(newGameHistory: GameHistory) {
        viewModelScope.launch(Dispatchers.IO) {
            rep.insertGameHistory(newGameHistory)
            Log.e("Message", rep.getScore())

        }
    }


    fun loadAllGameHistory(){

        _gameHistoryList.postValue(rep.getAllGameHistory())

    }
}
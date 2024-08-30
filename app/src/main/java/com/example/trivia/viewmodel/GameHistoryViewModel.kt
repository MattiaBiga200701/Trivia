package com.example.trivia.viewmodel

import androidx.lifecycle.*
import com.example.trivia.db.GameHistory
import com.example.trivia.db.Repository



class GameHistoryViewModel(private val rep: Repository) : ViewModel() {


    private val _gameHistoryList = MutableLiveData<List<GameHistory>>()
    val gameHistoryList: LiveData<List<GameHistory>> get() = _gameHistoryList


    fun insertGameHistory(newGameHistory: GameHistory) {

        rep.insertGameHistory(newGameHistory)
    }


    fun loadAllGameHistory(){

        _gameHistoryList.postValue(rep.getAllGameHistory())

    }
}
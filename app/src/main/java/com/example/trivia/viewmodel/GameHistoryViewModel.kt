package com.example.trivia.viewmodel


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
        }
    }


    fun loadAllGameHistory() {
        viewModelScope.launch(Dispatchers.IO){
            _gameHistoryList.postValue(rep.getAllGameHistory())

        }

    }

    fun loadHistoryFilterByDate(date: String){
        viewModelScope.launch(Dispatchers.IO){
            _gameHistoryList.postValue(rep.getHistoryFilterByDate(date))
        }
    }

    fun loadHistoryFilterByCategory(category: String){
        viewModelScope.launch(Dispatchers.IO){
            _gameHistoryList.postValue(rep.getHistoryFilterByCategory(category))
        }
    }

    fun loadHistoryFilter(category: String, date: String){

        viewModelScope.launch(Dispatchers.IO){
            _gameHistoryList.postValue(rep.getHistoryFilter(category, date))
        }

    }
}
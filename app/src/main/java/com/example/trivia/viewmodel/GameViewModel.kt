package com.example.trivia.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trivia.logic.GameLogic
import com.example.trivia.model.entities.Question
import com.example.trivia.model.Repository


class GameViewModel: ViewModel() {


    private val _questions = MutableLiveData<List<Question>>()
    val questions: LiveData<List<Question>> get() = _questions

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int> get() = _score

    private val rep = Repository()

    init {
        _score.value = 0
    }

    fun loadQuestions(category: String, difficulty: String) {



        _isLoading.postValue(true)
        val categoryID = rep.getCategoryID(category)
        rep.fetchQuestions(categoryID, difficulty)
        val result = rep.getQuestions()
        _questions.postValue(result)
        _isLoading.postValue(false)
    }

    fun setScore(selectedAnswers: Map<Int, String?>) {
        val controller = GameLogic()
        controller.checkAnswers(selectedAnswers, rep.getQuestions())
        _score.postValue(controller.getScore())
    }
}


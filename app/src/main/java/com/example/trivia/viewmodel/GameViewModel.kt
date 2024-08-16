package com.example.trivia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trivia.model.entities.Question
import com.example.trivia.model.Repository


class GameViewModel: ViewModel() {


    private val _questions = MutableLiveData<List<Question>>()
    val questions: LiveData<List<Question>> get() = _questions

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int> get() = _score

    init {
        _score.value = 0
    }

    suspend fun loadQuestions(category: String, difficulty: String) {

        val rep = Repository()

        _isLoading.postValue(true)
        val categoryID = rep.getCategoryID(category)
        val result = rep.fetchQuestions(categoryID, difficulty)
        _questions.postValue(result)
        _isLoading.postValue(false)
    }

    fun checkAnswers(selectedAnswers: Map<Int, String?>) {
        var correctAnswers = 0
        _questions.value?.forEachIndexed { index, question ->
            if (selectedAnswers[index] == question.correctAnswer) {
                correctAnswers++
            }
        }
        _score.value = correctAnswers
    }
}


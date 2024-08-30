package com.example.trivia.viewmodel



import android.content.Context
import android.media.MediaPlayer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trivia.logic.GameLogic
import com.example.trivia.db.Question
import com.example.trivia.db.Repository
import com.example.trivia.db.TriviaDB


class GameSessionViewModel(context: Context): ViewModel(){


    private val _questions = MutableLiveData<List<Question>>()
    val questions: LiveData<List<Question>> get() = _questions

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int> get() = _score

    private val rep : Repository

    private var mediaPlayer: MediaPlayer? = null

    init {

        _score.value = 0

        val gameHistoryDao = TriviaDB.getInstance(context).gameHistoryDao()

        rep = Repository(gameHistoryDao)
    }

     fun loadQuestions(context: Context, category: String, difficulty: String) {
        _isLoading.postValue(true)
        val categoryID = rep.getCategoryID(category)
        rep.fetchQuestions(context, categoryID, difficulty, this)


    }

    fun onQuestionFetched(questions: List<Question>){
        _questions.postValue(questions)
        _isLoading.postValue(false)
    }

    fun setScore(selectedAnswers: Map<Int, String?>) {
        val controller = GameLogic()
        controller.checkAnswers(selectedAnswers, rep.getQuestions())
        _score.postValue(controller.getScore())
    }

    fun playSound(context: Context, soundResId: Int) {
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(context, soundResId)
        mediaPlayer?.setOnCompletionListener {
            it.release()
            mediaPlayer = null
        }
        mediaPlayer?.start()
    }

    override fun onCleared() {
        super.onCleared()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    fun stopSound() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}


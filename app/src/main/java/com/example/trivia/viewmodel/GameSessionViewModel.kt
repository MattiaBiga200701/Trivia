package com.example.trivia.viewmodel



import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trivia.db.GameHistory
import com.example.trivia.logic.GameLogic
import com.example.trivia.db.Question
import com.example.trivia.db.Repository
import com.example.trivia.logic.SimpleTimer
import java.time.LocalDate
import java.time.format.DateTimeFormatter



class GameSessionViewModel(repository: Repository): ViewModel(){


    private val _questions = MutableLiveData<List<Question>>()
    val questions: LiveData<List<Question>> get() = _questions

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int> get() = _score

    private val selectedAnswers = mutableMapOf<Int, String?>()

    private var rep: Repository

    private var mediaPlayer: MediaPlayer? = null

    private val timer = SimpleTimer()



    init {

        _score.value = 0
        rep = repository
    }

    fun getTimer(): SimpleTimer{
        return this.timer
    }


    private fun getCurrentDateAsString(): String {
        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return currentDate.format(formatter)
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

    fun setScore() {
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

    fun updateAnswer(questionIndex: Int, answer: String?) {
        selectedAnswers[questionIndex] = answer
    }


    fun setGameHistory(){
        val gameHistoryViewModel = GameHistoryViewModel(this.rep)
        val category = _questions.value?.get(0)?.category
        val difficulty =_questions.value?.get(0)?.difficulty
        Log.e("CHECK TIMER", this.timer.getElapsedTimeFormatted())
        val newGameHistory = GameHistory(_score.value ?: 0, this.timer.getElapsedTimeFormatted(), category, difficulty, this.getCurrentDateAsString() )
        gameHistoryViewModel.insertGameHistory(newGameHistory)
    }

}


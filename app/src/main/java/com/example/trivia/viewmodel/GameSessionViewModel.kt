package com.example.trivia.viewmodel



import android.content.Context
import android.media.MediaPlayer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.trivia.db.GameHistory
import com.example.trivia.logic.GameLogic
import com.example.trivia.db.Question
import com.example.trivia.db.Repository
import com.example.trivia.db.TriviaDB
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.concurrent.TimeUnit


class GameSessionViewModel(context: Context): ViewModel(){


    private val _questions = MutableLiveData<List<Question>>()
    val questions: LiveData<List<Question>> get() = _questions

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int> get() = _score

    private val selectedAnswers = mutableMapOf<Int, String?>()


    private val rep : Repository

    private var mediaPlayer: MediaPlayer? = null

    private var startTime: Long = 0
    private var totalTime: Long = 0
    private var isTimerRunning: Boolean = false

    init {

        _score.value = 0

        val gameHistoryDao = TriviaDB.getInstance(context).gameHistoryDao()

        rep = Repository(gameHistoryDao)
    }

    fun startTimer() {
        if (!isTimerRunning) {
            startTime = System.currentTimeMillis()
            isTimerRunning = true
        }
    }

    fun stopTimer() {
        if (isTimerRunning) {
            totalTime += System.currentTimeMillis() - startTime
            isTimerRunning = false
        }
    }

    fun resetTimer(){
        totalTime = 0
        startTime = 0
        isTimerRunning = false
    }



    private fun getTotalTime(): String {
        val totalMillis = totalTime + if (isTimerRunning) System.currentTimeMillis() - startTime else 0

        val hours = TimeUnit.MILLISECONDS.toHours(totalMillis)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(totalMillis) % 60
        val seconds = TimeUnit.MILLISECONDS.toSeconds(totalMillis) % 60

        return String.format(
            Locale.US,
            "%02d:%02d:%02d",
            hours,
            minutes,
            seconds

        )
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
        val newGameHistory = GameHistory(_score.value ?: 0, this.getTotalTime(), category, difficulty, this.getCurrentDateAsString() )
        gameHistoryViewModel.insertGameHistory(newGameHistory)
    }

}


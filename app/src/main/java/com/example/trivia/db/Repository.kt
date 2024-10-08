package com.example.trivia.db

import android.content.Context

import com.android.volley.Request
import com.android.volley.RequestQueue

import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.trivia.viewmodel.GameSessionViewModel

import org.json.JSONObject
import org.jsoup.Jsoup


class Repository(private val gameHistoryDao: GameHistoryDao) {

    private var questions: List<Question> = emptyList()

     fun fetchQuestions(context: Context, categoryID: String, difficulty: String, viewModel: GameSessionViewModel): List<Question>{

            val url = "https://opentdb.com/api.php?amount=10&category=$categoryID&difficulty=$difficulty&type=multiple"
            val requestQueue: RequestQueue = Volley.newRequestQueue(context)


            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET, url, null,
                { response: JSONObject ->
                    val resultsArray = response.getJSONArray("results")
                    val questionList = mutableListOf<Question>()

                    for (i in 0 until resultsArray.length()) {

                        val questionObject = resultsArray.getJSONObject(i)
                        val options = mutableListOf<String>()
                        val correctAnswer = Jsoup.parse(questionObject.getString("correct_answer")).text()
                        val incorrectAnswers = questionObject.getJSONArray("incorrect_answers")

                            for (j in 0 until incorrectAnswers.length()) {
                                options.add(Jsoup.parse(incorrectAnswers.getString(j)).text())
                            }
                            options.add(correctAnswer)
                            options.shuffle()

                        val question = Question(
                            category = Jsoup.parse(questionObject.getString("category")).text(),
                            difficulty = Jsoup.parse(questionObject.getString("difficulty")).text(),
                            question = Jsoup.parse(questionObject.getString("question")).text(),
                            options = options,
                            correctAnswer = correctAnswer
                        )
                        questionList.add(question)
                    }


                    this.questions = questionList
                    viewModel.onQuestionFetched(this.questions)
                },
                { error ->
                    error.printStackTrace()
                }
            )

            requestQueue.add(jsonObjectRequest)

            return this.questions
    }


    fun getQuestions(): List<Question>{
        return this.questions
    }

    fun insertGameHistory(gameHistory: GameHistory) {
        gameHistoryDao.insert(gameHistory)
    }


    fun getAllGameHistory(): List<GameHistory> {
        return gameHistoryDao.getAllHistory()
    }

    fun getHistoryFilterByDate(date: String): List<GameHistory> {
        return gameHistoryDao.getHistoryFilterByDate(date)
    }

    fun getHistoryFilterByCategory(category: String): List<GameHistory> {
        return gameHistoryDao.getHistoryFilterByCategory(category)
    }

    fun getHistoryFilter(category: String, date: String): List<GameHistory> {
        return gameHistoryDao.getHistoryFilter(category, date)
    }




}


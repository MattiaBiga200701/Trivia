package com.example.trivia.utils

import android.text.Html
import android.util.Log
import com.example.trivia.persistence.Question
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL

class Repository {

    suspend fun fetchQuestions(categoryID: String, difficulty: String): List<Question> {
        return withContext(Dispatchers.IO) {
            try {
                val url = "https://opentdb.com/api.php?amount=10&category=$categoryID&difficulty=$difficulty&type=multiple"
                val response = URL(url).readText()
                val json = JSONObject(response)
                val questions = mutableListOf<Question>()
                val results = json.getJSONArray("results")
                for (i in 0 until results.length()) {
                    val questionObj = results.getJSONObject(i)
                    val question = Html.fromHtml(questionObj.getString("question"), Html.FROM_HTML_MODE_LEGACY).toString()
                    val correctAnswer = Html.fromHtml(questionObj.getString("correct_answer"), Html.FROM_HTML_MODE_LEGACY).toString()
                    val incorrectAnswers = questionObj.getJSONArray("incorrect_answers")

                    val options = mutableListOf<String>()
                    for (j in 0 until incorrectAnswers.length()) {
                        options.add(Html.fromHtml(incorrectAnswers.getString(j), Html.FROM_HTML_MODE_LEGACY).toString())
                    }
                    options.add(correctAnswer)
                    options.shuffle()

                    questions.add(Question(difficulty, categoryID, question, options, correctAnswer))
                }
                questions
            } catch (e: Exception) {
                Log.e("FetchQuestions", "Error fetching questions")
                emptyList()
            }
        }
    }




    fun getCategoryID(category: String): String {
        return when (category) {
            "General Knowledge" -> "9"
            "Vehicles" -> "28"
            "Sports" -> "21"
            "Entertainment: Video Games" -> "15"
            "Entertainment: Film" -> "11"
            "Science & Nature" -> "17"
            "Celebrities" -> "26"
            "Animals" -> "27"
            "Entertainment: Books" -> "10"
            "Entertainment: Music" -> "12"
            "Entertainment: Musical & Theatres" -> "13"
            "Entertainment: Television" -> "14"
            "Entertainment: Board Games" -> "16"
            "Science: Computers" -> "18"
            "Science: Mathematics" -> "19"
            "Mythology" -> "20"
            "History" -> "23"
            "Geography" -> "22"
            "Politics" -> "24"
            "Art" -> "25"
            "Entertainment: Comics" -> "29"
            "Entertainment: Cartoon & Animations" -> "32"
            "Entertainment: Japanese Anime & Manga" -> "31"
            "Science: Gadgets" -> "30"
            else -> "9" // Default to "General Knowledge"
        }
    }
}
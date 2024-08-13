package com.example.trivia.persistence

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Question")
data class Question(

    var difficulty: String,
    var category: String,
    var question: String,
    var options: List<String>,
    var correctAnswer: String

){
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
package com.example.trivia.persistence

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Question")
data class Questions(

    var difficulty: String,
    var category: String,
    var question: String,
    var correctAnswer: String,

){
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
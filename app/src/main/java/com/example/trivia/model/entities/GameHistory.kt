package com.example.trivia.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "History")
data class GameHistory(

    var gameDate: String,
    var gameCategory: String,
    var gameDifficulty: String,

    var correctAnswer: Int,

    var answerTime: Int

){
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
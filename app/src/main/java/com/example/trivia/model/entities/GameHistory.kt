package com.example.trivia.model.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_scores")
data class GameScore(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val score: Int,
    val time: Long,
    val category: String,
    val difficulty: String,
    val date: Long = System.currentTimeMillis()
)
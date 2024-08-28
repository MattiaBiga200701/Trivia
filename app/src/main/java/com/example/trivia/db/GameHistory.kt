package com.example.trivia.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_history")
data class GameHistory(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val score: Int,
    val time: Long,
    val category: String,
    val difficulty: String,
    val date: String
)
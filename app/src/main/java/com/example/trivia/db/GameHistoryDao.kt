package com.example.trivia.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface GameHistoryDao {
    @Insert
    fun insert(gameHistory: GameHistory)

    @Query("SELECT * FROM game_history ORDER BY date DESC")
    fun getAllHistory(): List<GameHistory>

    @Query("SELECT category FROM game_history")
    fun getScore(): String
}
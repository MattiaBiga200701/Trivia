package com.example.trivia.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface GameHistoryDao {
    @Insert
    suspend fun insert(gameHistory: GameHistory)

    @Query("SELECT * FROM game_history ORDER BY date DESC")
    suspend fun getAllHistory(): List<GameHistory>
}
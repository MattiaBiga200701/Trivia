package com.example.trivia.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.trivia.model.entities.GameScore

@Dao
interface GameScoreDao {
    @Insert
    suspend fun insert(gameScore: GameScore)

    @Query("SELECT * FROM game_scores ORDER BY date DESC")
    suspend fun getAllScores(): List<GameScore>
}
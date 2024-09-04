package com.example.trivia.db

import androidx.room.Dao

import androidx.room.Insert
import androidx.room.Query


@Dao
interface GameHistoryDao {
    @Insert
    fun insert(gameHistory: GameHistory)

    @Query("""
        SELECT * FROM game_history
        """)
    fun getAllHistory(): List<GameHistory>

    /* Query di test
    @Query("""DELETE FROM game_history""")
    fun deleteAllHistory()
    */


    @Query(""" SELECT * FROM game_history WHERE category = :categoryFilter""")
    fun getHistoryFilterByCategory(categoryFilter: String): List<GameHistory>

    @Query("""SELECT * FROM game_history WHERE date = :dateFilter""")
    fun getHistoryFilterByDate(dateFilter: String): List<GameHistory>

    @Query("""SELECT * FROM game_history WHERE date = :dateFilter AND category = :categoryFilter""")
    fun getHistoryFilter(categoryFilter: String, dateFilter: String): List<GameHistory>
}
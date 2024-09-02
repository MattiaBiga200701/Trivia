package com.example.trivia.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game_history")
data class GameHistory(

    @ColumnInfo(name = "score")
    val score: Int,

    @ColumnInfo(name = "time")
    val time: String,

    @ColumnInfo(name = "category")
    val category: String?,

    @ColumnInfo(name = "difficulty")
    val difficulty: String?,

    @ColumnInfo(name = "date")
    val date: String
){
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
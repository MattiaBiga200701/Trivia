package com.example.trivia.model

import androidx.room.Database
import com.example.trivia.model.entities.GameHistory

@Database(entities = [GameHistory::class] , version = 1)
abstract class TriviaDB {
}
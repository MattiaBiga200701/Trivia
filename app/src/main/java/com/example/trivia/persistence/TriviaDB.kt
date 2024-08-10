package com.example.trivia.persistence

import androidx.room.Database

@Database(entities = [GameHistory::class] , version = 1)
abstract class TriviaDB {
}
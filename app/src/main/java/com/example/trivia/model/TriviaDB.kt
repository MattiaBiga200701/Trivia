package com.example.trivia.model

import android.content.Context
import androidx.room.Database
//import androidx.room.Room
//import androidx.room.RoomDatabase
import com.example.trivia.model.entities.GameScore
/*
@Database(entities = [GameScore::class], version = 1)
abstract class DbGameResult : RoomDatabase() {
    abstract fun gameResultDao(): DaoGameResult

    companion object {
        @Volatile
        private var INSTANCE: DbGameResult? = null

        fun getInstance(context: Context): DbGameResult {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DbGameResult::class.java,
                    "gameResultsDb.db"
                )
                    .createFromAsset("gameResultsDb.db")
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

 */
package com.example.trivia.db

import android.content.Context
import androidx.room.Database

import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [GameHistory::class], version = 1)
abstract class TriviaDB : RoomDatabase() {
    abstract fun gameHistoryDao(): GameHistoryDao

    companion object {
        @Volatile
        private var INSTANCE: TriviaDB? = null

        fun getInstance(context: Context): TriviaDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TriviaDB::class.java,
                    "TriviaAppDB.db"
                )
                    .fallbackToDestructiveMigration()
                    .createFromAsset("TriviaAppDB.db")
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}


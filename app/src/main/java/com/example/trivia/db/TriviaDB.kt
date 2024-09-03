package com.example.trivia.db

import android.content.Context
import androidx.room.Database


import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase


@Database(entities = [GameHistory::class], version = 1)
abstract class TriviaDB : RoomDatabase() {
    abstract fun gameHistoryDao(): GameHistoryDao

    companion object {
        private var db: TriviaDB? = null

        fun getInstance(context: Context): TriviaDB {
            if (db == null) {
                db = databaseBuilder(
                    context,
                    TriviaDB::class.java,
                    "TriviaData.db"
                )
                    .createFromAsset("TriviaData.db")
                    .build()
            }
            return db as TriviaDB

        }
    }
}


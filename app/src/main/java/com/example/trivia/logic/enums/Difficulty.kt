package com.example.trivia.logic.enums

enum class Difficulty(val id: String) {

    EASY("easy"),
    MEDIUM("medium"),
    HARD("hard");


    companion object {
        fun fromString(id: String): Difficulty? {
            return entries.find { it.id == id }
        }
    }

}
package com.example.trivia.logic

import com.example.trivia.db.Question

class GameLogic {

    private var score = 0

    fun getScore(): Int{
        return this.score
    }

    fun checkAnswers(selectedAnswers: Map<Int, String?>, questions: List<Question>){

        for(i in questions.indices){
            if(selectedAnswers[i] == questions[i].correctAnswer){
               this.score++
            }
        }

    }


}
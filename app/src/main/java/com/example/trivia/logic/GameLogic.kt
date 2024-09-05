package com.example.trivia.logic

import com.example.trivia.db.Question

class GameLogic {

    private var score = 0

    private val incorrectAnswers = mutableMapOf<Int, String?>()

    private val incorrectQuestions = mutableMapOf<Int, Question>()

    fun getScore(): Int{
        return this.score
    }

    fun getIncorrectAnswers(): Map<Int, String?>{
        return this.incorrectAnswers
    }

    fun getIncorrectQuestion(): Map<Int, Question>{
        return this.incorrectQuestions
    }

    fun checkAnswers(selectedAnswers: Map<Int, String?>, questions: List<Question>){

        for(i in questions.indices){
            if(selectedAnswers[i] == questions[i].correctAnswer){
               this.score++
            }
        }

    }

    fun createIncorrectList(answers: Map<Int, String?>, questions: List<Question>){
        for(i in questions.indices){
            if(answers[i] != questions[i].correctAnswer){
                this.incorrectAnswers[i] = answers[i]
                this.incorrectQuestions[i] = questions[i]
            }
        }
    }


}
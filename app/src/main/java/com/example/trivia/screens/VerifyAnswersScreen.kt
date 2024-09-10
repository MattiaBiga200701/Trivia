package com.example.trivia.screens

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.trivia.R
import com.example.trivia.logic.GameLogic
import com.example.trivia.ui.theme.iconSize
import com.example.trivia.ui.theme.mediumFontSize
import com.example.trivia.ui.theme.mediumPadding
import com.example.trivia.ui.theme.smallFontSize
import com.example.trivia.ui.theme.smallPadding
import com.example.trivia.viewmodel.GameSessionViewModel


@Composable
fun ErrorScreen(navController: NavController, viewModel: GameSessionViewModel) {

    val questions by viewModel.questions.observeAsState(initial = emptyList())
    val answers by viewModel.answers.observeAsState(initial = emptyMap())
    val context = LocalContext.current
    val isClicked = remember { mutableStateOf(false) }
    val colors=MaterialTheme.colorScheme
    val controller = GameLogic()

    controller.createIncorrectList(answers, questions)
    val incorrectAnswers = controller.getIncorrectAnswers()
    val incorrectQuestions = controller.getIncorrectQuestion()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(colors.background, colors.primary)
                )
            ), contentAlignment = Alignment.Center
    ) {


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = mediumPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(45.dp))

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {


                IconButton(
                    onClick = {
                        if (!isClicked.value) {
                            isClicked.value = true
                            navController.navigateUp()


                            Handler(Looper.getMainLooper()).postDelayed({
                                isClicked.value = false
                            }, 300)
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 2.dp)
                        .size(iconSize)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back to Homepage",
                        tint = colors.onBackground
                    )
                }


                Text(
                    text =context.getString(R.string.error_title),
                    color = colors.onBackground,
                    fontSize = mediumFontSize,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(start = 20.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = mediumPadding)
            ) {
                items(incorrectAnswers.keys.toList()) { i ->
                    IncorrectComposable(
                        question = incorrectQuestions[i]?.question,
                        incorrectAnswer = incorrectAnswers[i],
                        correctAnswer = incorrectQuestions[i]?.correctAnswer
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                }
            }
        }
    }
}

@Composable
    fun IncorrectComposable(question: String?, incorrectAnswer: String?, correctAnswer: String?) {

        val context= LocalContext.current
        val colors=MaterialTheme.colorScheme

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = smallPadding)
                .background(Color(0x80000000), shape = RoundedCornerShape(8.dp))
                .padding(mediumPadding)
        ) {
            Text(
                text = context.getString(R.string.question) + ": " + question,
                color = colors.onBackground,
                fontSize = smallFontSize,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = context.getString(R.string.answer_string) + ": " + incorrectAnswer,
                color = colors.error,
                fontSize = smallFontSize,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = context.getString(R.string.correct_answer_string) + ": " + correctAnswer,
                color = colors.primary,
                fontSize = smallFontSize,
                fontWeight = FontWeight.SemiBold
            )
        }
    }





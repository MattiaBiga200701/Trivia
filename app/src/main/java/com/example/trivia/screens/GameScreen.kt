package com.example.trivia.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.material3.CircularProgressIndicator

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight

import androidx.navigation.NavController
import com.example.trivia.ui.theme.MyBlack
import com.example.trivia.ui.theme.MyGreen

import com.example.trivia.ui.theme.fontSize

import com.example.trivia.viewmodel.GameSessionViewModel


@Composable
fun QuizScreen(
    navController: NavController,
    category: String,
    difficulty: String,
    viewModel: GameSessionViewModel
) {
    val questions by viewModel.questions.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(true)
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.loadQuestions(context, category, difficulty)
    }

    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(MyBlack, MyGreen)
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color.White)
        }
    } else if (questions.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(MyBlack, MyGreen)
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No questions available",
                fontSize = fontSize,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    } else {

        navController.navigate("question/$category/$difficulty/0")
    }
}

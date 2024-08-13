package com.example.trivia.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState

import androidx.compose.runtime.mutableStateMapOf

import androidx.compose.runtime.remember

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

import com.example.trivia.viewmodel.GameViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun QuizScreen(
               navController: NavController,
               category: String,
               difficulty: String,
               viewModel: GameViewModel = viewModel()
) {
    val questions by viewModel.questions.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(true)
    val selectedOption = remember { mutableStateMapOf<Int, String?>() }

    LaunchedEffect(category, difficulty) {

        viewModel.loadQuestions(category, difficulty)

    }

    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else if (questions.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "No questions available", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
    } else {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {
            questions.forEachIndexed { index, question ->
                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                    Text(text = question.question, fontSize = 18.sp, fontWeight = FontWeight.Bold)

                    question.options.forEach { option ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clickable {
                                    selectedOption[index] = option
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selectedOption[index] == option,
                                onClick = { selectedOption[index] = option }
                            )
                            Text(text = option, fontSize = 16.sp)
                        }
                    }
                }
            }
        }
    }
}
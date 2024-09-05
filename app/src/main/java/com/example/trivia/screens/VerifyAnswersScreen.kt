package com.example.trivia.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.trivia.R
import com.example.trivia.ui.theme.MyBlack
import com.example.trivia.ui.theme.MyGreen
import com.example.trivia.ui.theme.mediumFontSize
import com.example.trivia.ui.theme.mediumPadding
import com.example.trivia.viewmodel.GameSessionViewModel

@Composable
fun ErrorScreen(navController: NavController, viewModel: GameSessionViewModel) {

    val questions by viewModel.questions.observeAsState(initial = emptyList())
    val answers by viewModel.answers.observeAsState(initial = emptyMap())
    val context = LocalContext.current

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(MyBlack, MyGreen)
                )
            ), contentAlignment = Alignment.Center
    ) {
        val boxWidth = maxWidth
        val boxHeight = maxHeight

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = mediumPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(45.dp))

            Text(
                text = "Incorrect Answers",
                fontSize = mediumFontSize,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(15.dp))


        }


    }

}




@Composable
fun IncorrectComposable(){



}
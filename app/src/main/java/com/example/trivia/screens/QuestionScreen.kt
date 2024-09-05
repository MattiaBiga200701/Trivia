package com.example.trivia.screens


import androidx.compose.foundation.background

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.trivia.ui.theme.MyBlack
import com.example.trivia.ui.theme.MyGreen
import com.example.trivia.ui.theme.MyPink
import com.example.trivia.ui.theme.cornerRounding
import com.example.trivia.ui.theme.fontSize
import com.example.trivia.ui.theme.mediumPadding
import com.example.trivia.ui.theme.mediumSpace
import com.example.trivia.ui.theme.smallFontSize
import com.example.trivia.ui.theme.smallPadding
import com.example.trivia.ui.theme.smallSpace
import com.example.trivia.ui.theme.standardButton
import com.example.trivia.viewmodel.GameSessionViewModel
import kotlinx.coroutines.delay
import java.util.Locale

@Composable
fun QuestionScreen(
    navController: NavController,
    category: String,
    difficulty: String,
    questionIndex: Int,
    viewModel: GameSessionViewModel
) {
    val questions by viewModel.questions.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(true)
    val context = LocalContext.current


    val selectedOption = remember { mutableStateOf<String?>(null) }


    val timerDuration = 30
    val timeRemaining = remember { mutableIntStateOf(timerDuration) }

    LaunchedEffect(Unit) {
        while (timeRemaining.intValue > 0) {
            delay(1000L)
            timeRemaining.intValue -= 1
        }


        if (questionIndex + 1 < questions.size) {

            viewModel.getTimer().stop()
            navController.navigate("question/$category/$difficulty/${questionIndex + 1}")
        } else {

            viewModel.setScore()
            navController.navigate("end")
        }
    }
    val minutes = timeRemaining.intValue / 60
    val seconds = timeRemaining.intValue % 60
    val formattedTime = String.format(Locale.US, "%02d:%02d", minutes, seconds)

    if(questionIndex == 0) {
        LaunchedEffect(Unit) {

            viewModel.loadQuestions(context, category, difficulty)
        }
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
                text = context.getString(R.string.error_question),
                fontSize = fontSize,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    } else {

        viewModel.getTimer().start()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(MyBlack, MyGreen)
                    )
                )
                .padding(mediumPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {

                Spacer(modifier = Modifier.height(30.dp))

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = smallPadding)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.size(100.dp)
                    ) {
                        CircularProgressIndicator(
                            progress = { timeRemaining.intValue / timerDuration.toFloat() },
                            modifier = Modifier.size(100.dp),
                            strokeWidth = smallSpace,
                            color = Color.White
                        )
                        Text(
                            text = formattedTime,
                            fontSize = fontSize,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }



                Spacer(modifier = Modifier.height(mediumPadding))

                Text(
                    text = "Category: $category",
                    fontSize = fontSize,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = smallPadding)
                )

                Text(
                    text = "Difficulty: $difficulty",
                    fontSize = fontSize,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = mediumPadding)
                )

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = smallPadding),
                    shape = RoundedCornerShape(cornerRounding),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(mediumPadding)) {

                        Text(
                            text = "Question ${questionIndex + 1} of ${questions.size}",
                            fontSize = fontSize,
                            fontWeight = FontWeight.Bold,
                            color = Color.Gray
                        )

                        Spacer(modifier = Modifier.height(smallSpace))

                        Text(
                            text = questions[questionIndex].question,
                            fontSize = fontSize,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )

                        Spacer(modifier = Modifier.height(smallSpace))

                        questions[questionIndex].options.forEach { option ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = smallPadding)
                                    .clickable { selectedOption.value = option },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = selectedOption.value == option,
                                    onClick = { selectedOption.value = option },
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = Color(0xFF2196F3)
                                    )
                                )
                                Text(
                                    text = option,
                                    fontSize = smallFontSize,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.Black
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(mediumSpace))

                Button(
                    onClick = {
                        if (selectedOption.value != null) {
                            viewModel.updateAnswer(questionIndex, selectedOption.value)
                            if (questionIndex + 1 < questions.size) {
                                navController.navigate("question/${category}/${difficulty}/${questionIndex + 1}")
                            } else {
                                viewModel.getTimer().stop()
                                viewModel.setScore()
                                navController.navigate("end")
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MyPink),
                    shape = RoundedCornerShape(cornerRounding),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = smallPadding)
                        .height(standardButton)
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "End Quiz",
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(smallSpace))
                    Text(
                        text = if (questionIndex + 1 < questions.size) context.getString(R.string.next_button) else context.getString(R.string.end_quiz_button),
                        color = Color.White,
                        fontSize = fontSize
                    )
                }
            }
        }
    }
}

package com.example.trivia.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.trivia.exceptions.EmptyInputException
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
fun QuizScreen(
    navController: NavController,
    category: String,
    difficulty: String,
    viewModel: GameSessionViewModel
) {
    val questions by viewModel.questions.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(true)
    val selectedOption = remember { mutableStateMapOf<Int, String?>() }
    val context = LocalContext.current


    val timerDuration = 300
    val timeRemaining = remember { mutableIntStateOf(timerDuration) }

    LaunchedEffect(Unit){
        viewModel.loadQuestions(context, category, difficulty)
    }

    LaunchedEffect(Unit) {
        while (timeRemaining.intValue > 0) {
            delay(1000L)
            timeRemaining.intValue -= 1
        }

        //viewModel.setScore(selectedOption)
        navController.navigate("timeOver/${category}/${difficulty}")
    }

    val minutes = timeRemaining.intValue / 60
    val seconds = timeRemaining.intValue % 60
    val formattedTime = String.format(Locale.US,"%02d:%02d", minutes, seconds)


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
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.White)
            }
        } else if (questions.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
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
            Column(
                modifier = Modifier.fillMaxSize()
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = smallPadding)
                        .background(Color(0x80000000))
                        .border(1.dp, Color.White, RoundedCornerShape(8.dp))
                        .clip(RoundedCornerShape(8.dp))
                        .padding(horizontal = mediumPadding, vertical = smallPadding)
                ) {
                    Text(
                        text = "Time remaining: $formattedTime",
                        fontSize = fontSize,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .padding(mediumPadding)
                ) {
                    item {
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
                    }

                    items(questions.size) { index ->
                        val question = questions[index]
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
                                    text = question.question,
                                    fontSize = fontSize,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )

                                Spacer(modifier = Modifier.height(smallSpace))

                                question.options.forEach { option ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = smallPadding)
                                            .clickable {
                                                selectedOption[index] = option
                                            },
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        RadioButton(
                                            selected = selectedOption[index] == option,
                                            onClick = { selectedOption[index] = option },
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
                    }

                    item {
                        Spacer(modifier = Modifier.height(mediumSpace))
                        Button(
                            onClick = {
                                try {
                                    if (questions.size != selectedOption.size) {
                                        throw EmptyInputException("You must answer all questions")
                                    }
                                    viewModel.setScore(selectedOption)
                                    navController.navigate("end")
                                } catch (e: EmptyInputException) {
                                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
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
                            Text(text = "End Quiz", color = Color.White, fontSize = fontSize)
                        }
                    }
                }
            }
        }
    }
}

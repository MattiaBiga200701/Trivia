

package com.example.trivia.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.trivia.exceptions.EmptyInputException
import com.example.trivia.ui.theme.MyBlack
import com.example.trivia.ui.theme.MyCustomFont
import com.example.trivia.ui.theme.MyGreen
import com.example.trivia.ui.theme.MyPink
import com.example.trivia.ui.theme.bigSpace
import com.example.trivia.ui.theme.cornerRounding
import com.example.trivia.ui.theme.fontSize
import com.example.trivia.ui.theme.mediumPadding
import com.example.trivia.ui.theme.mediumSpace
import com.example.trivia.ui.theme.smallPadding
import com.example.trivia.ui.theme.smallSpace
import com.example.trivia.ui.theme.standardButton


@Composable
fun PlayScreen(navController: NavController) {

    var selectedCategory by remember { mutableStateOf("") }
    var selectedDifficulty by remember { mutableStateOf("") }

    val categories = listOf(
        "General Knowledge", "Vehicles", "Sports", "Entertainment: Video Games", "Entertainment: Film",
        "Science & Nature", "Celebrities", "Animals", "Entertainment: Books", "Entertainment: Music",
        "Entertainment: Musical & Theatres", "Entertainment: Television", "Entertainment: Board Games",
        "Science: Computers", "Science: Mathematics", "Mythology", "History", "Geography", "Politics",
        "Art", "Entertainment: Comics", "Entertainment: Cartoon & Animations",
        "Entertainment: Japanese Anime & Manga", "Science: Gadgets"
    )
    val difficulties = listOf("easy", "medium", "hard")
    val context = LocalContext.current


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(MyBlack, MyGreen)
                )
            )
            .padding(mediumPadding),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(mediumPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Select Category",
                fontSize = fontSize,
                fontWeight = FontWeight.Bold,
                fontFamily = MyCustomFont,
                color = Color.White,
                modifier = Modifier.padding(bottom = mediumPadding)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = smallPadding),
                shape = RoundedCornerShape(cornerRounding),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                MenuATendina(
                    options = categories,
                    selectedOption = selectedCategory,
                    onOptionSelected = { selectedCategory = it }
                )
            }

            Spacer(modifier = Modifier.height(mediumSpace))

            Text(
                text = "Select Difficulty",
                fontSize = fontSize,
                fontWeight = FontWeight.Bold,
                fontFamily = MyCustomFont,
                color = Color.White,
                modifier = Modifier.padding(bottom = mediumPadding)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = smallPadding),
                shape = RoundedCornerShape(cornerRounding),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                MenuATendina(
                    options = difficulties,
                    selectedOption = selectedDifficulty,
                    onOptionSelected = { selectedDifficulty = it }
                )
            }

            Spacer(modifier = Modifier.height(bigSpace))

            Button(

                onClick = {

                    try {

                        if (selectedCategory.isEmpty()) {
                            throw EmptyInputException("Select a Category First")
                        } else if (selectedDifficulty.isEmpty()) {
                            throw EmptyInputException("Select a Difficulty First")
                        }
                        navController.navigate("question/${selectedCategory}/${selectedDifficulty}/${0}")

                    }catch(e: EmptyInputException){
                        Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    }
                          },
                colors = ButtonDefaults.buttonColors(containerColor = MyPink),
                shape = RoundedCornerShape(cornerRounding),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(standardButton)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored. Filled. ArrowForward,
                    contentDescription = "Start Quiz",
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(smallSpace))
                Text(text = "Start Quiz", color = Color.White, fontSize = fontSize)
            }
        }
    }
}

@Composable
fun MenuATendina(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var opzioneSelezionata by remember { mutableStateOf(selectedOption) }

    Column(modifier = Modifier.padding(mediumPadding)) {

        Text(
            text = opzioneSelezionata.ifEmpty { "Select an option" },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
                .padding(mediumPadding),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onOptionSelected(option)
                        opzioneSelezionata = option
                        expanded = false
                    }
                )
            }
        }
    }
}

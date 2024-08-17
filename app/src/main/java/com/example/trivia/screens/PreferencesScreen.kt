package com.example.trivia.screens

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.trivia.ui.theme.MyCustomFont


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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF000000), Color(0xFF23FFCD))
                )
            )
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Select Category",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = MyCustomFont,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                MenuATendina(
                    options = categories,
                    selectedOption = selectedCategory,
                    onOptionSelected = { selectedCategory = it }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Select Difficulty",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = MyCustomFont,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                MenuATendina(
                    options = difficulties,
                    selectedOption = selectedDifficulty,
                    onOptionSelected = { selectedDifficulty = it }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { navController.navigate("quiz/${selectedCategory}/${selectedDifficulty}") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF231AA)),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(56.dp)
            ) {
                Text(text = "Start Quiz", color = Color.White, fontSize = 24.sp)
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

    Column(modifier = Modifier.padding(16.dp)) {

        Text(
            text = opzioneSelezionata.ifEmpty { "Select an option" },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
                .padding(16.dp),
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

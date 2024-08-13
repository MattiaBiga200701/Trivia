package com.example.trivia.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@Composable
fun PlayScreen(navController: NavController) {

    var selectedCategory by remember { mutableStateOf("") }
    var selectedDifficulty by remember { mutableStateOf("") }

    val categories = listOf(
        "General Knowledge",
        "Vehicles",
        "Sports",
        "Entertainment: Video Games",
        "Entertainment: Film",
        "Science & Nature",
        "Celebrities",
        "Animals",
        "Entertainment: Books",
        "Entertainment: Music",
        "Entertainment: Musical & Theatres",
        "Entertainment: Television",
        "Entertainment: Board Games",
        "Science: Computers",
        "Science: Mathematics",
        "Mythology",
        "History",
        "Geography",
        "Politics",
        "Art",
        "Entertainment: Comics",
        "Entertainment: Cartoon & Animations",
        "Entertainment: Japanese Anime & Manga",
        "Science: Gadgets"
    )
    val difficulties = listOf("easy", "medium", "hard")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "Category", fontSize = 24.sp, modifier = Modifier.padding(bottom = 8.dp))


        MenuATendina(
            options = categories,
            selectedOption = selectedCategory,
            onOptionSelected = { selectedCategory = it }
        )

        Text(text = "Difficulty", fontSize = 24.sp, modifier = Modifier.padding(bottom = 8.dp))



        MenuATendina(
            options = difficulties,
            selectedOption = selectedDifficulty,
            onOptionSelected = { selectedDifficulty = it }
        )
        Text(
            text = "Start quiz",
            fontSize = 24.sp,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .clickable {
                    navController.navigate("quiz/${selectedCategory}/${selectedDifficulty}")

                }
        )
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
package com.example.trivia

import android.os.Bundle
import android.util.Log

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.FlowColumnScopeInstance.align
//import androidx.compose.foundation.layout.ColumnScopeInstance.align
//import androidx.compose.foundation.layout.FlowRowScopeInstance.align
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController



import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL
import android.text.Html
import com.example.trivia.ui.theme.TriviaTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TriviaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TriviaApp()
                }
            }
        }
    }
}

@Composable
fun TriviaApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "homepage") {
        composable("homepage") { Homepage(navController) }
        composable("play") { PlayScreen(navController) }
        //composable("options"){OptionsScreen()}
        composable("quiz/{category}/{difficulty}") { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category")
            val difficulty = backStackEntry.arguments?.getString("difficulty")
            QuizScreen(category = category ?: "", difficulty = difficulty ?: "")
        }
    }
}


@Composable
fun Homepage(navController: NavController){

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Trivia",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )


        Text(
            text = "Play",
            fontSize = 24.sp,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .clickable {
                    navController.navigate("play")

                }
        )


        Text(
            text = "Options",
            fontSize = 24.sp,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .clickable {
                    //navController.navigate("options")

                }
        )
    }
}


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


@Composable
fun QuizScreen(category: String, difficulty: String) {
    val questions = remember { mutableStateListOf<String>() }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(category, difficulty) {
        isLoading = true
        val categoryID = getCategoryID(category)
        val questionsFromApi = fetchQuestions(categoryID, difficulty)
        questions.addAll(questionsFromApi)
        isLoading = false
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
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            questions.forEach { question ->
                Text(text = question)
            }
        }
    }
}


suspend fun fetchQuestions(categoryID: String, difficulty: String): List<String> {
    return withContext(Dispatchers.IO) {
        try {
            val url = "https://opentdb.com/api.php?amount=10&category=$categoryID&difficulty=$difficulty&type=multiple"
            val response = URL(url).readText()
            val json = JSONObject(response)
            val questions = mutableListOf<String>()
            val results = json.getJSONArray("results")
            for (i in 0 until results.length()) {
                val question = results.getJSONObject(i).getString("question")
                val decodedQuestion = Html.fromHtml(question, Html.FROM_HTML_MODE_LEGACY).toString()
                questions.add(decodedQuestion)
            }
            questions
        } catch (e: Exception) {
            Log.e("FetchQuestions", "Error fetching questions", e)
            emptyList<String>()
        }
    }
}



fun getCategoryID(category: String): String {
    return when (category) {
        "General Knowledge" -> "9"
        "Vehicles" -> "28"
        "Sports" -> "21"
        "Entertainment: Video Games" -> "15"
        "Entertainment: Film" -> "11"
        "Science & Nature" -> "17"
        "Celebrities" -> "26"
        "Animals" -> "27"
        "Entertainment: Books" -> "10"
        "Entertainment: Music" -> "12"
        "Entertainment: Musical & Theatres" -> "13"
        "Entertainment: Television" -> "14"
        "Entertainment: Board Games" -> "16"
        "Science: Computers" -> "18"
        "Science: Mathematics" -> "19"
        "Mythology" -> "20"
        "History" -> "23"
        "Geography" -> "22"
        "Politics" -> "24"
        "Art" -> "25"
        "Entertainment: Comics" -> "29"
        "Entertainment: Cartoon & Animations" -> "32"
        "Entertainment: Japanese Anime & Manga" -> "31"
        "Science: Gadgets" -> "30"
        else -> "9" // Default to "General Knowledge"
    }
}



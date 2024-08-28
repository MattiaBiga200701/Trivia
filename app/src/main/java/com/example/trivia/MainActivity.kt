package com.example.trivia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.trivia.screens.EndScreen
import com.example.trivia.screens.Homepage
import com.example.trivia.screens.OptionsScreen
import com.example.trivia.screens.PlayScreen
import com.example.trivia.screens.QuizScreen
import com.example.trivia.ui.theme.TriviaTheme
import com.example.trivia.viewmodel.SettingsViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.trivia.screens.TimeUpScreen
import com.example.trivia.viewmodel.GameViewModel


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
    val settingsViewModel: SettingsViewModel = viewModel()
    val gameViewModel: GameViewModel = viewModel()

    NavHost(navController = navController, startDestination = "homepage") {

        composable("homepage") { Homepage(navController) }

        composable("play") { PlayScreen(navController) }

        composable("options"){OptionsScreen(navController, settingsViewModel)}

        composable("quiz/{category}/{difficulty}") { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category")
            val difficulty = backStackEntry.arguments?.getString("difficulty")
            QuizScreen(navController, category = category ?: "", difficulty = difficulty ?: "", gameViewModel)
        }

        composable("timeOver/{category}/{difficulty}") { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category")
            val difficulty = backStackEntry.arguments?.getString("difficulty")
            TimeUpScreen(navController = navController, category = category ?: "", difficulty = difficulty ?: "")}

        composable("end") { EndScreen(navController = navController, gameViewModel, settingsViewModel) }
    }
}




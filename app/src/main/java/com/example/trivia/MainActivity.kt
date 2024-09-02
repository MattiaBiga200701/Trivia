package com.example.trivia

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.trivia.screens.EndScreen
import com.example.trivia.screens.Homepage
import com.example.trivia.screens.OptionsScreen
import com.example.trivia.screens.PlayScreen
import com.example.trivia.ui.theme.TriviaTheme
import com.example.trivia.viewmodel.SettingsViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.trivia.screens.QuestionScreen
import com.example.trivia.screens.TimeUpScreen
import com.example.trivia.viewmodel.GameSessionViewModel
import com.example.trivia.viewmodel.GameViewModelFactory


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

    @Composable
    fun TriviaApp() {
        val navController = rememberNavController()

        val settingsViewModel: SettingsViewModel = viewModel()
        val gameViewModel: GameSessionViewModel = viewModel(
            factory = GameViewModelFactory(LocalContext.current.applicationContext as Application)
        )

        NavHost(navController = navController, startDestination = "homepage") {
            composable("homepage") { Homepage(navController) }
            composable("play") { PlayScreen(navController) }
            composable("options") { OptionsScreen(navController, settingsViewModel) }
            composable(
                "question/{category}/{difficulty}/{questionIndex}",
                arguments = listOf(
                    navArgument("category") { type = NavType.StringType },
                    navArgument("difficulty") { type = NavType.StringType },
                    navArgument("questionIndex") { type = NavType.IntType }
                )
            ) { backStackEntry ->
                val category = backStackEntry.arguments?.getString("category") ?: ""
                val difficulty = backStackEntry.arguments?.getString("difficulty") ?: ""
                val questionIndex = backStackEntry.arguments?.getInt("questionIndex") ?: 0

                QuestionScreen(
                    navController = navController,
                    category = category,
                    difficulty = difficulty,
                    questionIndex = questionIndex,
                    viewModel = gameViewModel
                )
            }

            composable("timeOver/{category}/{difficulty}") { backStackEntry ->
                val category = backStackEntry.arguments?.getString("category")
                val difficulty = backStackEntry.arguments?.getString("difficulty")
                TimeUpScreen(navController, category ?: "", difficulty ?: "")
            }
            composable("end") { EndScreen(navController, gameViewModel, settingsViewModel) }
        }
    }
}



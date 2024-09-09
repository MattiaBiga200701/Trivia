package com.example.trivia


import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import com.example.trivia.db.Repository
import com.example.trivia.db.TriviaDB
import com.example.trivia.screens.ErrorScreen
import com.example.trivia.screens.GameHistoryScreen
import com.example.trivia.screens.QuestionScreen
import com.example.trivia.viewmodel.GameHistoryModelFactory
import com.example.trivia.viewmodel.GameHistoryViewModel
import com.example.trivia.viewmodel.GameSessionViewModel
import com.example.trivia.viewmodel.GameSessionModelFactory


class MainActivity : ComponentActivity() {

    private val settingsViewModel: SettingsViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val darkThemeEnabled by settingsViewModel.themeState.observeAsState(initial = false)

            TriviaTheme(darkTheme = darkThemeEnabled) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TriviaApp()
                }
            }
        }
    }

    @Suppress("DEPRECATION")
    private fun setImmersiveMode() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Version >= Android 11
            window.setDecorFitsSystemWindows(false)
            window.insetsController?.let { controller ->
                controller.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                controller.hide(WindowInsets.Type.systemBars())
            }
        } else {
            // Version <  Android 11
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    )


            window.decorView.setOnSystemUiVisibilityChangeListener { visibility ->
                if ((visibility and View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {

                    window.decorView.systemUiVisibility = (
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            )
                }
            }
        }
    }



    @RequiresApi(Build.VERSION_CODES.R)
    @Composable
    fun TriviaApp() {
        val navController = rememberNavController()

        val settingsViewModel = this.settingsViewModel

        val context = LocalContext.current
        val db = TriviaDB.getInstance(context)
        val repository = Repository(db.gameHistoryDao())


        val gameViewModel: GameSessionViewModel = viewModel(
            factory = GameSessionModelFactory(repository)
        )


        val gameHistoryViewModel: GameHistoryViewModel = viewModel(
            factory = GameHistoryModelFactory(repository)
        )

        LaunchedEffect(Unit){
            setImmersiveMode()
        }


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
            composable("end") { EndScreen(navController, gameViewModel, settingsViewModel) }
            composable("history") {
                GameHistoryScreen(navController, gameHistoryViewModel)
            }
            composable("incorrectAnswers") {
                ErrorScreen(navController, gameViewModel)
            }
        }
    }
}

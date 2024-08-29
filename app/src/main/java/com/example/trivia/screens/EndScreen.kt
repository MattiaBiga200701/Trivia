package com.example.trivia.screens


import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

import androidx.navigation.NavController
import com.example.trivia.R
import com.example.trivia.ui.theme.MyBlack
import com.example.trivia.ui.theme.MyGreen
import com.example.trivia.ui.theme.MyPink

import com.example.trivia.ui.theme.bigSpace
import com.example.trivia.ui.theme.cornerRounding
import com.example.trivia.ui.theme.fontSize
import com.example.trivia.ui.theme.medalSize
import com.example.trivia.ui.theme.mediumFontSize
import com.example.trivia.ui.theme.mediumPadding

import com.example.trivia.ui.theme.smallSpace
import com.example.trivia.ui.theme.standardButton
import com.example.trivia.viewmodel.GameSessionViewModel

import com.example.trivia.viewmodel.SettingsViewModel

@Composable
fun EndScreen(navController: NavController, gameViewModel: GameSessionViewModel, settingsViewModel: SettingsViewModel) {

    val context = LocalContext.current
    val score by gameViewModel.score.observeAsState(initial = 0)
    val isSoundEnable by settingsViewModel.soundState.observeAsState(initial = false)

    val message = when (score) {
        in 9..10 -> "Incredible! You're a true champion!"
        in 7..8 -> "Great job! You've scored really well!"
        in 5..6 -> "Well done! Keep improving!"
        else -> "You can do better!"

    }


    val medalResource = when (score) {
        in 9..10 -> R.drawable.gold_medal
        in 7..8 -> R.drawable.silver_medal
        in 5..6 -> R.drawable.bronze_medal
        else -> R.drawable.poop
    }

    val soundResId = when (score) {
        in 9..10 -> R.raw.victory_sound
        in 7..8 -> R.raw.cheer_sound
        in 5..6 -> R.raw.clap_sound
        else -> R.raw.fail_sound
    }

    val rotationY = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        rotationY.animateTo(
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 2000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            )
        )
    }

    if(isSoundEnable) {
        LaunchedEffect(Unit) {
            gameViewModel.playSound(context, soundResId)
        }

        DisposableEffect(Unit) {
            onDispose {
                gameViewModel.stopSound()
            }
        }
    }

    BackHandler {
        navController.navigate("homepage") {
            popUpTo("homepage") { inclusive = true }
            launchSingleTop = true
        }
    }

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
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = message,
                fontSize = mediumFontSize,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(bigSpace))

            Image(
                painter = painterResource(id = medalResource),
                contentDescription = null,
                modifier = Modifier
                    .size(medalSize)
                    .graphicsLayer(rotationY = rotationY.value)
            )

            Spacer(modifier = Modifier.height(bigSpace))

            Text(text = "Your Score:", fontSize = mediumFontSize, fontWeight = FontWeight.Bold,  color = Color.White)

            Spacer(modifier = Modifier.height(smallSpace))

            Text(text = "$score/10", fontSize = mediumFontSize, fontWeight = FontWeight.Bold, color = Color.White)

            Spacer(modifier = Modifier.height(64.dp))

            Button(
                onClick = {
                    navController.navigate("homepage") {
                        popUpTo("homepage") { inclusive = true }
                        launchSingleTop = true
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = MyPink),
                shape = RoundedCornerShape(cornerRounding),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(mediumPadding)
                    .height(standardButton)
            ) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Main Menu",
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(smallSpace))
                Text(
                    text = "Main Menu",
                    color = Color.White,
                    fontSize = fontSize
                )
            }

        }
    }

}


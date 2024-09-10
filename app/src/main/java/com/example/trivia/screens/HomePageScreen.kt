package com.example.trivia.screens

import android.app.Activity
import androidx.activity.compose.BackHandler
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
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.trivia.R
import com.example.trivia.ui.theme.cornerRounding
import com.example.trivia.ui.theme.fontSize
import com.example.trivia.ui.theme.logoSize
import com.example.trivia.ui.theme.mediumPadding
import com.example.trivia.ui.theme.smallPadding
import com.example.trivia.ui.theme.microSpace
import com.example.trivia.ui.theme.standardButton


@Composable
fun Homepage(navController: NavController) {

    val context = LocalContext.current

    var showExitDialog by remember { mutableStateOf(false) }

    val colors= MaterialTheme.colorScheme






    BackHandler {
        showExitDialog = true
    }

    if (showExitDialog) {

        AlertDialog(
            onDismissRequest = { showExitDialog = false },
            title = { Text(context.getString(R.string.alert_dialog_title)) },
            text = { Text(context.getString(R.string.alert_dialog_question)) },
            confirmButton = {
                Button(
                    onClick = {
                        showExitDialog = false

                        (context as? Activity)?.finish()
                    }
                ) {
                    Text(context.getString(R.string.alert_dialog_choice_1))
                }
            },
            dismissButton = {
                Button(
                    onClick = { showExitDialog = false }
                ) {
                    Text(context.getString(R.string.alert_dialog_choice_2))
                }
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(colors.background, colors.primary)
                )
            )
            .padding(mediumPadding),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Spacer(modifier = Modifier.height(100.dp))

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "App Logo",
                modifier = Modifier.size(logoSize)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.navigate("Play") },
                colors = ButtonDefaults.buttonColors(containerColor = colors.primary),
                shape = RoundedCornerShape(cornerRounding),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = smallPadding, horizontal = mediumPadding)
                    .height(standardButton)
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Play",
                    tint = colors.onBackground
                )
                Spacer(modifier = Modifier.width(microSpace))
                Text(text = context.getString(R.string.play_string), color = colors.onBackground, fontSize = fontSize)
            }

            Button(
                onClick = { navController.navigate("Options") },
                colors = ButtonDefaults.buttonColors(containerColor = colors.secondary),
                shape = RoundedCornerShape(cornerRounding),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = smallPadding, horizontal = mediumPadding)
                    .height(standardButton)
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Options",
                    tint = colors.onBackground
                )
                Spacer(modifier = Modifier.width(microSpace))
                Text(text = context.getString(R.string.options_string), color = colors.onBackground, fontSize = fontSize)
            }


            Button(
                onClick = { navController.navigate("history") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF98FF98)),
                shape = RoundedCornerShape(cornerRounding),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = smallPadding, horizontal = mediumPadding)
                    .height(standardButton)
            ) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "History",
                    tint = colors.onBackground
                )
                Spacer(modifier = Modifier.width(microSpace))
                Text(text = context.getString(R.string.history_string), color = colors.onBackground, fontSize = fontSize)
            }
        }
    }
}

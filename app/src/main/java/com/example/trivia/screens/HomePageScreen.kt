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
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import com.example.trivia.ui.theme.MyBlack
import com.example.trivia.ui.theme.MyGreen
import com.example.trivia.ui.theme.MyPink

import com.example.trivia.ui.theme.cornerRounding
import com.example.trivia.ui.theme.fontSize
import com.example.trivia.ui.theme.logoSize
import com.example.trivia.ui.theme.mediumPadding
import com.example.trivia.ui.theme.mediumSpace
import com.example.trivia.ui.theme.smallPadding
import com.example.trivia.ui.theme.smallSpace
import com.example.trivia.ui.theme.standardButton


@Composable
fun Homepage(navController: NavController) {

    val context = LocalContext.current

    var showExitDialog by remember {mutableStateOf(false)}

    // Gestisce il back press
    BackHandler {
        showExitDialog = true
    }

    if (showExitDialog) {
        // Mostra il pop-up di conferma
        AlertDialog(
            onDismissRequest = { showExitDialog = false },
            title = { Text("Esci dall'app") },
            text = { Text("Sei sicuro di voler uscire dall'applicazione?") },
            confirmButton = {
                Button(
                    onClick = {
                        showExitDialog = false
                        // Esce dall'app
                        (context as? Activity)?.finish()
                    }
                ) {
                    Text("Sì")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showExitDialog = false }
                ) {
                    Text("No")
                }
            }
        )
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(MyBlack, MyGreen)
                )
            )
            .padding(mediumPadding),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Spacer(modifier = Modifier.height(120.dp))

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(logoSize)

            )


            Spacer(modifier = Modifier.height(mediumSpace))

            Button(
                onClick = { navController.navigate("Play") },
                colors = ButtonDefaults.buttonColors(containerColor = MyGreen),
                shape = RoundedCornerShape(cornerRounding),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = smallPadding, horizontal = mediumPadding)
                    .height(standardButton)

            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Play",
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(smallSpace))
                Text(text = "Play", color = Color.White, fontSize = fontSize)
            }

            Button(
                onClick = { navController.navigate("Options") },
                colors = ButtonDefaults.buttonColors(containerColor= MyPink),
                shape = RoundedCornerShape(cornerRounding),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = smallPadding, horizontal = mediumPadding)
                    .height(standardButton)
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Options",
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(smallSpace))
                Text(text = "Options", color = Color.White, fontSize = fontSize)
            }
        }
    }
}

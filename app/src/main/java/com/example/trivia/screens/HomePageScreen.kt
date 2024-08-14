package com.example.trivia.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

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
                    navController.navigate("options")

                }
        )
    }
}

package com.example.trivia.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun OptionsScreen(navController: NavController) {

    val context = LocalContext.current


    var darkThemeEnabled by remember { mutableStateOf(false) }


    var soundEnabled by remember { mutableStateOf(false) }


    var notificationsEnabled by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        Text(text = "Settings", fontSize = 32.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(32.dp))


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Dark Theme", fontSize = 18.sp)
            Switch(
                checked = darkThemeEnabled,
                onCheckedChange = { isChecked ->
                    darkThemeEnabled = isChecked
                    Toast.makeText(
                        context,
                        "Dark Theme ${if (isChecked) "Enabled" else "Disabled"}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Enable Sound", fontSize = 18.sp)
            Switch(
                checked = soundEnabled,
                onCheckedChange = { isChecked ->
                    soundEnabled = isChecked
                    Toast.makeText(
                        context,
                        "Sound ${if (isChecked) "Enabled" else "Disabled"}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Enable Notifications", fontSize = 18.sp)
            Switch(
                checked = notificationsEnabled,
                onCheckedChange = { isChecked ->
                    notificationsEnabled = isChecked
                    Toast.makeText(
                        context,
                        "Notifications ${if (isChecked) "Enabled" else "Disabled"}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))


        Button(
            onClick = { navController.navigate("homepage") },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Save & Return to Main Menu")
        }
    }
}

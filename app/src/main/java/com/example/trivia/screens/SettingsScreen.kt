package com.example.trivia.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.trivia.viewmodel.SettingsViewModel



@Composable
fun OptionsScreen(navController: NavController, viewModel: SettingsViewModel) {

    val context = LocalContext.current


    val darkThemeEnabled by viewModel.themeState.observeAsState(initial = false)



    val soundEnabled by viewModel.soundState.observeAsState(initial = false)


    val notificationsEnabled by viewModel.notificationsState.observeAsState(initial = false)

    print("DIO CANEEEEEEEEE " + darkThemeEnabled)

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
                    viewModel.setThemeState(isChecked)
                    toastMessage(message = "Dark Theme", context = context , check = isChecked )
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
                    viewModel.setSoundState(isChecked)
                    toastMessage(message = "Sound", context = context , check = isChecked )
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
                    viewModel.setNotificationsState(isChecked)
                    toastMessage(message = "Notifications", context = context , check = isChecked )
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


fun toastMessage(message: String, context: Context, check: Boolean){
    Toast.makeText(
        context,
        message + " " + if (check) "Enabled" else "Disabled",
        Toast.LENGTH_SHORT
    ).show()
}

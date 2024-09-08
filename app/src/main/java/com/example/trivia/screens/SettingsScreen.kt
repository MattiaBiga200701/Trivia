package com.example.trivia.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.trivia.R
import com.example.trivia.ui.theme.MyBlack
import com.example.trivia.ui.theme.MyGreen
import com.example.trivia.ui.theme.MyPink
import com.example.trivia.ui.theme.bigSpace
import com.example.trivia.ui.theme.cornerRounding
import com.example.trivia.ui.theme.fontSize
import com.example.trivia.ui.theme.iconSize
import com.example.trivia.ui.theme.mediumFontSize
import com.example.trivia.ui.theme.mediumPadding
import com.example.trivia.ui.theme.smallPadding
import com.example.trivia.ui.theme.standardButton
import com.example.trivia.viewmodel.SettingsViewModel



@Composable
fun OptionsScreen(navController: NavController, viewModel: SettingsViewModel) {

    val context = LocalContext.current

    val darkThemeEnabled by viewModel.themeState.observeAsState(initial = false)
    val soundEnabled by viewModel.soundState.observeAsState(initial = false)
    val notificationsEnabled by viewModel.notificationsState.observeAsState(initial = false)

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
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = mediumPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(45.dp))

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = mediumPadding)
            ){

                IconButton(
                    onClick = { navController.navigateUp() },
                    modifier = Modifier
                        .align(alignment = Alignment.CenterStart)
                        .padding(start = 4.dp)
                        .size(iconSize)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back to Homepage",
                        tint = Color.White
                    )
                }

                Text(
                    text = context.getString(R.string.settings_string),
                    fontSize = mediumFontSize,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White

                )

            }

            Spacer(modifier = Modifier.height(bigSpace))

            SettingRow(
                label = context.getString(R.string.dark_theme_string),
                checked = darkThemeEnabled,
                onCheckedChange = { isChecked ->
                    viewModel.setThemeState(isChecked)
                    toastMessage(message = context.getString(R.string.dark_theme_string), context = context , check = isChecked )
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            SettingRow(
                label = context.getString(R.string.sound_string),
                checked = soundEnabled,
                onCheckedChange = { isChecked ->
                    viewModel.setSoundState(isChecked)
                    toastMessage(message = context.getString(R.string.sound_toast), context = context , check = isChecked )
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            SettingRow(
                label = context.getString(R.string.notifications_string),
                checked = notificationsEnabled,
                onCheckedChange = { isChecked ->
                    viewModel.setNotificationsState(isChecked)
                    toastMessage(message = context.getString(R.string.notification_toast), context = context , check = isChecked )
                }
            )

            Spacer(modifier = Modifier.height(bigSpace))

            Button(
                onClick = { navController.navigate("homepage") },
                colors = ButtonDefaults.buttonColors(containerColor = MyPink),
                shape = RoundedCornerShape(cornerRounding),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .padding(vertical = smallPadding, horizontal = mediumPadding)
                    .height(standardButton)
            ) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Save",
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = context.getString(R.string.save_button),
                    color = Color.White,
                    fontSize = fontSize
                )
            }
            }
        }
    }


@Composable
fun SettingRow(label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = smallPadding),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, fontSize = fontSize, fontWeight = FontWeight.Bold, color = Color.White)
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color(0xFF23FFCD),
                uncheckedThumbColor = Color(0xFFF231AA),
                checkedTrackColor = Color(0xFF23FFCD).copy(alpha = 0.5f),
                uncheckedTrackColor = Color(0xFFF231AA).copy(alpha = 0.5f)
            )
        )
    }
}

fun toastMessage(message: String, context: Context, check: Boolean){
    Toast.makeText(
        context,
        message + " " + if (check) "ON" else "OFF",
        Toast.LENGTH_SHORT
    ).show()
}

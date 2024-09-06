package com.example.trivia.screens

import android.app.DatePickerDialog
import android.content.Context

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.*
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.DropdownMenuItem


import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.trivia.R
import com.example.trivia.db.GameHistory
import com.example.trivia.logic.enums.Category
import com.example.trivia.ui.theme.MyBlack
import com.example.trivia.ui.theme.MyGreen
import com.example.trivia.ui.theme.mediumFontSize
import com.example.trivia.ui.theme.mediumPadding
import com.example.trivia.ui.theme.smallFontSize
import com.example.trivia.ui.theme.smallPadding
import com.example.trivia.viewmodel.GameHistoryViewModel
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameHistoryScreen(
    navController: NavController,
    viewModel: GameHistoryViewModel
) {
    val gameHistoryList by viewModel.gameHistoryList.observeAsState(emptyList())
    val context = LocalContext.current

    val selectedDate = remember { mutableStateOf<String?>(null) }
    val showDatePicker = remember { mutableStateOf(false) }

    val expanded = remember { mutableStateOf(false) }
    val selectedCategory = remember { mutableStateOf<String?>(null) }
    val categories = Category.entries.map { it.categoryName }






    LaunchedEffect(Unit) {
        viewModel.loadAllGameHistory()
    }

    if (showDatePicker.value) {
        ShowDatePicker(
            context = context,
            onDateSelected = { date ->
                selectedDate.value = date
                showDatePicker.value = false
            },
            onDismiss = {
                showDatePicker.value = false
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
            .padding(mediumPadding)
    ) {
        Column {

            Spacer(modifier = Modifier.height(45.dp))


            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {


                IconButton(
                    onClick = { navController.navigateUp() },
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 4.dp)
                        .size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back to Homepage",
                        tint = Color.White
                    )
                }


                Text(
                    text = context.getString(R.string.history_title),
                    color = Color.White,
                    fontSize = mediumFontSize,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))


            OutlinedButton(
                onClick = {
                    showDatePicker.value = true
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (selectedDate.value.isNullOrEmpty()) "Select Date" else selectedDate.value
                        ?: "",
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            ExposedDropdownMenuBox(
                expanded = expanded.value,
                onExpandedChange = { expanded.value = !expanded.value },
                modifier = Modifier.wrapContentSize()
            ) {

                TextField(
                    value = selectedCategory.value ?: "Select Category",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Category") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )


                ExposedDropdownMenu(
                    expanded = expanded.value,
                    onDismissRequest = { expanded.value = false }
                ) {
                    categories.forEach { category ->
                        DropdownMenuItem(
                            text = { Text(category) },
                            onClick = {
                                selectedCategory.value = category
                                expanded.value = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))


            if (gameHistoryList.isEmpty()) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "No game history available",
                        color = Color.White,
                        fontSize = smallFontSize,
                        fontWeight = FontWeight.Bold
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(gameHistoryList) { gameHistory ->
                        GameHistoryItem(gameHistory, context)
                        HorizontalDivider(color = Color.White)
                    }
                }
            }
        }
    }
}



@Composable
fun GameHistoryItem(gameHistory: GameHistory, context: Context) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = smallPadding)
            .background(Color(0x80000000), shape = RoundedCornerShape(8.dp))
            .padding(mediumPadding)
    ) {
        Text(
            text = context.getString(R.string.category_title) + ": " + gameHistory.category,
            color = Color.White,
            fontSize = smallFontSize,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = context.getString(R.string.difficulty_title) + ": " + gameHistory.difficulty,
            color = Color.White,
            fontSize = smallFontSize,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = context.getString(R.string.score_string_1) + ": " + gameHistory.score,
            color = Color.White,
            fontSize = smallFontSize,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = context.getString(R.string.time) + ": " + gameHistory.time,
            color = Color.White,
            fontSize = smallFontSize,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = context.getString(R.string.date) + ": " + gameHistory.date,
            color = Color.White,
            fontSize = smallFontSize
        )
    }
}

@Composable
fun ShowDatePicker(context: Context, onDateSelected: (String) -> Unit, onDismiss: () -> Unit) {

    val calendar = Calendar.getInstance()


    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->

            val selectedDate = "$dayOfMonth/${month + 1}/$year"
            onDateSelected(selectedDate)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )


    datePickerDialog.setOnCancelListener {
        onDismiss()
    }


    datePickerDialog.show()
}

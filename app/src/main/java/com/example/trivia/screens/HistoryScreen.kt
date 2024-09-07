package com.example.trivia.screens

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.BorderStroke

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size

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

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import java.util.Locale


@Composable
fun GameHistoryScreen(
    navController: NavController,
    viewModel: GameHistoryViewModel
) {
    val gameHistoryList by viewModel.gameHistoryList.observeAsState(emptyList())
    val context = LocalContext.current

    var selectedDate by remember { mutableStateOf<String?>(null) }
    val showDatePicker =  remember { mutableStateOf(false) }

    var selectedCategory by remember { mutableStateOf<String?>(null) }
    val categories = Category.entries.map { it.categoryName }

    LaunchedEffect(Unit) {
        viewModel.loadAllGameHistory()
    }

    LaunchedEffect(selectedDate, selectedCategory) {
        when {
            selectedDate != null && selectedCategory != null -> {

                viewModel.loadHistoryFilter(selectedCategory!!, selectedDate!!)

            }
            selectedDate != null -> {
                viewModel.loadHistoryFilterByDate(selectedDate!!)
            }
            selectedCategory != null -> {

                viewModel.loadHistoryFilterByCategory(selectedCategory!!)
            }
            else -> {
                viewModel.loadAllGameHistory()
            }
        }
    }



    if (showDatePicker.value) {
        ShowDatePicker(
            context = context,
            onDateSelected = { date ->
                selectedDate = date
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

            Button(
                onClick = {
                    showDatePicker.value = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, Color.White)
            ) {
                Text(
                    text = if (selectedDate.isNullOrEmpty()) "Select Date" else selectedDate ?: "",
                    color = Color.White,
                    fontSize = smallFontSize
                )
            }


            Spacer(modifier = Modifier.height(16.dp))


            CustomDropdownMenu(
                label = "Category",
                options = categories,
                selectedOption = selectedCategory,
                onOptionSelected = { selectedCategory = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    selectedDate = null
                    selectedCategory = null
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, Color.White)
            ) {
                Text(
                    text = "Clear Filters",
                    color = Color.White,
                    fontSize = smallFontSize
                )
            }


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

            val formattedMonth = String.format(Locale.US, "%02d",month + 1)
            val formattedDay = String.format(Locale.US, "%02d", dayOfMonth)
            val selectedDate = "$year-$formattedMonth-$formattedDay"
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDropdownMenu(
    label: String,
    options: List<String>,
    selectedOption: String?,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val expanded = remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded.value,
        onExpandedChange = { expanded.value = !expanded.value },
        modifier = modifier.fillMaxWidth()
    ) {

        TextField(
            value = selectedOption ?: "Select $label",
            onValueChange = {},
            readOnly = true,
            label = { Text(
                text = label,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.White
            ) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value)
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor()
                .border(1.dp, Color.White, RoundedCornerShape(8.dp)),
            textStyle = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
        )
        )

        ExposedDropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onOptionSelected(option)
                        expanded.value = false
                    }
                )
            }
        }
    }
}





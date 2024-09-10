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

import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
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

import androidx.navigation.NavController
import com.example.trivia.R
import com.example.trivia.db.GameHistory
import com.example.trivia.logic.enums.Category

import com.example.trivia.ui.theme.borderStrokeSize
import com.example.trivia.ui.theme.cornerRounding
import com.example.trivia.ui.theme.iconSize

import com.example.trivia.ui.theme.mediumFontSize
import com.example.trivia.ui.theme.mediumPadding
import com.example.trivia.ui.theme.microFontSize
import com.example.trivia.ui.theme.smallFontSize
import com.example.trivia.ui.theme.smallPadding
import com.example.trivia.ui.theme.smallSpace
import com.example.trivia.ui.theme.standardButton
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
    val colors=MaterialTheme.colorScheme

    var selectedDate by remember { mutableStateOf<String?>(null) }
    val showDatePicker =  remember { mutableStateOf(false) }

    var selectedCategory by remember { mutableStateOf<String?>(null) }
    val categories = Category.entries.map { it.categoryName }
    var isEnabled by remember { mutableStateOf(true) }

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
                    colors = listOf(colors.background, colors.primary)
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
                    .padding(vertical = mediumPadding)
            ) {

                IconButton(
                    onClick = {
                        if (isEnabled) {
                            isEnabled = false
                            navController.navigateUp()
                        }
                    },
                    enabled = isEnabled,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 2.dp)
                        .size(iconSize)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back to Homepage",
                        tint = colors.onBackground
                    )
                }

                Text(
                    text = context.getString(R.string.history_title),
                    color = colors.onBackground,
                    fontSize = mediumFontSize,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.height(smallSpace))

            Button(
                onClick = {
                    showDatePicker.value = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(standardButton),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = colors.onBackground
                ),
                shape = RoundedCornerShape(cornerRounding),
                border = BorderStroke(borderStrokeSize, colors.secondary)
            ) {
                Text(
                    text = if (selectedDate.isNullOrEmpty()) "Select Date" else selectedDate ?: "",
                    color = colors.onBackground,
                    fontSize = microFontSize
                )
            }


            Spacer(modifier = Modifier.height(smallSpace))


            CustomDropdownMenu(
                label = "Category",
                options = categories,
                selectedOption = selectedCategory,
                onOptionSelected = { selectedCategory = it }
            )

            Spacer(modifier = Modifier.height(smallSpace))

            Button(
                onClick = {
                    selectedDate = null
                    selectedCategory = null
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(standardButton),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colors.secondary,
                    contentColor = colors.onBackground
                ),
                shape = RoundedCornerShape(cornerRounding),
                //border = BorderStroke(1.dp, Color.White)
            ) {
                Text(
                    text = "Clear Filters",
                    color = colors.onBackground,
                    fontSize = smallFontSize
                )
            }

            Spacer(modifier = Modifier.height(smallSpace))

            if (gameHistoryList.isEmpty()) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "No game history available",
                        color = colors.onBackground,
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
                        HorizontalDivider(color = colors.onBackground)
                    }
                }
            }
        }
    }
}


@Composable
fun GameHistoryItem(gameHistory: GameHistory, context: Context) {

    val colors=MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = smallPadding)
            .background(Color(0x80000000), shape = RoundedCornerShape(cornerRounding))
            .padding(mediumPadding)
    ) {
        Text(
            text = context.getString(R.string.category_title) + ": " + gameHistory.category,
            color = colors.onBackground,
            fontSize = smallFontSize,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = context.getString(R.string.difficulty_title) + ": " + gameHistory.difficulty,
            color = colors.onBackground,
            fontSize = smallFontSize,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = context.getString(R.string.score_string_1) + ": " + gameHistory.score,
            color = colors.onBackground,
            fontSize = smallFontSize,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = context.getString(R.string.time) + ": " + gameHistory.time,
            color = colors.onBackground,
            fontSize = smallFontSize,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = context.getString(R.string.date) + ": " + gameHistory.date,
            color = colors.onBackground,
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

    val colors=MaterialTheme.colorScheme

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
                fontSize = microFontSize,
                color = colors.onBackground
            ) },
            trailingIcon = {
                //ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value, )
                Icon(
                    imageVector = if (expanded.value) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = colors.onBackground
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = colors.onBackground,
                unfocusedTextColor = colors.onBackground
            ),
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor()
                .border(borderStrokeSize, colors.secondary, RoundedCornerShape(cornerRounding)),
            textStyle = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = microFontSize
        )
        )

        ExposedDropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
            modifier = Modifier.background(Color((0xFF333333)))
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onOptionSelected(option)
                        expanded.value = false
                    },
                    modifier = Modifier
                        .background(Color(0xFF333333))
                        .fillMaxSize()
                )
            }
        }
    }
}





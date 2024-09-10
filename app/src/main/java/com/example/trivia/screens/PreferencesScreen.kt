

package com.example.trivia.screens

import android.widget.Toast
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.trivia.R
import com.example.trivia.exceptions.EmptyInputException
import com.example.trivia.logic.enums.Category
import com.example.trivia.logic.enums.Difficulty
import com.example.trivia.ui.theme.MyCustomFont
import com.example.trivia.ui.theme.bigSpace
import com.example.trivia.ui.theme.borderStrokeSize
import com.example.trivia.ui.theme.cornerRounding
import com.example.trivia.ui.theme.fontSize
import com.example.trivia.ui.theme.iconSize
import com.example.trivia.ui.theme.mediumFontSize
import com.example.trivia.ui.theme.mediumPadding
import com.example.trivia.ui.theme.mediumSpace
import com.example.trivia.ui.theme.microFontSize
import com.example.trivia.ui.theme.microSpace

import com.example.trivia.ui.theme.standardButton


@Composable
fun PlayScreen(navController: NavController) {

    var selectedCategory by remember { mutableStateOf<String?>(null) }
    var selectedDifficulty by remember { mutableStateOf<String?>(null) }
    var isEnabled by remember { mutableStateOf(true) }
    val context = LocalContext.current

    val categories = Category.entries.map { it.categoryName }
    val difficulties = Difficulty.entries.map {it.id}

    val colors=MaterialTheme.colorScheme

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(colors.background, colors.primary)
                )
            )
            .padding(mediumPadding),
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
                    text = context.getString(R.string.preference_screen_title),
                    fontSize = mediumFontSize,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        //.padding(start= 20.dp)
                        .align(Alignment.Center),
                    color = colors.onBackground

                )



            }

            Spacer(modifier = Modifier.height(80.dp))

            Text(
                text = context.getString(R.string.category_string),
                fontSize = fontSize,
                fontWeight = FontWeight.Bold,
                fontFamily = MyCustomFont,
                color = colors.onBackground,
                modifier = Modifier.padding(bottom = mediumPadding)
            )

            DropDownMenu(
                label = "Category",
                options = categories,
                selectedOption = selectedCategory,
                onOptionSelected = { selectedCategory = it }
            )

            Spacer(modifier = Modifier.height(mediumSpace))

            Text(
                text = context.getString(R.string.difficulty_string),
                fontSize = fontSize,
                fontWeight = FontWeight.Bold,
                fontFamily = MyCustomFont,
                color = Color.White,
                modifier = Modifier.padding(bottom = mediumPadding)
            )

            DropDownMenu(
                label = "Difficulty",
                options = difficulties,
                selectedOption = selectedDifficulty,
                onOptionSelected = { selectedDifficulty = it }
            )

            Spacer(modifier = Modifier.height(bigSpace))

            Button(

                onClick = {

                    try {

                        if (selectedCategory?.isEmpty() == true) {
                            throw EmptyInputException(context.getString(R.string.error_message_1))
                        } else if (selectedDifficulty?.isEmpty() == true) {
                            throw EmptyInputException(context.getString(R.string.error_message_2))
                        }
                        navController.navigate("question/${selectedCategory}/${selectedDifficulty}/${0}")

                    }catch(e: EmptyInputException){
                        Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    }
                          },
                colors = ButtonDefaults.buttonColors(containerColor = colors.secondary),
                shape = RoundedCornerShape(cornerRounding),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(standardButton)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored. Filled. ArrowForward,
                    contentDescription = "Start Quiz",
                    tint = colors.onBackground
                )
                Spacer(modifier = Modifier.width(microSpace))
                Text(context.getString(R.string.start_quiz_string), color = colors.onBackground, fontSize = fontSize)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownMenu(
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

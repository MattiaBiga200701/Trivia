

package com.example.trivia.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.trivia.R
import com.example.trivia.exceptions.EmptyInputException
import com.example.trivia.logic.enums.Category
import com.example.trivia.logic.enums.Difficulty
import com.example.trivia.ui.theme.MyBlack
import com.example.trivia.ui.theme.MyCustomFont
import com.example.trivia.ui.theme.MyGreen
import com.example.trivia.ui.theme.MyPink
import com.example.trivia.ui.theme.bigSpace
import com.example.trivia.ui.theme.cornerRounding
import com.example.trivia.ui.theme.fontSize
import com.example.trivia.ui.theme.mediumPadding
import com.example.trivia.ui.theme.mediumSpace
import com.example.trivia.ui.theme.smallPadding
import com.example.trivia.ui.theme.microSpace
import com.example.trivia.ui.theme.standardButton


@Composable
fun PlayScreen(navController: NavController) {

    var selectedCategory by remember { mutableStateOf("") }
    var selectedDifficulty by remember { mutableStateOf("") }
    val context = LocalContext.current


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(MyBlack, MyGreen)
                )
            )
            .padding(mediumPadding),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(mediumPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = context.getString(R.string.category_string),
                fontSize = fontSize,
                fontWeight = FontWeight.Bold,
                fontFamily = MyCustomFont,
                color = Color.White,
                modifier = Modifier.padding(bottom = mediumPadding)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = smallPadding),
                shape = RoundedCornerShape(cornerRounding),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                SimpleDropDownMenu(
                    isCategory = true,
                    selectedOption = selectedCategory,
                    onOptionSelected = { selectedCategory = it }
                )
            }

            Spacer(modifier = Modifier.height(mediumSpace))

            Text(
                text = context.getString(R.string.difficulty_string),
                fontSize = fontSize,
                fontWeight = FontWeight.Bold,
                fontFamily = MyCustomFont,
                color = Color.White,
                modifier = Modifier.padding(bottom = mediumPadding)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = smallPadding),
                shape = RoundedCornerShape(cornerRounding),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                SimpleDropDownMenu(
                    isCategory = false,
                    selectedOption = selectedDifficulty,
                    onOptionSelected = { selectedDifficulty = it }
                )
            }

            Spacer(modifier = Modifier.height(bigSpace))

            Button(

                onClick = {

                    try {

                        if (selectedCategory.isEmpty()) {
                            throw EmptyInputException(context.getString(R.string.error_message_1))
                        } else if (selectedDifficulty.isEmpty()) {
                            throw EmptyInputException(context.getString(R.string.error_message_2))
                        }
                        navController.navigate("question/${selectedCategory}/${selectedDifficulty}/${0}")

                    }catch(e: EmptyInputException){
                        Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    }
                          },
                colors = ButtonDefaults.buttonColors(containerColor = MyPink),
                shape = RoundedCornerShape(cornerRounding),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(standardButton)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored. Filled. ArrowForward,
                    contentDescription = "Start Quiz",
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(microSpace))
                Text(context.getString(R.string.start_quiz_string), color = Color.White, fontSize = fontSize)
            }
        }
    }
}

@Composable
fun SimpleDropDownMenu(
    isCategory: Boolean,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var optionSelected by remember { mutableStateOf(selectedOption) }
    val context= LocalContext.current

    Column(modifier = Modifier.padding(mediumPadding)) {

        Text(
            text = optionSelected.ifEmpty { context.getString(R.string.select_option_string) },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = true }
                .padding(mediumPadding),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {

            if(isCategory){
                for(item in Category.entries){
                    DropdownMenuItem(
                        text = { Text(item.categoryName) },
                        onClick = {
                            onOptionSelected(item.categoryName)
                            optionSelected = item.categoryName
                            expanded = false })
                }
            }else {
                for(item in Difficulty.entries){
                    DropdownMenuItem(
                        text = { Text(item.id) },
                        onClick = {
                            onOptionSelected(item.id)
                            optionSelected = item.id
                            expanded = false })
                }
            }
        }
    }
}

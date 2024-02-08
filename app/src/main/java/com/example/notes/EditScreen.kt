package com.example.notes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.TextField
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.runtime.setValue
import androidx.navigation.NavController

@Composable

fun EditScreen( navController: NavController,list: MutableList<Element>, modifier: Modifier = Modifier, id: String){
    val currentElement by remember {
        mutableStateOf(list.single{it.id == id})
    }
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        var title by remember { mutableStateOf(currentElement.title) }
        var text by remember { mutableStateOf(currentElement.text) }
        var errorMessage = remember{ mutableStateOf("")}

        val minTitleChar = 3
        val maxTitleChar = 50
        val maxTextChar = 120


        Text("Edit:")
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") }
        )
        TextField(
            value = text,
            onValueChange = { text = it},
            label = { Text("Text") }
        )
        Button(onClick = {
            if(title.length >=minTitleChar && title.length < maxTitleChar && text.length < maxTextChar){
                currentElement.title = title
                currentElement.text = text
                navController.navigate("List")
            }
            else if (text.length > maxTextChar){
                errorMessage.value = "Error, the text length is to long."
            }
            else if (title.length > maxTitleChar){
                errorMessage.value = "Error, the title length is to long."
            }
            else {
                errorMessage.value = "Error, the title length is to short."
            }

        }) {
            Text("RETURN")
        }
    Row {
        Text(text = errorMessage.value)
    }
    }
}
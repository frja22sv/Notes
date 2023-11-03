package com.example.notes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.TextField
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue // Added TextFieldValue import
import androidx.compose.ui.text.input.TextFieldValue.Companion // Added TextFieldValue.Companion import
import androidx.navigation.NavController

@Composable

fun DetailScreen( navController: NavController,list: MutableList<Element>, modifier: Modifier = Modifier, id: String){
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
        Text("Detail:")
        TextField(
            value = title,
            onValueChange = { },
            label = { Text("Title") },
            enabled = false
        )
        TextField(
            value = text,
            onValueChange = {  },
            label = { Text("Text") },
            enabled = false
        )
        Button(onClick = {
            currentElement.title = title
            currentElement.text = text
            navController.navigate("List")
        }) {
            Text("RETURN")
        }

    }
}
package com.example.notes

import android.os.Bundle
import android.service.autofill.OnClickAction
import android.widget.Button
import android.widget.ListView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavArgument
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.notes.ui.theme.NotesTheme
import java.time.format.TextStyle
import java.util.UUID

data class Element(
    val id: String = UUID.randomUUID().toString(),
    var title: String,
    var text: String
)
data class ElementAction(
    val deleteButton: Button,
    val editButton: Button
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()


            val list = remember {
                mutableStateListOf(
                    Element(title = "", text = "")
                )
            }

            NotesTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background) {

                NavHost(navController = navController, startDestination = "List"){
                    composable(route = "List"){
                        ListView(navController = navController, list = list)
                    }
                    composable(route = "Edit/{id}", arguments =  listOf(navArgument("id"){
                        type = NavType.StringType
                        nullable = true
                    }))
                    {navBackStackEntry ->
                        val id = navBackStackEntry.arguments?.getString("id") ?: ""
                        EditScreen(navController = navController, list = list, id = id)
                    }
                    composable(route = "Detail/{id}", arguments =  listOf(navArgument("id"){
                        type = NavType.StringType
                        nullable = true
                    }))
                    {navBackStackEntry ->
                        val id = navBackStackEntry.arguments?.getString("id") ?: ""
                        DetailScreen(navController = navController, list = list, id = id)
                    }
                }

                }
            }
        }
    }
}

@Composable
fun RowView(navController: NavController,list: MutableList<Element>){

    Row(
        modifier = Modifier
            .padding(top = 72.dp)
            .fillMaxSize(),
    ){
        LazyVerticalGrid(
            columns = GridCells.Fixed(1
            ),
            content = {
            items(list) {element->
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .border(4.dp, Color.Yellow)
                    .padding(10.dp)
                ){
                    Column{
                        Text(text = element.title,
                            color = Color.White,
                            style = androidx.compose.ui.text.TextStyle(fontSize = 25.sp)
                            )
                        Text(text = element.text,
                            color = Color.White,
                            style = androidx.compose.ui.text.TextStyle(fontSize = 25.sp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.End
                        ){
                            Button(
                                onClick = {
                                    navController.navigate("Edit/${element.id}")
                                },
                                modifier = Modifier.padding(end = 8.dp),
                            ) {
                                Text(text = "EDIT")
                            }
                            Button(
                                onClick = {
                                    navController.navigate("Detail/${element.id}")
                                },
                                modifier = Modifier.padding(end = 8.dp),
                            ) {
                                Text(text = "DETAIL")
                            }
                            Button(
                                onClick = {
                                    list.remove(element)
                                },
                                modifier = Modifier.padding(end = 8.dp),
                            ) {
                                Text(text = "DELETE")
                            }
                        }
                    }
                }


            }
        })
    }

}
@Composable
fun ListView(navController: NavController, list: MutableList<Element>, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.DarkGray)

    ) {
        Column(
            modifier = modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,


            ) {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .background(Color.Yellow)

            ) {
                Text(
                    text = "Notes:",
                    modifier = Modifier.padding(20.dp),
                    style = androidx.compose.ui.text.TextStyle(fontSize = 29.sp)

                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.End
                ){
                    Button(
                        onClick = {
                            list.add(Element(title = "", text = ""))
                        },
                        modifier = Modifier.padding(end = 8.dp),
                    ) {
                        Text(text = "ADD NOTE")
                    }
                }

                }



            }

            RowView(navController, list)
        }
    }







@Preview(showBackground = true)
@Composable
fun JakobPreview() {
    NotesTheme {

        //ListView()
    }
}
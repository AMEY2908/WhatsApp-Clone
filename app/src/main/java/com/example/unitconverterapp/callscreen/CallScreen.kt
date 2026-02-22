package com.example.unitconverterapp.callscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.unitconverterapp.R
import com.example.unitconverterapp.homescreen.BottomNavigation
import com.example.unitconverterapp.navigation.Routes

@Composable

fun CallScreen(navHostController: NavHostController) {

    val sampleData=listOf(
        Call(image = R.drawable.channel3, name = "India", time = "yesterday,8:30 PM",missedCall = true),
        Call(image = R.drawable.channel5, name = "Rey Mys", time = "today,2:30 PM",missedCall = false),
        Call(image = R.drawable.channel5, name = "Rey Mys", time = "today,2:30 PM",missedCall = true),
        Call(image = R.drawable.channel4, name = "Roman", time = "Yesterday,9:30 AM",missedCall = true),
        Call(image = R.drawable.channel4, name = "Roman", time = "Yesterday,9:30 AM",missedCall = true),
        Call(image = R.drawable.channel5, name = "Roman", time = "Yesterday,9:30 AM",missedCall = true),

        )




    var isSearching by remember {
        mutableStateOf(false)
    }
    var search by remember {
        mutableStateOf("")
    }
    var showMenu by remember {
        mutableStateOf(false)
    }
    Scaffold(
        topBar = {
            Box(modifier = Modifier.fillMaxWidth()) {
                Column {
                    Row {
                        if (isSearching) {

                            TextField(
                                value = search,
                                onValueChange = {
                                    search = it
                                },
                                placeholder = { Text(text = "Search") },
                                colors = TextFieldDefaults.colors(
                                    unfocusedContainerColor = Color.Transparent,
                                    focusedContainerColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent
                                ),
                                modifier = Modifier.padding(start = 12.dp),
                                singleLine = true
                            )
                        } else {
                            Text(
                                text = "Calls",
                                fontSize = 28.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(
                                    start = 12.dp,
                                    top = 16.dp
                                )
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        if (isSearching) {
                            IconButton(onClick = {
                                isSearching = false
                                search = ""
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.cross),
                                    contentDescription = null,
                                    modifier = Modifier.size(15.dp)
                                )
                            }
                        } else {
                            IconButton(onClick = {
                                isSearching = true
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.search),
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            IconButton(onClick = {
                                showMenu = true

                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.more),
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            DropdownMenu(
                                expanded = showMenu,
                                onDismissRequest =
                                    { showMenu = false }
                            ) {
                                DropdownMenuItem(
                                    text = { Text(text = "Settings") },
                                    onClick = { showMenu = false }
                                )
                            }

                        }
                    }
                    HorizontalDivider()
                }
            }
        },
        bottomBar = {
            BottomNavigation(
                selectedItem = 1, // Updates tab
                onClick = { index ->

                    val route = when (index) {
                        0 -> Routes.HomeScreen
                        1 -> Routes.UpdateScreen
                        2 -> Routes.CommunityScreen
                        3 -> Routes.CallScreen
                        else -> Routes.HomeScreen
                    }

                    navHostController.navigate(route) {
                        popUpTo(navHostController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {}, containerColor = colorResource
                    (id = R.color.lightGreen),
                modifier = Modifier.size(65.dp),
                contentColor = Color.White
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.camera2), contentDescription = null,
                    Modifier.size(28.dp)
                )
            }
        }
    ) {
        Column(modifier = Modifier.padding(it)) {

            Spacer(Modifier.height(16.dp))

            FavouriteSection()
            Button(
                onClick = {}, colors = ButtonDefaults.buttonColors(
                    colorResource(R.color.lightGreen)
                ), modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {


                Text(
                    text = "Start a new Call",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Recent Calls",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(
                    horizontal = 16.dp,
                    vertical = 8.dp)
            )
            LazyColumn {
                items(sampleData){data->
                    CallItemDesign(data)
                }
            }
        }
    }
}
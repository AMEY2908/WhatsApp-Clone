package com.example.unitconverterapp.homescreen


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.unitconverterapp.navigation.Routes


@Composable

fun UpdateScreen(navHostController: NavHostController) {

    val scrollState = rememberScrollState()

    val sampleStatus = listOf(
        StatusData(R.drawable.chat2, "HERO X", "10:00AM"),
        StatusData(R.drawable.boy3, "HX", "19:00PM"),
        StatusData(R.drawable.chat1, "NEO", "15:00PM"),
    )

    val sampleChannels = listOf(
        Channels(R.drawable.channel, "You Tube", "Latest News"),
        Channels(R.drawable.channel2, "Facebook", "Learn"),
        Channels(R.drawable.channel3, "India", "Explore India")
    )

    Scaffold(

        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
                containerColor = colorResource(R.color.lightGreen),
                modifier = Modifier.size(65.dp),
                contentColor = Color.White
            ) {
                Icon(
                    painter = painterResource(R.drawable.camera2),
                    contentDescription = null,
                    modifier = Modifier.size(28.dp)
                )
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

        topBar = { TopBar() }

    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {

            Text(
                text = "Status",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
            )

            MyStatus()

            sampleStatus.forEach {
                StatusItem(statusData = it)
            }

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = Color.Gray)

            Text(
                text = "Channels",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
            )

            Spacer(Modifier.height(8.dp))

            Column(modifier = Modifier.padding(horizontal = 16.dp)) {

                Text(
                    text = "Stay updated on topics that matter to you. Find channels to follow below."
                )

                Spacer(modifier = Modifier.height(32.dp))

                Text(text = "Find Channels to Follow")
            }

            Spacer(Modifier.height(16.dp))

            sampleChannels.forEach {
                ChannelItemDesign(channels = it)
            }
        }
    }
}
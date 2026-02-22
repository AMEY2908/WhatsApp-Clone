package com.example.unitconverterapp.homescreen


import androidx.annotation.DrawableRes

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.unitconverterapp.R

@Composable

fun BottomNavigation(
    selectedItem: Int,
    onClick: (Int) -> Unit
) {

    val items = listOf(
        NavigationItem("Chats", R.drawable.chat, R.drawable.chat),
        NavigationItem("Updates", R.drawable.socialmedia, R.drawable.socialmedia),
        NavigationItem("Communities", R.drawable.communitites, R.drawable.communitites),
        NavigationItem("Call", R.drawable.call, R.drawable.call)
    )

    NavigationBar(
        containerColor = Color.White,
        modifier = Modifier.height(80.dp)
    ) {
        items.forEachIndexed { index, item ->

            NavigationBarItem(
                selected = selectedItem == index,
                onClick = { onClick(index) },

                label = {
                    Text(
                        text = item.name,
                        color = if (selectedItem == index)
                            Color.Black else Color.DarkGray
                    )
                },

                icon = {
                    Icon(
                        painter = painterResource(
                            if (selectedItem == index)
                                item.selectIcon
                            else
                                item.unselectIcon
                        ),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                },

                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = colorResource(R.color.lightGreen)
                )
            )
        }
    }
}

data class NavigationItem(
    val name: String,
    @DrawableRes val selectIcon: Int,
    @DrawableRes val unselectIcon: Int,
)
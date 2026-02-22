package com.example.unitconverterapp.callscreen

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unitconverterapp.R

@Composable
@Preview(showSystemUi = true)

fun FavouriteSection() {
    val sampleFavourites = listOf(
        FavouriteContact(
            image = R.drawable.boy1,
            name = "Ashish Chanchalani"
        ),
        FavouriteContact(
            image = R.drawable.boy2,
            name = "Carry Chanchalani"
        ),
        FavouriteContact(
            image = R.drawable.boy3,
            name = "Carry Minati"
        ),
        FavouriteContact(
            image = R.drawable.boy4,
            name = "Bhuvan Bam "
        ),
    )
    Column(
        modifier = Modifier.padding(
            start = 16.dp,
            bottom = 8.dp
        )
    ) {
        Text(
            text = "Favourites",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
        ) {
            sampleFavourites.forEach { FavouritesItem(it) }
        }
    }
}

data class FavouriteContact(
    val image: Int, val name: String
)
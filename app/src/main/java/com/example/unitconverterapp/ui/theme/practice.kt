package com.example.unitconverterapp.ui.theme


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun SpacerBox() {
    Box(
        modifier = Modifier.background(Color.Cyan)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // It provide space between element
            Spacer(modifier = Modifier.height(15.dp))
            CompositionLocalProvider(LocalContentColor provides Color.Red) {
                Text("My Name is CODER, HELLO NICE TO MEET YOU ")
                Text("My Name is CODER, HELLO NICE TO MEET YOU ")
                Text("My Name is CODER, HELLO NICE TO MEET YOU ")
                Text("My Name is CODER, HELLO NICE TO MEET YOU ")
                Text("My Name is CODER, HELLO NICE TO MEET YOU ")
            }
        }
    }
}

@Composable
fun MenuBox() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = {},
            Modifier.height(48.dp),
            shape = RoundedCornerShape(40.dp),
            colors = ButtonDefaults.buttonColors( Color.Cyan,contentColor = Color.Red),

        ) {

            Text("BUTTON")

        }
    }
}

@Composable
fun State(){

}


@Preview
@Composable
fun SpacerPreview() {
MenuBox()
}
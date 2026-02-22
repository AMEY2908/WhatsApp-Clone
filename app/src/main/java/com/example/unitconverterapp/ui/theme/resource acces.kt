package com.example.unitconverterapp.ui.theme


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.DisableSelection
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.unitconverterapp.R


@Composable
fun AccessStringResources() {
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter
    ) {
        Image(painter = painterResource(R.drawable.burger), contentDescription = "ImageOne")


    }
}

@Composable
fun SimpleText() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            "THIS IS BURGER",
            color = Color.Blue,
            fontSize = 30.sp,
            fontStyle = FontStyle.Italic,
            style = TextStyle(
                shadow = Shadow(color = Color.Red, blurRadius = 8f)
            ),

            )
    }
}

@Composable
fun ColorFulText() {
    val rainbow = listOf(
        Color.Blue,
        Color.Red,
        Color.Yellow,
        Color.Green,
        Color.Magenta,
        Color.Cyan,
    )
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = buildAnnotatedString {
                append("Order This Burger From Burger King\n")
                withStyle(
                    SpanStyle(
                        brush = Brush.linearGradient(colors = rainbow)
                    )
                )
                {
                    append("Burger King is Best")
                }
                append("\ntell them to eat this ")
            }
        )
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ScrollableText() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = "THIS IS THE NEW PAGE I AM CREATING ".repeat(50),
            maxLines = 2,
            fontSize = 36.sp,
            overflow = TextOverflow.Ellipsis
//            modifier = Modifier.basicMarquee(),
        )
    }
}

@Composable
fun PasswordDetail() {
    var password by rememberSaveable {
        mutableStateOf("")
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        TextField(
            value = password, onValueChange = { password = it },
            label = {
                Text("ENTER PASSWORD")
            },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            )
        )

    }

}

@Composable
fun PartialText() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        SelectionContainer {
            Column {
                Text(text = "MY NAME IS DEVELOPER")
                Text(text = "I WANT TO BE CODER")
                DisableSelection {
                    Text(text = "HELLO WORLD SELECT ")
                }
            }
        }
    }
}

@Composable
fun AnnotatedStringText() {
    val uriHandler = LocalUriHandler.current
    Text(
        buildAnnotatedString {
            append("BUILD YOUR APP FASTER")
            pushStringAnnotation(
                tag = "URL",
                annotation = "https://www.youtube.com/watch?v=U5dE-_E1wsg&t=11036s"
            )
            withStyle(style = SpanStyle(color = Color.Blue)) {
                pop()
            }
        }
       
    )
}

@Preview(showSystemUi = true)
@Composable
fun ResourcePreview() {

}

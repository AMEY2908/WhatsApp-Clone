package com.example.unitconverterapp.ui.theme

import android.R
import android.R.attr.onClick
import android.graphics.drawable.BitmapDrawable
import android.media.Image
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.state.ToggleableState

import androidx.compose.ui.unit.dp
import java.nio.file.WatchEvent


@Composable
fun FilledButton(){
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        val context= LocalContext.current
        Button(onClick ={
            Toast.makeText(context, "button is clicked", Toast.LENGTH_SHORT).show()
        }
        ) {
            Text("Filled Button")
        }
    }
}
@Composable
fun TonalButton(){
    val context=LocalContext.current
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        FilledTonalButton(onClick = {
            Toast.makeText(context, "", Toast.LENGTH_SHORT).show()
        })
        {
            Text("Tonal BUTTON")
        }
    }
}

@Composable
fun TextButton(){
    Box(modifier = Modifier.width(50.dp), contentAlignment = Alignment.Center){

    TextButton(onClick ={}, colors = ButtonDefaults.textButtonColors(contentColor = Color.Red)
        ) {
        Text("CLICK ME")
    }

    }
}
@Composable
fun ImageShow(){
    Box(modifier = Modifier.height(500.dp).width(500.dp),
        contentAlignment = Alignment.Center){
        Image(painter = painterResource(R.drawable.ic_menu_report_image),
            contentDescription ="MY PNG ",
            modifier = Modifier.size(200.dp).clip(CircleShape), contentScale = ContentScale.Crop)
    }
}
@Composable
fun ColumnPreview(){
    Box (modifier= Modifier.fillMaxSize(),contentAlignment= Alignment.Center)
    {
        Box(
            modifier = Modifier.width(250.dp).height(200.dp).background(color = Color.Cyan),
            contentAlignment = Alignment.Center
        ) {
            Column(modifier = Modifier.horizontalScroll(rememberScrollState()).verticalScroll(rememberScrollState()).padding(25.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text("MY NAME IS CODER")
                Text("I CODE IN KOTLIN")
                Text("I WANT TO BECOME ANDROID DEVELOPER")
                Text("I WANT TO BECOME ANDROID DEVELOPER")
                Text("I WANT TO BECOME ANDROID DEVELOPER")
                Text("I WANT TO BECOME ANDROID DEVELOPER")
                Text("I WANT TO BECOME ANDROID DEVELOPER")
                Text("I WANT TO BECOME ANDROID DEVELOPER")
                Text("I WANT TO BECOME ANDROID DEVELOPER")
                Text("I WANT TO BECOME ANDROID DEVELOPER")
                Text("I WANT TO BECOME ANDROID DEVELOPER")
                Text("I WANT TO BECOME ANDROID DEVELOPER")
                Text("I WANT TO BECOME ANDROID DEVELOPER")
                Text("I WANT TO BECOME ANDROID DEVELOPER")
                Text("I WANT TO BECOME ANDROID DEVELOPER")
                Text("I WANT TO BECOME ANDROID DEVELOPER")
                Text("I WANT TO BECOME ANDROID DEVELOPER")
                Text("I WANT TO BECOME ANDROID DEVELOPER")
//                 Lazy column is used for preferred list
                LazyColumn(modifier = Modifier.fillMaxWidth().height(200.dp).background(Color.LightGray)){
                    items(100) {
                         index->Text("ITEM at index $index",
                         modifier = Modifier.fillMaxWidth().padding(16.dp).background(Color.White))
                    }
                }
            }
        }
    }
}

@Composable
fun Checkbox() {
    // Initialize states for the child checkboxes
    val childCheckedStates = remember { mutableStateListOf(true, false, false) }

    // Compute the parent state based on children's states
    val parentState = when {
        childCheckedStates.all { it } -> ToggleableState.On
        childCheckedStates.none() { it } -> ToggleableState.Off
        else -> ToggleableState.Indeterminate
    }

    Column {
        // Parent TriStateCheckbox
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text("Select all")
            TriStateCheckbox(
                state = parentState,
                onClick = {
                    // Determine new state based on current state
                    val newState = parentState != ToggleableState.On
                    childCheckedStates.forEachIndexed { index, _ ->
                        childCheckedStates[index] = newState
                    }
                }
            )
        }

        // Child Checkboxes
        childCheckedStates.forEachIndexed { index, checked ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text("Option ${index + 1}")
                Checkbox(
                    checked = checked,
                    onCheckedChange = { isChecked ->
                        // Update the individual child state
                        childCheckedStates[index] = isChecked
                    }
                )
            }
        }
    }

    if (childCheckedStates.all { it }) {
        Text("All options selected")
    }
}

@Composable
fun IconButtonName(onEditClick:()-> Unit){
    Box(modifier=Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        IconButton(onClick = onEditClick) {
            Icon(imageVector=Icons.Default.Edit, contentDescription = "Edit")
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun Preview(){

}

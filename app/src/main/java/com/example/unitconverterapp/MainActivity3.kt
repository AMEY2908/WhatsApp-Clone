package com.example.unitconverterapp


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.unitconverterapp.ui.theme.AlertDialogExample
import com.example.unitconverterapp.ui.theme.CounterViewModel
import com.example.unitconverterapp.ui.theme.DialogExamples
import com.example.unitconverterapp.ui.theme.IconButtonName

import com.example.unitconverterapp.ui.theme.MyShoppingTheme
import com.example.unitconverterapp.ui.theme.TheCounterApp

class MainActivity3 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            
        }
    }
}
@Composable
fun FirstScreen(){
    val name= remember{ mutableStateOf("") }
}





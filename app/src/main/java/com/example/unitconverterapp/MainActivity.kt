package com.example.unitconverterapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.unitconverterapp.callscreen.CallScreen
import com.example.unitconverterapp.homescreen.ChatDesign
import com.example.unitconverterapp.homescreen.Homescreen
import com.example.unitconverterapp.homescreen.TopBar
import com.example.unitconverterapp.homescreen.UpdateScreen
import com.example.unitconverterapp.navigation.WhatsAppNavigationSystem
import com.example.unitconverterapp.ui.theme.AccessStringResources
import com.example.unitconverterapp.ui.theme.PartialText
import com.example.unitconverterapp.ui.theme.UnitconverterappTheme
import com.example.unitconverterapp.whatsapp.presentation.RegistrationScreen
import com.example.unitconverterapp.whatsapp.presentation.WelcomeScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            UnitconverterappTheme {
                WhatsAppNavigationSystem()
            }
        }
    }
}




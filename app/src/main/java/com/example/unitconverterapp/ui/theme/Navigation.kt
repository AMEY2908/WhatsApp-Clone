package com.example.unitconverterapp.ui.theme

import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NAV(){
    val navController= rememberNavController()
    NavHost(navController=navController, startDestination = "FirstScreen"){

    }


}
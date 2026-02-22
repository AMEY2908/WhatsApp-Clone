package com.example.unitconverterapp.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.unitconverterapp.callscreen.CallScreen
import com.example.unitconverterapp.communityscreen.CommunityScreen
import com.example.unitconverterapp.homescreen.Homescreen
import com.example.unitconverterapp.homescreen.UpdateScreen
import com.example.unitconverterapp.profile.UserProfileScreen
import com.example.unitconverterapp.profile.UserProfileScreen
import com.example.unitconverterapp.viewmodels.BaseViewModel
import com.example.unitconverterapp.whatsapp.presentation.RegistrationScreen
import com.example.unitconverterapp.whatsapp.presentation.SplashScreen
import com.example.unitconverterapp.whatsapp.presentation.WelcomeScreen


@Composable
fun WhatsAppNavigationSystem() {
    val navController = rememberNavController()

    NavHost(
        startDestination = Routes.SplashScreen,
        navController = navController,
                enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }

    ) {

        composable<Routes.SplashScreen> {
            SplashScreen(navController)
        }
        composable<Routes.WelcomeScreen> {
            WelcomeScreen(navController)
        }

        composable<Routes.UserRegistrationScreen>{
            RegistrationScreen(navController)
        }
        composable<Routes.HomeScreen> {
            val baseViewModel: BaseViewModel= hiltViewModel()
            Homescreen(navController,baseViewModel)
        }
        composable<Routes.UpdateScreen> {
            UpdateScreen(navController)
        }
        composable<Routes.CommunityScreen> {
            CommunityScreen(navController)
        }
        composable<Routes.CallScreen> {
            CallScreen(navController)
        }
        composable<Routes.UserProfileScreen> {

            UserProfileScreen(navHostController = navController)
        }

    }
}
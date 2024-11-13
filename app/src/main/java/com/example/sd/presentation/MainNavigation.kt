package com.example.sd.presentation

import MainScreen
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sd.presentation.authorization.AuthScreen
import com.example.sd.presentation.authorization.AuthViewModel
import com.example.sd.presentation.dashboard.DashboardViewModel
import com.example.sd.presentation.filter.FilterViewModel

@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    val viewModel: AuthViewModel = hiltViewModel()
    val dashboardViewModel: DashboardViewModel = hiltViewModel()
    val filterViewModel: FilterViewModel = hiltViewModel()
    NavHost(navController = navController, startDestination = "splash") {
        composable("login") { AuthScreen(navController) }
        composable("splash") { SplashScreen(navController) }
        composable("load") { LoadingScreen(dashboardViewModel,viewModel,navController) }
        composable("drawer") { MainScreen(viewModel,dashboardViewModel,filterViewModel) }

    }
}
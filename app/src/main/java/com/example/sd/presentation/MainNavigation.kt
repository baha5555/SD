package com.example.sd.presentation

import MainScreen
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sd.presentation.accounts.AccountsViewModel
import com.example.sd.presentation.authorization.AuthScreen
import com.example.sd.presentation.authorization.AuthViewModel
import com.example.sd.presentation.contact.ContactViewModel
import com.example.sd.presentation.createBids.CreateBidsViewModel
import com.example.sd.presentation.dashboard.DashboardViewModel
import com.example.sd.presentation.filter.FilterViewModel
import com.example.sd.presentation.knowledgeBases.KnowledgeBasesViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    val viewModel: AuthViewModel = hiltViewModel()
    val dashboardViewModel: DashboardViewModel = hiltViewModel()
    val filterViewModel: FilterViewModel = hiltViewModel()
    val contactViewModel:ContactViewModel = hiltViewModel()
    val createBidsViewModel: CreateBidsViewModel = hiltViewModel()
    val accountsViewModel:AccountsViewModel = hiltViewModel()
    val knowledgeBasesViewModel: KnowledgeBasesViewModel = hiltViewModel()
    NavHost(navController = navController, startDestination = "splash") {
        composable("login") { AuthScreen(navController) }
        composable("splash") { SplashScreen(navController) }
        composable("load") { LoadingScreen(dashboardViewModel,viewModel, navController = navController,createBidsViewModel = createBidsViewModel, contactViewModel = contactViewModel, knowledgeBasesViewModel = knowledgeBasesViewModel) }
        composable("drawer") { MainScreen(viewModel,dashboardViewModel,filterViewModel,contactViewModel,createBidsViewModel,accountsViewModel,knowledgeBasesViewModel) }

    }
}
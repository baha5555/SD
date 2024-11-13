import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.*
import com.example.sd.R
import com.example.sd.presentation.dashboard.AnalysisScreen
import com.example.sd.presentation.BidsScreen
import com.example.sd.presentation.DetailScreen
import com.example.sd.presentation.filter.FilterScreen
import com.example.sd.presentation.ProfileScreen
import com.example.sd.presentation.SplashScreen
import com.example.sd.presentation.authorization.AuthScreen
import com.example.sd.presentation.authorization.AuthViewModel
import com.example.sd.presentation.authorization.ChangePasswordScreen
import com.example.sd.presentation.authorization.SuccessChangePassword
import com.example.sd.presentation.dashboard.DashboardViewModel
import com.example.sd.presentation.filter.FilterViewModel


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val viewModel: AuthViewModel = hiltViewModel()
    val dashboardViewModel: DashboardViewModel = hiltViewModel()
    val filterViewModel: FilterViewModel = hiltViewModel()

    LaunchedEffect(key1 = true) {
        dashboardViewModel.getDashboard()
        viewModel.getAboutMe()
    }

    Scaffold(
        bottomBar = {
            when (navController.currentBackStackEntryAsState().value?.destination?.route) {
                "BidsScreen", "ReportsScreen", "AnalysisScreen", "ProfileScreen" -> {
                    BottomNavigationBar(navController)
                }
            }
        }
    ) {paddingValues ->
        Log.e("paddingValues","paddingValues -> ${paddingValues}")
        Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
            NavHost(navController, startDestination = "splash") {
                composable("drawer") { MainScreen() }
                composable("login") { AuthScreen(navController) }
                composable("splash") { SplashScreen(navController) }
                composable("AnalysisScreen") { AnalysisScreen(navController, dashboardViewModel) }
                composable("BidsScreen") { BidsScreen(navController, viewModel, filterViewModel) }
                composable("DetailScreen") { DetailScreen(navController, viewModel) }
                composable("ReportsScreen") { /* Экран для отчетов */ }
                composable("ProfileScreen") { ProfileScreen(navController, viewModel) }
                composable("FilterScreen") { FilterScreen(navController, filterViewModel) }
                composable("SuccessChangePassword") { SuccessChangePassword(navController) }
                composable("ChangePasswordScreen") { ChangePasswordScreen(navController, viewModel)
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val selectedColor = Color(0xFF004FC7)
    val unselectedColor = Color.Gray

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route


    BottomNavigation(
        modifier = Modifier.fillMaxHeight(0.1f),
        backgroundColor = Color.White,
        contentColor = Color.Gray, elevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .padding(bottom = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.Top,
        ) {


            BottomNavigationItem(
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_home),
                            contentDescription = "Главная",
                            tint = if (currentRoute == "AnalysisScreen") selectedColor else Color.Unspecified
                        )
                        Spacer(modifier = Modifier.height(8.dp)) // Добавляем паддинг между иконкой и текстом
                        Text(
                            "Главная",
                            style = TextStyle(
                                fontSize = 12.sp,
                                fontFamily = FontFamily(Font(R.font.inter)),
                                fontWeight = FontWeight(500),
                                color = if (currentRoute == "AnalysisScreen") selectedColor else unselectedColor,
                                textAlign = TextAlign.Center,
                            )
                        )
                    }
                },
                selected = currentRoute == "AnalysisScreen",
                onClick = {
                    navController.navigate("AnalysisScreen") {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
            BottomNavigationItem(
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_report),
                            contentDescription = "Обращения",
                            tint = if (currentRoute == "BidsScreen") selectedColor else Color.Unspecified
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Обращения",
                            style = TextStyle(
                                fontSize = 12.sp,
                                fontFamily = FontFamily(Font(R.font.inter)),
                                fontWeight = FontWeight(500),
                                color = if (currentRoute == "BidsScreen") selectedColor else unselectedColor,
                                textAlign = TextAlign.Center,
                            )
                        )
                    }
                },
                selected = currentRoute == "BidsScreen",
                onClick = {
                    navController.navigate("BidsScreen") {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_plus),
                        contentDescription = "Добавить",
                        tint = Color.Unspecified
                    )
                },
                selected = false,
                onClick = { /* Действие для центральной кнопки */ }
            )
            BottomNavigationItem(
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_analysis),
                            contentDescription = "Отчеты",
                            tint = if (currentRoute == "ReportsScreen") selectedColor else Color.Unspecified
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "Отчеты", style = TextStyle(
                                fontSize = 12.sp,
                                fontFamily = FontFamily(Font(R.font.inter)),
                                fontWeight = FontWeight(500),
                                color = if (currentRoute == "ReportsScreen") selectedColor else unselectedColor,
                                textAlign = TextAlign.Center,
                            )
                        )
                    }
                },
                selected = currentRoute == "ReportsScreen",
                onClick = {
                    navController.navigate("ReportsScreen") {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
            BottomNavigationItem(
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_profile),
                            contentDescription = "Профиль",
                            tint = if (currentRoute == "ProfileScreen") selectedColor else Color.Unspecified
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "Профиль", style = TextStyle(
                                fontSize = 12.sp,
                                fontFamily = FontFamily(Font(R.font.inter)),
                                fontWeight = FontWeight(500),
                                color = if (currentRoute == "ProfileScreen") selectedColor else unselectedColor,
                                textAlign = TextAlign.Center,
                            )
                        )
                    }
                },
                selected = currentRoute == "ProfileScreen",
                onClick = {
                    navController.navigate("ProfileScreen") {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.navigation.NavController
import androidx.navigation.compose.*
import com.example.sd.R
import com.example.sd.presentation.dashboard.AnalysisScreen
import com.example.sd.presentation.BidsScreen
import com.example.sd.presentation.DetailScreen
import com.example.sd.presentation.filter.FilterScreen
import com.example.sd.presentation.ProfileScreen
import com.example.sd.presentation.accounts.AccountsViewModel
import com.example.sd.presentation.authorization.AuthViewModel
import com.example.sd.presentation.authorization.ChangePasswordScreen
import com.example.sd.presentation.authorization.SuccessChangePassword
import com.example.sd.presentation.contact.ContactScreen
import com.example.sd.presentation.contact.ContactViewModel
import com.example.sd.presentation.contact.DetailScreenContact
import com.example.sd.presentation.createBids.CreateBidCreateBidsScreenСompletion
import com.example.sd.presentation.createBids.CreateBidsScreen1
import com.example.sd.presentation.createBids.CreateBidsScreen2
import com.example.sd.presentation.createBids.CreateBidsScreen3
import com.example.sd.presentation.createBids.CreateBidsScreen4
import com.example.sd.presentation.createBids.CreateBidsScreen5
import com.example.sd.presentation.createBids.CreateBidsViewModel
import com.example.sd.presentation.dashboard.DashboardViewModel
import com.example.sd.presentation.filter.FilterViewModel
import com.example.sd.presentation.knowledgeBases.KnowledgeBasesDetailScreen
import com.example.sd.presentation.knowledgeBases.KnowledgeBasesFilterScreen
import com.example.sd.presentation.knowledgeBases.KnowledgeBasesScreen
import com.example.sd.presentation.knowledgeBases.KnowledgeBasesViewModel


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", )
@Composable
fun MainScreen(viewModel: AuthViewModel,dashboardViewModel: DashboardViewModel,filterViewModel: FilterViewModel,contactViewModel: ContactViewModel,createBidsViewModel: CreateBidsViewModel,accountsViewModel: AccountsViewModel,knowledgeBasesViewModel: KnowledgeBasesViewModel) {
    val navController = rememberNavController()


    Scaffold(
        bottomBar = {
            when (navController.currentBackStackEntryAsState().value?.destination?.route) {
                "BidsScreen", "ReportsScreen", "AnalysisScreen", "ProfileScreen" -> {

                   Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically){
                       BottomNavigationBar(navController,createBidsViewModel)
                   }
                }
            }
        }
    ) {paddingValues ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)) {
            NavHost(navController, startDestination = "AnalysisScreen") {
                composable("AnalysisScreen") { AnalysisScreen(navController, dashboardViewModel) }
                composable("BidsScreen") { BidsScreen(navController, viewModel, filterViewModel) }
                composable("DetailScreen") { DetailScreen(navController, viewModel) }
                composable("DetailScreenContact") { DetailScreenContact(navController, contactViewModel) }
                composable("ReportsScreen") { /* Экран для отчетов */ }
                composable("ProfileScreen") { ProfileScreen(navController, viewModel) }
                composable("FilterScreen") { FilterScreen(navController, filterViewModel) }
                composable("SuccessChangePassword") { SuccessChangePassword(navController) }
                composable("KnowledgeBasesScreen") { KnowledgeBasesScreen(navController,knowledgeBasesViewModel) }
                composable("KnowledgeBasesDetailScreen") { KnowledgeBasesDetailScreen(navController,knowledgeBasesViewModel) }
                composable("KnowledgeBasesFilterScreen") { KnowledgeBasesFilterScreen(navController,knowledgeBasesViewModel,contactViewModel) }
                composable("ContactScreen") { ContactScreen(navController,contactViewModel,filterViewModel) }
                composable("ChangePasswordScreen") { ChangePasswordScreen(navController, viewModel)}
                composable("step1") { CreateBidsScreen1(navController, viewModel = createBidsViewModel,) }
                composable("step2") {CreateBidsScreen2(navController, viewModel = createBidsViewModel, ) }
                composable("step3") { CreateBidsScreen3(navController, viewModel = createBidsViewModel, searchViewModel = contactViewModel) }
                composable("step4") { CreateBidsScreen4(navController, viewModel = createBidsViewModel, searchViewModel = contactViewModel, accountsViewModel = accountsViewModel) }
                composable("step5") { CreateBidsScreen5(navController, viewModel = createBidsViewModel, searchViewModel = contactViewModel,) }
                composable("step6") { CreateBidCreateBidsScreenСompletion(navController,createBidsViewModel) }
                }
            }
        }
    }


@Composable
fun BottomNavigationBar(navController: NavController,viewModel: CreateBidsViewModel) {
    val selectedColor = Color(0xFF004FC7)
    val unselectedColor = Color.Gray

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route


    BottomNavigation(
        modifier = Modifier
            .fillMaxHeight(0.1f)
            .fillMaxWidth(0.95f),
        backgroundColor = Color.White,
        contentColor = Color.Gray, elevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxHeight().fillMaxWidth()
                .padding(bottom = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
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
                                fontWeight = FontWeight(700),
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
                                fontWeight = FontWeight(700),
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
                selected = currentRoute == "step1",
                onClick = {
                    viewModel.getUUID()
                    viewModel.getEntityNumber("App\\Models\\Bids\\Bid")
                    navController.navigate("step1") {
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
                            painter = painterResource(id = R.drawable.icon_analysis),
                            contentDescription = "Отчеты",
                            tint = if (currentRoute == "ReportsScreen") selectedColor else Color.Unspecified
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "Отчеты", style = TextStyle(
                                fontSize = 12.sp,
                                fontFamily = FontFamily(Font(R.font.inter)),
                                fontWeight = FontWeight(700),
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
                            painter = painterResource(id = R.drawable.icon_more),
                            contentDescription = "Ещё",
                            tint = if (currentRoute == "ProfileScreen") selectedColor else Color.Unspecified
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "Ещё", style = TextStyle(
                                fontSize = 12.sp,
                                fontFamily = FontFamily(Font(R.font.inter)),
                                fontWeight = FontWeight(700),
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

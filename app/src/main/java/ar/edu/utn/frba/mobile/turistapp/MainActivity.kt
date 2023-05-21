package ar.edu.utn.frba.mobile.turistapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ar.edu.utn.frba.mobile.turistapp.ui.home.HomeScreen
import ar.edu.utn.frba.mobile.turistapp.ui.tour.TourScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }
}

@Composable
private fun App() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController = navController)
        }
        composable("tour/{tourId}",
            arguments = listOf(navArgument("tourId") { type = NavType.IntType })
        ) { backStackEntry ->
            val tourId: Int? = backStackEntry.arguments?.getInt("tourId")
            if (tourId is Int)
                TourScreen(tourId, navController)
        }
    }
}
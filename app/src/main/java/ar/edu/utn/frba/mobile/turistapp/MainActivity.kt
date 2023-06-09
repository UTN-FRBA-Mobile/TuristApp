package ar.edu.utn.frba.mobile.turistapp

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ar.edu.utn.frba.mobile.turistapp.core.repository.FavoritesRepository
import ar.edu.utn.frba.mobile.turistapp.core.utils.AudioPlayer
import ar.edu.utn.frba.mobile.turistapp.ui.home.HomeScreen
import ar.edu.utn.frba.mobile.turistapp.ui.locations_map.googleMaps.MapViewModel
import ar.edu.utn.frba.mobile.turistapp.ui.locations_map.map.MapScreen
import ar.edu.utn.frba.mobile.turistapp.ui.login.LoginScreen
import ar.edu.utn.frba.mobile.turistapp.ui.tour.TourScreen
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                viewModel.getDeviceLocation(fusedLocationProviderClient)
            }
        }

    private fun askPermissions() = when {
        ContextCompat.checkSelfPermission(
            this,
            ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED -> {
            viewModel.getDeviceLocation(fusedLocationProviderClient)
        }
        else -> {
            requestPermissionLauncher.launch(ACCESS_FINE_LOCATION)
        }
    }

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val viewModel: MapViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        askPermissions()
            setContent {
            App(viewModel)
        }
        val sharedPref = getSharedPreferences("myPref", MODE_PRIVATE)
        FavoritesRepository.sharedPreference = sharedPref
    }
}

@Composable
private fun App(mapViewModel: MapViewModel) {
    val navController = rememberNavController()
    val audioPlayer = AudioPlayer()

    val currentUser = Firebase.auth.currentUser
    val startDestination = if(currentUser != null) "home" else "login"

    NavHost(navController = navController, startDestination = startDestination) {
        composable("login") {
            LoginScreen(navController = navController)
        }
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
        composable(
            route = "map/{tourId}",
            arguments = listOf(navArgument("tourId") { type = NavType.IntType })
        ) { backStackEntry ->
            val tourId = backStackEntry.arguments?.getInt("tourId")
            if (tourId is Int)
                MapScreen(mapViewModel, audioPlayer, tourId, navController)
        }
    }
}
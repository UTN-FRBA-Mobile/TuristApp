package ar.edu.utn.frba.mobile.turistapp.ui.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import ar.edu.utn.frba.mobile.turistapp.R
import ar.edu.utn.frba.mobile.turistapp.ui.theme.TuristAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(
    title: String? = null,
    navController: NavController? = null,
    content: @Composable (PaddingValues) -> Unit) {
    val navigationIcon: (@Composable () -> Unit) = {
        IconButton(onClick = {
            if (navController?.previousBackStackEntry != null)
                navController.popBackStack()
        }) {
            Icons.Filled.ArrowBack
        }
    }
    TuristAppTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = title ?: stringResource(id = R.string.app_name))
                    },
                    navigationIcon = navigationIcon,
                )
            },
            content = {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    content(it)
                }
            }
        )
    }
}
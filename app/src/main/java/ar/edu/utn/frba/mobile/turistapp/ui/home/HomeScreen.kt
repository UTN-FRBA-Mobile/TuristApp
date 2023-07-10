package ar.edu.utn.frba.mobile.turistapp.ui.home

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ar.edu.utn.frba.mobile.turistapp.R
import ar.edu.utn.frba.mobile.turistapp.core.models.MinifiedTour
import ar.edu.utn.frba.mobile.turistapp.core.utils.AvailableLanguages
import ar.edu.utn.frba.mobile.turistapp.core.utils.LocaleUtils
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(viewModel: ToursViewModel = viewModel(), navController: NavController? = null) {
    val selectedLanguage = remember { mutableStateOf(LocaleUtils.currentLocale()) }
    val favoriteToursState = viewModel.favoriteTours.observeAsState()

    //LaunchedEffect se encargará de llamar a getNearbyTours cada vez que cambie selectedLanguage.
    // Así, cuando el usuario seleccione un nuevo idioma, se buscarán los tours en el idioma
    // seleccionado y se actualizará la UI.
    LaunchedEffect(selectedLanguage.value) {
        viewModel.getNearbyTours(selectedLanguage.value)
    }

    val nearbyToursState = viewModel.nearbyTours.observeAsState()
    val nearbyTours = nearbyToursState.value
    val favoriteTours = favoriteToursState.value

    HomeScreenView(nearbyTours, favoriteTours, navController)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenView(
    nearbyTours: List<MinifiedTour>?,
    favoriteTours: List<MinifiedTour>?,
    navController: NavController? = null
) {
    val openLanguageDialog = remember { mutableStateOf(false) }
    val selectedLanguage = remember { mutableStateOf(LocaleUtils.currentLocale()) }

    val token = "754150192109-f0a45itg6kbg7fig4iisqqjqav3drblo.apps.googleusercontent.com"
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.tours))
                },
                modifier = Modifier.border(1.dp, Color.Gray),
                actions = {
                    IconButton(onClick = {
                        openLanguageDialog.value = true
                    }) {
                        Icon(
                            painter = ChangeIconLanguage(selectedLanguage.value),
                            tint = Color.Unspecified,
                            contentDescription = stringResource(R.string.selectLanguage)
                        )
                    }
                    IconButton(onClick = {
                        GoogleSignIn.getClient(context, GoogleSignInOptions
                            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestIdToken(token)
                            .requestEmail()
                            .build()).revokeAccess()
                        navController?.navigate("login")
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.logout),
                            tint = Color.Unspecified,
                            contentDescription = "Cerrar sesión"
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(64.dp))
            if (nearbyTours != null && favoriteTours != null) {
                Tours(nearbyTours, favoriteTours, navController)
            } else {
                Loading()
            }
        }
    }
    if (openLanguageDialog.value) {
        LanguageDialog(openLanguageDialog, selectedLanguage)
    }
}

@SuppressLint("DiscouragedApi")
@Composable
fun LanguageDialog(
    openLanguageDialog: MutableState<Boolean>,
    selectedLanguage: MutableState<AvailableLanguages>
) {
    val languages = AvailableLanguages.values()
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = {
            openLanguageDialog.value = false
        },
        title = {
            Text(text = stringResource(R.string.selectLanguage))
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectableGroup()
            ) {
                languages.forEach { item ->
                    LabelledRadioButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = item == selectedLanguage.value, // Comparar con el valor actual de selectedLanguage
                                onClick = {
                                    LocaleUtils.setLocale(context, item)
                                    selectedLanguage.value = item
                                    (context as? Activity)?.recreate()
                                },
                                role = Role.RadioButton
                            ),
                        label = stringResource(
                            id = context.resources.getIdentifier(
                                item.resStringName,
                                "string",
                                context.packageName
                            )
                        ),
                        selected = item == selectedLanguage.value // Comparar con el valor actual de selectedLanguage
                    )
                }
            }
        },
        confirmButton = {
        }
    )
}

@Composable
fun LabelledRadioButton(
    modifier: Modifier = Modifier,
    label: String,
    selected: Boolean,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = modifier.height(56.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors()
        )
        Text(
            text = label,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Composable
fun Tours(
    nearbyTours: List<MinifiedTour>,
    favoriteTours: List<MinifiedTour>,
    navController: NavController? = null
) {
    LazyColumn {
        item {
            Text(
                text = stringResource(R.string.nearbyTours),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
            )
        }
        nearbyTours.forEach { tour ->
            item { TourRow(tour, navController) }
        }
        item {
            Text(
                text = stringResource(R.string.favorites),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
            )
        }
        favoriteTours.forEach { tour ->
            item { FavoriteTourRow(tour, navController) }
        }
        item {
            NoFavorites(isVisible = favoriteTours.isEmpty())
        }
    }
}

@Composable
fun NoFavorites(isVisible: Boolean) {
    if(isVisible){
        Text(
            text = stringResource(R.string.nofavorites),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
        )
    }
}

@Composable
fun ChangeIconLanguage(language: AvailableLanguages): Painter {
    if (language == AvailableLanguages.English) {
        return painterResource(R.drawable.us)
    }
    return painterResource(R.drawable.es)
}

@Composable
fun Loading() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}


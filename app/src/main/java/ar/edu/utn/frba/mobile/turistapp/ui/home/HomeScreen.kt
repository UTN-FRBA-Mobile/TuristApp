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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
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
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ar.edu.utn.frba.mobile.turistapp.R
import ar.edu.utn.frba.mobile.turistapp.core.api.MockToursAPI
import ar.edu.utn.frba.mobile.turistapp.core.models.MinifiedTour
import ar.edu.utn.frba.mobile.turistapp.core.utils.AvailableLanguages
import ar.edu.utn.frba.mobile.turistapp.core.utils.LocaleUtils

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(viewModel: ToursViewModel = viewModel(), navController: NavController? = null) {
    val nearbyToursState = viewModel.nearbyTours.observeAsState()
    val favoriteToursState = viewModel.favoriteTours.observeAsState()
    val nearbyTours = nearbyToursState.value
    val favoriteTours = favoriteToursState.value
    HomeScreenView(nearbyTours, favoriteTours, navController)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenView(nearbyTours: List<MinifiedTour>?, favoriteTours: List<MinifiedTour>?, navController: NavController? = null) {
    val openLanguageDialog = remember { mutableStateOf(false) }
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
                            imageVector = Icons.Filled.Settings,
                            contentDescription = stringResource(R.string.selectLanguage)
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
        LanguageDialog(openLanguageDialog)
    }
}

@SuppressLint("DiscouragedApi")
@Composable
fun LanguageDialog(openLanguageDialog: MutableState<Boolean>) {
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
            Column(modifier = Modifier
                .fillMaxWidth()
                .selectableGroup()) {
                languages.forEach { item ->
                    LabelledRadioButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = item == LocaleUtils.currentLocale(),
                                onClick = {
                                    LocaleUtils.setLocale(context, item)
                                    (context as? Activity)?.recreate()
                                },
                                role = Role.RadioButton
                            ),
                        label = stringResource(id = context.resources.getIdentifier(item.resStringName, "string", context.packageName)),
                        selected = item == LocaleUtils.currentLocale()
                    )
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    openLanguageDialog.value = false
                }
            ) {
                Text(stringResource(R.string.confirm))
            }
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
fun Tours(nearbyTours: List<MinifiedTour>, favoriteTours: List<MinifiedTour>, navController: NavController? = null) {
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
            item { TourRow(tour, navController) }
        }
    }
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

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreenView(MockToursAPI.sampleTours(), MockToursAPI.sampleTours())
}
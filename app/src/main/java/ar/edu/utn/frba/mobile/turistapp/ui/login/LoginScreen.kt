package ar.edu.utn.frba.mobile.turistapp.ui.login

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ar.edu.utn.frba.mobile.turistapp.R
import ar.edu.utn.frba.mobile.turistapp.core.utils.AvailableLanguages
import ar.edu.utn.frba.mobile.turistapp.core.utils.LocaleUtils
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(viewModel: LoginScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel(), navController: NavController) {
    LoginScreenView(viewModel, navController)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreenView(viewModel: LoginScreenViewModel, navController: NavController? = null) {
    val openLanguageDialog = remember { mutableStateOf(false) }
    val selectedLanguage = remember { mutableStateOf(LocaleUtils.currentLocale()) }

    val token = "754150192109-f0a45itg6kbg7fig4iisqqjqav3drblo.apps.googleusercontent.com"
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            viewModel.signInWithGoogleCredentials(credential) {
                navController?.navigate("home")
            }
        }
        catch (ex: Exception) {
            Log.d("AUTH", "Login error ${ex.localizedMessage}")
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { stringResource(R.string.app_name) },
                actions = {
                    IconButton(onClick = {
                        openLanguageDialog.value = true
                    }) {
                        Icon(
                            painter = changeIconLanguage(selectedLanguage.value),
                            tint = Color.Unspecified,
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
            Text(
                text = stringResource(R.string.welcome),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold)
            Image(
                painter = painterResource(R.drawable.ic_turistapp),
                contentDescription = stringResource(R.string.app_name)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(10.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable {
                        val options = GoogleSignInOptions
                            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestIdToken(token)
                            .requestEmail()
                            .build()
                        val googleSignInClient = GoogleSignIn.getClient(context, options)
                        launcher.launch(googleSignInClient.signInIntent)
                    }
                    .border(1.dp, Color.Gray, RoundedCornerShape(10.dp))
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_google),
                    contentDescription = stringResource(R.string.googleLogin),
                    modifier = Modifier
                        .padding(10.dp)
                        .size(40.dp)
                )
                Text(
                    text = stringResource(R.string.googleLogin),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(10.dp)
                )
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
                                    selectedLanguage.value =
                                        item // Actualizar el valor de selectedLanguage cuando se selecciona un nuevo item
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
fun changeIconLanguage(language: AvailableLanguages): Painter {
    return if (language == AvailableLanguages.English)
        painterResource(R.drawable.us)
    else
        painterResource(R.drawable.es)
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreenView(LoginScreenViewModel())
}

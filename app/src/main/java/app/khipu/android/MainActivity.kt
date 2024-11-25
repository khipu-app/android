package app.khipu.android

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import app.khipu.android.login.LoginScreen
import app.khipu.android.login.LoginViewModel
import app.khipu.android.ui.theme.KhipuTheme
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalFoundationApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val loginViewModel by viewModels<LoginViewModel>()

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        if (resources.getBoolean(R.bool.portrait_only)) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

        setContent {
            KhipuTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LoginScreen(
                        modifier = Modifier.padding(innerPadding),
                        login = loginViewModel.login.observeAsState().value ?: "",
                        onLoginChange = { loginViewModel.login.value = it },
                        password = loginViewModel.password.observeAsState().value ?: "",
                        onPasswordChange = { loginViewModel.password.value = it },
                        ipDomain = loginViewModel.ipDomain.observeAsState().value ?: "",
                        onIpDomainChange = { loginViewModel.ipDomain.value = it },
                        onLoginClick = { /*TODO*/ },
                        onChangePasswordClick = { /*TODO*/ },
                        onRegisterClick = { /*TODO*/ },
                        onAppleClick = { /*TODO*/ },
                        onGoogleClick = { /*TODO*/ },
                        onDropboxClick = { /*TODO*/ },
                        onYandexDriveClick = { /*TODO*/ },
                        onSberDriveClick = { /*TODO*/ },
                        onMailruCloudClick = { /*TODO*/ },
                        onFingerprintClick = { /*TODO*/ },
                        personalVaultCode = loginViewModel.personalVaultCode.observeAsState().value ?: "",
                        onPersonalVaultCodeValueChange = { if (it.length <= 4) loginViewModel.personalVaultCode.value = it }
                    )
                }
            }
        }
    }
}

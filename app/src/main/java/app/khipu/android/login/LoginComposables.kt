package app.khipu.android.login

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import app.khipu.android.R
import app.khipu.android.ui.StandardAppButton
import app.khipu.android.ui.StandardAppOutlinedTextField
import app.khipu.android.ui.theme.PanelBackground
import app.khipu.android.ui.theme.PlaceholderColor
import app.khipu.android.ui.theme.commented
import app.khipu.android.ui.theme.screenHeader
import app.khipu.android.ui.theme.screenTitle
import kotlinx.coroutines.launch

//todo test adaptive design on different devices to ensure equal UI
//todo adapt interface for landscape orientation
@ExperimentalFoundationApi
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    login: String,
    onLoginChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onChangePasswordClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onAppleClick: () -> Unit,
    onGoogleClick: () -> Unit,
    onVkClick: () -> Unit,
    onYandexClick: () -> Unit,
    onSberClick: () -> Unit,
    onQrClick: () -> Unit,
    onNfcClick: () -> Unit,
    onFingerprintClick: () -> Unit,
    onFaceClick: () -> Unit,
    onSignatureClick: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { 3 })
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.padding(top = 21.dp),
            text = stringResource(id = R.string.enter) + "...",
            style = screenTitle
        )
        Row(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            StandardAppButton(
                modifier = Modifier
                    .size(36.dp),
                content = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = Icons.AutoMirrored.Filled.KeyboardArrowLeft.name
                    )
                }) {
                coroutineScope.launch {
                    val previousPageIndex =
                        if (pagerState.currentPage == 0) pagerState.pageCount - 1 else pagerState.currentPage - 1
                    keyboardController?.hide()
                    pagerState.animateScrollToPage(previousPageIndex)
                }
            }
            Column(
                modifier = Modifier.width(221.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                HorizontalPager(
                    state = pagerState,
                    userScrollEnabled = false
                ) {
                    when (it) {
                        0 -> PasswordLogin(
                            login = login,
                            onLoginChange = onLoginChange,
                            password = password,
                            onPasswordChange = onPasswordChange,
                            onLoginClick = onLoginClick,
                            onChangePasswordClick = onChangePasswordClick,
                            onRegisterClick = onRegisterClick
                        )

                        1 -> ServiceIdLogin(
                            onAppleClick = onAppleClick,
                            onGoogleClick = onGoogleClick,
                            onVkClick = onVkClick,
                            onYandexClick = onYandexClick,
                            onSberClick = onSberClick
                        )

                        2 -> ScanningLogin(
                            onQrClick = onQrClick,
                            onNfcClick = onNfcClick,
                            onFingerprintClick = onFingerprintClick,
                            onFaceClick = onFaceClick,
                            onSignatureClick = onSignatureClick
                        )
                    }
                }
                PageIndicator(
                    modifier = Modifier.padding(top = 23.dp),
                    pageCount = pagerState.pageCount,
                    currentPage = pagerState.currentPage
                )
            }
            StandardAppButton(
                modifier = Modifier
                    .size(36.dp),
                content = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = Icons.AutoMirrored.Filled.KeyboardArrowRight.name
                    )
                }) {
                coroutineScope.launch {
                    val nextPageIndex =
                        if (pagerState.currentPage == pagerState.pageCount - 1) 0 else pagerState.currentPage + 1
                    keyboardController?.hide()
                    pagerState.animateScrollToPage(nextPageIndex)
                }
            }
        }
        Spacer(modifier = Modifier.size(1.dp))
    }
}

@Composable
fun PageIndicator(
    modifier: Modifier = Modifier,
    pageCount: Int,
    currentPage: Int
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (pageIndex in 0 until pageCount) {
            Surface(
                modifier = Modifier.size(6.dp),
                shape = CircleShape,
                color = if (currentPage == pageIndex) PanelBackground else PlaceholderColor,
                content = {}
            )

            if (pageIndex != pageCount - 1) {
                Spacer(modifier = Modifier.size(6.dp))
            }
        }
    }
}

@Composable
fun PasswordLogin(
    modifier: Modifier = Modifier,
    login: String,
    onLoginChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onChangePasswordClick: () -> Unit,
    onRegisterClick: () -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(modifier = Modifier.padding(bottom = 40.dp), text = stringResource(id = R.string.with_login_password), style = screenHeader)
        Column(
            modifier = Modifier.width(221.dp)
        ) {
            StandardAppOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                text = login,
                onTextChange = onLoginChange,
                hint = stringResource(id = R.string.username),
                keyboardOptions = KeyboardOptions(autoCorrect = false, imeAction = ImeAction.Next)
            )
            Spacer(modifier = Modifier.size(20.dp))
            StandardAppOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                text = password,
                onTextChange = onPasswordChange,
                hint = stringResource(id = R.string.password),
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                visualTransformation = PasswordVisualTransformation('\u002A')
            )
        }
        Column(
            modifier = Modifier.width(221.dp).padding(top = 46.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            StandardAppButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.enter).lowercase(),
                onClick = onLoginClick
            )
            Text(
                modifier = Modifier
                    .padding(top = 14.dp)
                    .clickable(onClick = onChangePasswordClick),
                text = stringResource(id = R.string.dont_remember_password),
                style = commented
            )
            Text(
                modifier = Modifier
                    .padding(top = 14.dp)
                    .clickable(onClick = onRegisterClick),
                text = stringResource(id = R.string.registration),
                style = commented
            )
        }
    }
}

@Composable
fun ServiceIdLogin(
    modifier: Modifier = Modifier,
    onAppleClick: () -> Unit,
    onGoogleClick: () -> Unit,
    onVkClick: () -> Unit,
    onYandexClick: () -> Unit,
    onSberClick: () -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(modifier = Modifier.padding(bottom = 40.dp), text = stringResource(id = R.string.with_service_id), style = screenHeader)

        Column(
            modifier = Modifier.width(221.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            StandardAppButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.apple),
                onClick = onAppleClick
            )
            StandardAppButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.google),
                onClick = onGoogleClick
            )
            StandardAppButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.VK),
                onClick = onVkClick
            )
            StandardAppButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.yandex),
                onClick = onYandexClick
            )
            StandardAppButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.sber),
                onClick = onSberClick
            )
        }
    }
}

@Composable
fun ScanningLogin(
    modifier: Modifier = Modifier,
    onQrClick: () -> Unit,
    onNfcClick: () -> Unit,
    onFingerprintClick: () -> Unit,
    onFaceClick: () -> Unit,
    onSignatureClick: () -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(modifier = Modifier.padding(bottom = 40.dp), text = stringResource(id = R.string.with_scanning), style = screenHeader)

        Column(
            modifier = Modifier.width(221.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            StandardAppButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.qr_code),
                onClick = onQrClick
            )
            StandardAppButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.nfc_tag),
                onClick = onNfcClick
            )
            StandardAppButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.fingerprint),
                onClick = onFingerprintClick
            )
            StandardAppButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.face),
                onClick = onFaceClick
            )
            StandardAppButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.signature),
                onClick = onSignatureClick
            )
        }
    }
}
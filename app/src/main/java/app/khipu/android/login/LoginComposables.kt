package app.khipu.android.login

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Backspace
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Fingerprint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.khipu.android.R
import app.khipu.android.ui.StandardAppButton
import app.khipu.android.ui.StandardAppOutlinedTextField
import app.khipu.android.ui.theme.PanelBackground
import app.khipu.android.ui.theme.PinDotActiveColor
import app.khipu.android.ui.theme.PinDotInactiveColor
import app.khipu.android.ui.theme.PlaceholderColor
import app.khipu.android.ui.theme.commented
import app.khipu.android.ui.theme.screenHeader
import app.khipu.android.ui.theme.screenTitle
import kotlinx.coroutines.launch

//todo test adaptive design on different devices to ensure equal UI
@ExperimentalFoundationApi
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    login: String,
    onLoginChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    ipDomain: String,
    onIpDomainChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onChangePasswordClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onAppleClick: () -> Unit,
    onGoogleClick: () -> Unit,
    onDropboxClick: () -> Unit,
    onYandexDriveClick: () -> Unit,
    onSberDriveClick: () -> Unit,
    onMailruCloudClick: () -> Unit,
    onFingerprintClick: () -> Unit,
    personalVaultCode: String,
    onPersonalVaultCodeValueChange: (String) -> Unit,
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
            horizontalArrangement = Arrangement.spacedBy(30.dp),
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
                    modifier = Modifier
                        .testTag("Pager"),
                    state = pagerState,
                    userScrollEnabled = false
                ) {
                    when (it) {
                        0 -> LoginOnServer(
                            modifier = Modifier
                                .testTag("LoginOnServer"),
                            login = login,
                            onLoginChange = onLoginChange,
                            password = password,
                            onPasswordChange = onPasswordChange,
                            ipDomain = ipDomain,
                            onIpDomainChange = onIpDomainChange,
                            onLoginClick = onLoginClick,
                            onChangePasswordClick = onChangePasswordClick,
                            onRegisterClick = onRegisterClick
                        )

                        1 -> CloudStorageLogin(
                            modifier = Modifier
                                .testTag("CloudStorageLogin"),
                            onAppleClick = onAppleClick,
                            onGoogleClick = onGoogleClick,
                            onDropboxClick = onDropboxClick,
                            onYandexDriveClick = onYandexDriveClick,
                            onSberDriveClick = onSberDriveClick,
                            onMailruCloudClick = onMailruCloudClick
                        )

                        2 -> PersonalVaultLogin(
                            modifier = Modifier
                                .testTag("PersonalVaultLogin"),
                            personalVaultCode = personalVaultCode,
                            onPersonalVaultCodeValueChange = onPersonalVaultCodeValueChange,
                            onFingerprintClick = onFingerprintClick
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
                color = animateColorAsState(if (currentPage == pageIndex) PanelBackground else PlaceholderColor).value,
                content = {}
            )

            if (pageIndex != pageCount - 1) {
                Spacer(modifier = Modifier.size(6.dp))
            }
        }
    }
}

@Composable
fun LoginOnServer(
    modifier: Modifier = Modifier,
    login: String,
    onLoginChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    ipDomain: String,
    onIpDomainChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    onChangePasswordClick: () -> Unit,
    onRegisterClick: () -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(bottom = 40.dp),
            text = stringResource(id = R.string.on_server),
            style = screenHeader
        )
        Column(
            modifier = Modifier.width(221.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            StandardAppOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                text = login,
                onTextChange = onLoginChange,
                hint = stringResource(id = R.string.username),
                keyboardOptions = KeyboardOptions(autoCorrect = false, imeAction = ImeAction.Next)
            )
            StandardAppOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                text = password,
                onTextChange = onPasswordChange,
                hint = stringResource(id = R.string.password),
                keyboardOptions = KeyboardOptions(
                    autoCorrect = false,
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                visualTransformation = PasswordVisualTransformation('\u002A')
            )
            StandardAppOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                text = ipDomain,
                onTextChange = onIpDomainChange,
                hint = stringResource(id = R.string.ip_domain),
                keyboardOptions = KeyboardOptions(autoCorrect = false, imeAction = ImeAction.Done)
            )
        }
        Column(
            modifier = Modifier
                .width(221.dp)
                .padding(top = 46.dp),
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
fun CloudStorageLogin(
    modifier: Modifier = Modifier,
    onAppleClick: () -> Unit,
    onGoogleClick: () -> Unit,
    onDropboxClick: () -> Unit,
    onYandexDriveClick: () -> Unit,
    onSberDriveClick: () -> Unit,
    onMailruCloudClick: () -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(bottom = 40.dp),
            text = stringResource(id = R.string.in_cloud_storage),
            style = screenHeader,
            overflow = TextOverflow.Ellipsis
        )

        Column(
            modifier = Modifier.width(221.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            StandardAppButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.apple_icloud),
                onClick = onAppleClick
            )
            StandardAppButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.google_drive),
                onClick = onGoogleClick
            )
            StandardAppButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.dropbox),
                onClick = onDropboxClick
            )
            StandardAppButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.yandex_drive),
                onClick = onYandexDriveClick
            )
            StandardAppButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.sber_drive),
                onClick = onSberDriveClick
            )
            StandardAppButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.mailru_cloud),
                onClick = onMailruCloudClick
            )
        }
    }
}

@Composable
fun PinIndicator(
    modifier: Modifier = Modifier,
    //todo maybe add to constants?
    pinLength: Int = 4,
    currentLength: Int,
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (dotPosition in 1..pinLength) {
            item {
                Surface(
                    modifier = Modifier.size(6.dp),
                    shape = CircleShape,
                    color = animateColorAsState(targetValue = if (dotPosition <= currentLength) PinDotActiveColor else PinDotInactiveColor).value,
                    content = {}
                )

                if (dotPosition != pinLength) {
                    Spacer(modifier = Modifier.width(12.dp))
                }
            }
        }
    }
}

@Composable
fun PersonalVaultLogin(
    modifier: Modifier = Modifier,
    personalVaultCode: String,
    onPersonalVaultCodeValueChange: (String) -> Unit,
    onFingerprintClick: () -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(bottom = 40.dp),
            text = stringResource(id = R.string.in_personal_vault),
            style = screenHeader
        )

        PinIndicator(
            modifier = Modifier.padding(bottom = 40.dp),
            currentLength = personalVaultCode.length
        )

        LazyVerticalGrid(
            modifier = Modifier.width(221.dp),
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.Center,
            horizontalArrangement = Arrangement.Center,
        ) {
            for (number in 1..9) {
                item {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        StandardAppButton(
                            modifier = Modifier
                                .requiredSize(45.dp),
                            content = {
                                Text(
                                    text = number.toString(),
                                    style = screenHeader.copy(lineHeight = 30.sp, fontSize = 25.sp)
                                )
                            }
                        ) {
                            onPersonalVaultCodeValueChange(personalVaultCode + number.toString())
                        }
                        Spacer(
                            modifier = Modifier
                                .height(25.dp)
                        )
                    }
                }
            }
            item {
                StandardAppButton(
                    modifier = Modifier
                        .requiredSize(45.dp),
                    content = {
                        Icon(
                            modifier = Modifier.size(22.5.dp),
                            imageVector = Icons.Default.Fingerprint,
                            contentDescription = Icons.Default.Fingerprint.name,
                            tint = Color.White
                        )
                    },
                    onClick = onFingerprintClick
                )
            }
            item {
                StandardAppButton(
                    modifier = Modifier
                        .requiredSize(45.dp),
                    content = {
                        Text(
                            text = "0",
                            style = screenHeader.copy(lineHeight = 30.sp, fontSize = 25.sp)
                        )
                    }
                ) {
                    onPersonalVaultCodeValueChange(personalVaultCode + "0")
                }
            }
            item {
                StandardAppButton(
                    modifier = Modifier
                        .requiredSize(45.dp),
                    content = {
                        Icon(
                            modifier = Modifier.size(15.dp),
                            imageVector = Icons.AutoMirrored.Filled.Backspace,
                            contentDescription = Icons.AutoMirrored.Filled.Backspace.name,
                            tint = Color.White
                        )
                    }
                ) {
                    onPersonalVaultCodeValueChange(personalVaultCode.dropLast(1))
                }
            }
        }
    }
}
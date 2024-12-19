@file:OptIn(ExperimentalFoundationApi::class)

package app.khipu

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.hasAnyChild
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import app.khipu.android.login.LoginScreen
import org.junit.Rule
import org.junit.Test

class LoginScreenTests {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loginScreenTest_enterMethodsAreChangingViaRightButton() {
        composeTestRule.setContent {
            LoginScreen(
                login = "",
                onLoginChange = {},
                password = "",
                onPasswordChange = {},
                ipDomain = "",
                onIpDomainChange = {},
                onLoginClick = {},
                onChangePasswordClick = {},
                onRegisterClick = {},
                onAppleClick = {},
                onGoogleClick = {},
                onDropboxClick = {},
                onYandexDriveClick = {},
                onSberDriveClick = {},
                onMailruCloudClick = {},
                onFingerprintClick = {},
                personalVaultCode = "",
                onPersonalVaultCodeValueChange = {}
            )
        }

        //todo add left button checks
        //todo dynamic page count?
        //todo check for PageIndicator to change colors
        composeTestRule
            .onNodeWithTag(testTag = "Pager")
            .assert(hasAnyChild(hasTestTag("LoginOnServer")))
        composeTestRule
            .onNodeWithContentDescription(Icons.AutoMirrored.Filled.KeyboardArrowRight.name)
            .performClick()
        composeTestRule
            .onNodeWithTag(testTag = "Pager")
            .assert(hasAnyChild(hasTestTag("CloudStorageLogin")))
        composeTestRule
            .onNodeWithContentDescription(Icons.AutoMirrored.Filled.KeyboardArrowRight.name)
            .performClick()
        composeTestRule
            .onNodeWithTag(testTag = "Pager")
            .assert(hasAnyChild(hasTestTag("PersonalVaultLogin")))
        composeTestRule
            .onNodeWithContentDescription(Icons.AutoMirrored.Filled.KeyboardArrowRight.name)
            .performClick()
        composeTestRule
            .onNodeWithTag(testTag = "Pager")
            .assert(hasAnyChild(hasTestTag("LoginOnServer")))
        composeTestRule
            .onNodeWithContentDescription(Icons.AutoMirrored.Filled.KeyboardArrowRight.name)
            .performClick()
    }
}
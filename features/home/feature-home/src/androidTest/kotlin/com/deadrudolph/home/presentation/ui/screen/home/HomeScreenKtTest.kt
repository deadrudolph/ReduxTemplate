package com.deadrudolph.home.presentation.ui.screen.home

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.lifecycle.viewmodel.compose.viewModel
import com.deadrudolph.home.di.component.HomeComponentHolder
import com.deadrudolph.uicomponents.compose.theme.DefaultTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

internal class HomeScreenKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun homeScreenFetchContent() {
        composeTestRule.setContent {
            DefaultTheme {
                val factory = HomeComponentHolder.getInternal().provideVF()
                HomeScreen(store = viewModel(factory = factory))
            }
        }

        runTest {
            composeTestRule.onNodeWithContentDescription("Loading Dialog").assertExists()
            launch(Dispatchers.IO) {
                delay(5000)
                composeTestRule.onNodeWithContentDescription("Content").assertExists()
                composeTestRule.onNodeWithContentDescription("Users List")
                    .onChildren()
                    .assertCountEquals(6)
                composeTestRule.onAllNodesWithContentDescription("User Item")
                    .assertCountEquals(6)
            }
        }
    }
}

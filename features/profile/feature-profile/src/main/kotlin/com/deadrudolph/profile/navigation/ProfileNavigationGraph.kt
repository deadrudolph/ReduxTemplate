package com.deadrudolph.profile.navigation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.deadrudolph.profile.di.component.ProfileComponentHolder
import com.deadrudolph.profile.presentation.ui.screen.first.FirstProfileScreen
import com.deadrudolph.profile.presentation.ui.screen.second.SecondProfileScreen

fun NavGraphBuilder.addProfileFeatureGraph(popBackStack: () -> Unit) {
    val factory = ProfileComponentHolder.getInternal().provideVF()
    navigation(
        startDestination = ProfileNavTarget.ProfileFirstFeature.route.route,
        route = ProfileNavTarget.moduleRoute.route
    ) {
        val userIdKey = "userId"

        composable(ProfileNavTarget.ProfileFirstFeature.route.route) {
            FirstProfileScreen(
                store = viewModel(factory = factory)
            )
        }
        composable(
            "${ProfileNavTarget.ProfileSecondFeature.route.route}/{$userIdKey}",
            arguments = listOf(navArgument(userIdKey) { type = NavType.StringType })
        ) { entry ->
            SecondProfileScreen(
                store = viewModel(factory = factory),
                popBackStack = popBackStack,
                userId = entry.arguments?.getString(userIdKey, "").orEmpty()
            )
        }
    }
}

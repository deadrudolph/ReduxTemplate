package com.deadrudolph.template_redux.navigation.host

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.deadrudolph.home.navigation.addHomeFeatureGraph
import com.deadrudolph.navigation.GlobalNavTarget
import com.deadrudolph.navigation.Navigator
import com.deadrudolph.profile.navigation.addProfileFeatureGraph
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun NavigationComponent(
    navController: NavHostController,
    navigator: Navigator,
) {
    /**
     * With LaunchedEffect we create a CoroutineScope which is started as soon as our composable
     * component is in created state
     * and canceled as soon as the the view exits composition.
     * As a result, whenever Navigator.navigateTo() is called, this snippet listens to it and
     * performs the actual transition.
     */
    LaunchedEffect("navigation") {
        navigator.targetsSharedFlow.onEach {
            navController.navigate(it.route) {
                // Important so that the backstack consists of unique nav entries
                // (pay attention to parameters)
                popUpTo(it.route)
            }
        }.launchIn(this)
    }

    // Navigation Directions
    NavHost(
        navController = navController,
        startDestination = GlobalNavTarget.HomeModule.target.route
    ) {
        addHomeFeatureGraph { navController.popBackStack() }
        addProfileFeatureGraph { navController.popBackStack() }
    }
}

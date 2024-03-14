package com.deadrudolph.navigation

/**
 * Class which represents a route. Can be extended to hold other data
 * */
open class NavTarget(val route: String) {

    class Builder {
        private val route = StringBuilder()

        fun addDestination(destination: String): Builder {
            require(route.isBlank()) {
                "Route must be empty to add the destination"
            }
            route.append(destination)
            return this
        }

        fun addArgument(argument: String): Builder {
            require(route.isNotBlank()) {
                "Route must not be empty to add arguments"
            }
            route.append("/$argument")
            return this
        }

        fun build(): NavTarget = NavTarget(route.toString())
    }
}

/**
 * Represents a global NavTarget which is a module entry point
 * */
sealed class GlobalNavTarget(
    val target: NavTarget,
) {
    data object HomeModule : GlobalNavTarget(target = NavTarget("home_module"))
    data object ProfileModule : GlobalNavTarget(target = NavTarget("profile_module"))
}

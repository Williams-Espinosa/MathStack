package com.williamsel.mathstack.core.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.williamsel.mathstack.features.private.creategroups.presentacion.screens.CreategroupsScreen
import com.williamsel.mathstack.features.private.groups.presentacion.screens.GroupsScreen
import com.williamsel.mathstack.features.public.login.presentation.screen.LoginScreen
import com.williamsel.mathstack.features.public.register.presentacion.screens.RegisterScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Route.Login
    ) {
        composable<Route.Login> {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Route.Home) {
                        popUpTo(Route.Login) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Route.Register)
                }
            )
        }
        composable<Route.Register> {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(Route.Home) {
                        popUpTo(Route.Register) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.popBackStack()
                }
            )
        }
        composable<Route.Home> {
            GroupsScreen(
                onCreateGroup = {
                    navController.navigate(Route.CreateGroup)
                }
            )
        }

        composable<Route.Groups> {
            GroupsScreen(
                onCreateGroup = {
                    navController.navigate(Route.CreateGroup)
                }
            )
        }

        composable<Route.CreateGroup> {
            CreategroupsScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable<Route.GroupDetails> {
            Text(text = "Detalles del grupo")
        }
    }
}

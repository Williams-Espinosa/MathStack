package com.williamsel.mathstack.core.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.williamsel.mathstack.features.creategroups.presentacion.screens.CreategroupsScreen
import com.williamsel.mathstack.features.groups.presentacion.screens.GroupsScreen
import com.williamsel.mathstack.features.auth.login.presentation.screen.LoginScreen
import com.williamsel.mathstack.features.auth.register.presentacion.screens.RegisterScreen
import com.williamsel.mathstack.features.auth.termsandconditions.presentacion.screens.TermsandconditionsScreen

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
                },
                onNavigateToTerms = {
                    navController.navigate(Route.TermsAndConditions)
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
                },
                onNavigateToTerms = {
                    navController.navigate(Route.TermsAndConditions)
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

        composable<Route.TermsAndConditions> {
            TermsandconditionsScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable<Route.GroupDetails> {
            Text(text = "Detalles del grupo")
        }
    }
}

package com.mtovar.acividadesapp.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mtovar.acividadesapp.ui.screens.ActivityDetailScreen
import com.mtovar.acividadesapp.ui.screens.ActivityListScreen
import com.mtovar.acividadesapp.ui.screens.AddEditActivityScreen
import com.mtovar.acividadesapp.viewmodel.ActivityViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel: ActivityViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "list"
    ) {
        composable("list") {
            ActivityListScreen(
                viewModel = viewModel,
                onNavigateToDetail = { id ->
                    navController.navigate("detail/$id")
                },
                onNavigateToAdd = {
                    navController.navigate("add")
                }
            )
        }
        composable("add") {
            AddEditActivityScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable("detail/{activityId}") { backStackEntry ->
            val activityId = backStackEntry.arguments?.getString("activityId")
            ActivityDetailScreen(
                viewModel = viewModel,
                activityId = activityId,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToEdit = { id ->
                    navController.navigate("edit/$id")
                }
            )
        }
        composable("edit/{activityId}") { backStackEntry ->
            val activityId = backStackEntry.arguments?.getString("activityId")
            AddEditActivityScreen(
                viewModel = viewModel,
                activityId = activityId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
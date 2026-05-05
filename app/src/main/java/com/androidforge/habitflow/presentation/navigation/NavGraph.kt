package com.androidforge.habitflow.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.androidforge.habitflow.presentation.addedit.AddEditHabitScreen
import com.androidforge.habitflow.presentation.detail.HabitDetailScreen
import com.androidforge.habitflow.presentation.habits.HabitListScreen
import com.androidforge.habitflow.presentation.settings.SettingsScreen
import com.androidforge.habitflow.presentation.util.AdmobManager

@Composable
fun NavGraph(
    navController: NavHostController,
    admobManager: AdmobManager,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.HabitList.route,
        modifier = modifier
    ) {
        composable(route = Screen.HabitList.route) {
            HabitListScreen(
                onNavigateToAddEditHabit = { habitId ->
                    navController.navigate(Screen.AddEditHabit.createRoute(habitId))
                },
                onNavigateToDetail = { habitId ->
                    navController.navigate(Screen.HabitDetail.createRoute(habitId))
                },
                onNavigateToSettings = { navController.navigate(Screen.Settings.route) },
                admobManager = admobManager
            )
        }
        composable(
            route = Screen.AddEditHabit.route,
            arguments = listOf(
                navArgument(Screen.AddEditHabit.ARG_HABIT_ID) {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) {\ backStackEntry ->
            val habitId = backStackEntry.arguments?.getString(Screen.AddEditHabit.ARG_HABIT_ID)
            AddEditHabitScreen(
                habitId = habitId,
                onBack = { navController.popBackStack() },
                admobManager = admobManager
            )
        }
        composable(
            route = Screen.HabitDetail.route,
            arguments = listOf(
                navArgument(Screen.HabitDetail.ARG_HABIT_ID) {
                    type = NavType.StringType
                    nullable = false
                }
            )
        ) { backStackEntry ->
            val habitId = backStackEntry.arguments?.getString(Screen.HabitDetail.ARG_HABIT_ID)
            requireNotNull(habitId) { "Habit ID must be provided for HabitDetailScreen" }
            HabitDetailScreen(
                habitId = habitId,
                onBack = { navController.popBackStack() },
                admobManager = admobManager
            )
        }
        composable(route = Screen.Settings.route) {
            SettingsScreen(
                onBack = { navController.popBackStack() },
                admobManager = admobManager
            )
        }
    }
}
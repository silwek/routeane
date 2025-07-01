package com.silwek.routeane.ui.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.silwek.routeane.R
import com.silwek.routeane.ui.mode.ModesScreen
import com.silwek.routeane.ui.mode.ModesViewModel
import com.silwek.routeane.ui.todayplan.TodayPlanViewModel
import com.silwek.routeane.ui.todayplan.TodayScreenWithViewModel

@Composable
fun RouteaneApp(
    todayViewModel: TodayPlanViewModel,
    modesViewModel: ModesViewModel
) {
    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination?.route

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = stringResource(R.string.nav_todayplan)) },
                    label = { Text( stringResource(R.string.nav_todayplan)) },
                    selected = currentDestination == "today",
                    onClick = { navController.navigate("today") }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = stringResource(R.string.nav_modes)) },
                    label = { Text( stringResource(R.string.nav_modes)) },
                    selected = currentDestination == "modes",
                    onClick = { navController.navigate("modes") }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "today",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("today") {
                TodayScreenWithViewModel(viewModel = todayViewModel)
            }
            composable("modes") {
                ModesScreen(viewModel = modesViewModel)
            }
        }
    }
}
package com.silwek.routeane

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.silwek.routeane.data.AppPreferences
import com.silwek.routeane.data.DatabaseProvider
import com.silwek.routeane.data.populateDatabase
import com.silwek.routeane.data.repositories.PlannerRepository
import com.silwek.routeane.ui.routinedetail.RoutineDetailViewModel
import com.silwek.routeane.ui.main.RouteaneApp
import com.silwek.routeane.ui.mode.ModesViewModel
import com.silwek.routeane.ui.routines.RoutineItemsViewModel
import com.silwek.routeane.ui.theme.RouteaneTheme
import com.silwek.routeane.ui.theme.SystemBarColorEffect
import com.silwek.routeane.ui.todayplan.TodayPlanViewModel
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = DatabaseProvider.getDatabase(this)
        val preferences = AppPreferences(this)
        val repository = PlannerRepository(database)
        val todayViewModel = TodayPlanViewModel(repository, preferences)
        val modesViewModel = ModesViewModel(repository)
        val routineItemsViewModel = RoutineItemsViewModel(repository, preferences)
        val routineDetailViewModel = RoutineDetailViewModel(repository)

        lifecycleScope.launch {
            if (!repository.hasData()) {
                populateDatabase(database)
            }
        }

        setContent {
            RouteaneTheme {
                SystemBarColorEffect()
                RouteaneApp(
                    todayViewModel = todayViewModel,
                    modesViewModel = modesViewModel,
                    routineItemsViewModel = routineItemsViewModel,
                    routineDetailViewModel = routineDetailViewModel
                )
            }
        }
    }
}
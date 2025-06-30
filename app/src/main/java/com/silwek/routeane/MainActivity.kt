package com.silwek.routeane

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.silwek.routeane.data.DatabaseProvider
import com.silwek.routeane.data.repositories.PlannerRepository
import com.silwek.routeane.ui.theme.RouteaneTheme
import com.silwek.routeane.ui.theme.SystemBarColorEffect
import com.silwek.routeane.ui.todayplan.TodayPlanViewModel
import com.silwek.routeane.ui.todayplan.TodayScreenWithViewModel
import com.silwek.routeane.ui.todayplan.TodayViewModelFactory


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val context = this@MainActivity
        val database = DatabaseProvider.getDatabase(context)
        val repository = PlannerRepository(database)
        setContent {
            RouteaneTheme {
                val viewModel: TodayPlanViewModel = viewModel(
                    factory = TodayViewModelFactory(repository)
                )
                SystemBarColorEffect()
                TodayScreenWithViewModel(viewModel, dayOfWeek = 1, modeId = 2)
            }
        }
    }
}
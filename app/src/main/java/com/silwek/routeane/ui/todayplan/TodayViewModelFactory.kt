package com.silwek.routeane.ui.todayplan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.silwek.routeane.data.AppPreferences
import com.silwek.routeane.data.repositories.PlannerRepository

class TodayViewModelFactory(
    private val repository: PlannerRepository,
    private val preferences: AppPreferences
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TodayPlanViewModel::class.java)) {
            return TodayPlanViewModel(repository, preferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
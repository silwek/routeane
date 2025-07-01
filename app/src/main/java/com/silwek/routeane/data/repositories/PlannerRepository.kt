package com.silwek.routeane.data.repositories

import com.silwek.routeane.App
import com.silwek.routeane.data.AppDatabase
import com.silwek.routeane.data.DatabaseProvider
import com.silwek.routeane.data.entities.RoutineDayAssignmentWithItem
import com.silwek.routeane.data.entities.RoutineItem
import com.silwek.routeane.data.entities.RoutineMode
import kotlinx.coroutines.flow.Flow

class PlannerRepository(
    private val db: AppDatabase = DatabaseProvider.getDatabase(App.instance)
) {
    private val itemDao = db.routineItemDao()
    private val modeDao = db.routineModeDao()
    private val assignmentDao = db.routineDayAssignmentDao()

    fun getAllItemsFlow(): Flow<List<RoutineItem>> = itemDao.getAllItems()

    fun getAllModesFlow(): Flow<List<RoutineMode>> = modeDao.getAllModes()

    fun getAssignmentsForDayAndModeFlow(
        dayOfWeek: Int,
        modeId: Int
    ): Flow<List<RoutineDayAssignmentWithItem>> =
        assignmentDao.getAssignmentsWithItemsForDayAndModeFlow(dayOfWeek, modeId)

    suspend fun insertMode(mode: RoutineMode) {
        modeDao.insert(mode)
    }

    suspend fun updateModeName(modeId: Int, newName: String) {
        modeDao.updateModeName(modeId, newName)
    }
    suspend fun updateModeIcon(modeId: Int, newIcon: String) {
        modeDao.updateModeIcon(modeId, newIcon)
    }

    suspend fun deleteMode(mode: RoutineMode) {
        modeDao.delete(mode)
    }

    suspend fun hasData(): Boolean {
        return modeDao.getModesCount() > 0
    }
}
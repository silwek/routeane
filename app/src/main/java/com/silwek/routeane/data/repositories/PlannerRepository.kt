package com.silwek.routeane.data.repositories

import com.silwek.routeane.App
import com.silwek.routeane.data.AppDatabase
import com.silwek.routeane.data.DatabaseProvider
import com.silwek.routeane.data.entities.AssignmentForDay
import com.silwek.routeane.data.entities.RoutineDayAssignment
import com.silwek.routeane.data.entities.RoutineDayAssignmentWithItem
import com.silwek.routeane.data.entities.RoutineItem
import com.silwek.routeane.data.entities.RoutineMode
import kotlinx.coroutines.flow.Flow

class PlannerRepository(
    db: AppDatabase = DatabaseProvider.getDatabase(App.instance)
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

    fun getAssignmentsForItem(routineItemId: Int): Flow<List<AssignmentForDay>> {
        return assignmentDao.getAssignmentsForItem(routineItemId)
    }

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

    suspend fun insertAssignment(routineDayAssignment: RoutineDayAssignment) {
        assignmentDao.insert(routineDayAssignment)
    }


    suspend fun getAssignment(
        assignmentId: Int
    ): RoutineDayAssignment? =
        assignmentDao.getAssignment(assignmentId)

    suspend fun removeAssignment(itemId: Int) {
        assignmentDao.delete(
            RoutineDayAssignment(
                id = itemId,
                routineItemId = 0,
                dayOfWeek = 0,
                modeId = 0
            )
        )
    }

    suspend fun getRoutineItem(itemId: Int): RoutineItem? = itemDao.getItemById(itemId)

    suspend fun insertRoutineItem(name: String, duration: Int) {
        itemDao.insert(RoutineItem(name = name, defaultDurationMinutes = duration))
    }

    suspend fun updateRoutineItem(itemId: Int, name: String, duration: Int) {
        itemDao.update(RoutineItem(itemId, name, duration))
    }

    suspend fun updateRoutineItemIcon(itemId: Int, iconName: String) {
        itemDao.updateIcon(itemId, iconName = iconName)
    }

    suspend fun deleteRoutineItem(id: Int) {
        itemDao.delete(RoutineItem(id))
    }

}
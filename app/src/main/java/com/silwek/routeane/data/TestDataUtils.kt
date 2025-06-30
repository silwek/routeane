package com.silwek.routeane.data

import com.silwek.routeane.data.entities.RoutineDayAssignment
import com.silwek.routeane.data.entities.RoutineItem
import com.silwek.routeane.data.entities.RoutineMode
import timber.log.Timber

suspend fun populateDatabase(db: AppDatabase) {
    val modeDao = db.routineModeDao()
    val itemDao = db.routineItemDao()
    val assignmentDao = db.routineDayAssignmentDao()

    // Insert Modes
    val modeIds = listOf(
        modeDao.insert(RoutineMode(name = "Routine"))
            .also { Timber.d("Inserted item Routine id: $it") },
        modeDao.insert(RoutineMode(name = "Holidays")),
        modeDao.insert(RoutineMode(name = "Sick days"))
    )

    // Insert Items
    val itemIds = listOf(
        itemDao.insert(RoutineItem(name = "Make bed", defaultDurationMinutes = 5))
            .also { Timber.d("Inserted item Make bed id: $it") },
        itemDao.insert(RoutineItem(name = "Brush teeth", defaultDurationMinutes = 3)),
        itemDao.insert(RoutineItem(name = "Yoga", defaultDurationMinutes = 20)),
        itemDao.insert(RoutineItem(name = "Check emails", defaultDurationMinutes = 10))
    )

    // Insert Assignments
    assignmentDao.insert(
        RoutineDayAssignment(
            routineItemId = itemIds[0].toInt(),
            dayOfWeek = RoutineDayAssignment.EVERYDAY,
            modeId = modeIds[0].toInt()
        )
    )
    assignmentDao.insert(
        RoutineDayAssignment(
            routineItemId = itemIds[1].toInt(),
            dayOfWeek = RoutineDayAssignment.EVERYDAY,
            modeId = modeIds[0].toInt()
        )
    ).also { Timber.d("Inserted item Brush teeth everyday id: $it") }
    assignmentDao.insert(
        RoutineDayAssignment(
            routineItemId = itemIds[2].toInt(), dayOfWeek = 1, modeId = modeIds[0].toInt()
        )
    )
    assignmentDao.insert(
        RoutineDayAssignment(
            routineItemId = itemIds[3].toInt(), dayOfWeek = 2, modeId = modeIds[1].toInt()
        )
    )
}
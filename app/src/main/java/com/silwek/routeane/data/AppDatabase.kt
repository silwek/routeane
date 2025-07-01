package com.silwek.routeane.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.silwek.routeane.data.dao.RoutineDayAssignmentDao
import com.silwek.routeane.data.dao.RoutineItemDao
import com.silwek.routeane.data.dao.RoutineModeDao
import com.silwek.routeane.data.entities.RoutineDayAssignment
import com.silwek.routeane.data.entities.RoutineItem
import com.silwek.routeane.data.entities.RoutineMode

@Database(
    entities = [RoutineItem::class, RoutineMode::class, RoutineDayAssignment::class],
    version = 2,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun routineItemDao(): RoutineItemDao
    abstract fun routineModeDao(): RoutineModeDao
    abstract fun routineDayAssignmentDao(): RoutineDayAssignmentDao
}
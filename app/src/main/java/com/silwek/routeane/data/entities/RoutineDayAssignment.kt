package com.silwek.routeane.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "routine_day_assignments",
    foreignKeys = [
        ForeignKey(
            entity = RoutineItem::class,
            parentColumns = ["id"],
            childColumns = ["routineItemId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = RoutineMode::class,
            parentColumns = ["id"],
            childColumns = ["modeId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("routineItemId"), Index("modeId")]
)
data class RoutineDayAssignment(
    @PrimaryKey(autoGenerate = true) val id: Int = EVERYDAY,
    val routineItemId: Int,
    val dayOfWeek: Int,
    val modeId: Int
) {
    companion object {
        const val EVERYDAY = 0
        const val MONDAY = 1
        const val TUESDAY = 2
        const val WEDNESDAY = 3
        const val THURSDAY = 4
        const val FRIDAY = 5
        const val SATURDAY = 6
        const val SUNDAY = 7
    }
}

data class RoutineDayAssignmentWithItem(
    val assignmentId: Int,
    val dayOfWeek: Int,
    val modeId: Int,
    val itemId: Int,
    val itemName: String,
    val itemDefaultDuration: Int?
)
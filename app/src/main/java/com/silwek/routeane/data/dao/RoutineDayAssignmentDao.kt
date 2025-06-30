package com.silwek.routeane.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import com.silwek.routeane.data.entities.RoutineDayAssignmentWithItem
import com.silwek.routeane.data.entities.RoutineDayAssignment
import kotlinx.coroutines.flow.Flow

@Dao
interface RoutineDayAssignmentDao {
    @Insert
    suspend fun insert(assignment: RoutineDayAssignment): Long

    @Delete
    suspend fun delete(assignment: RoutineDayAssignment)

    @Query("SELECT * FROM routine_day_assignments")
    fun getAllAssignments(): Flow<List<RoutineDayAssignment>>

    @Query("""
        SELECT * FROM routine_day_assignments
        WHERE (dayOfWeek = :dayOfWeek OR dayOfWeek = ${RoutineDayAssignment.EVERYDAY})
        AND modeId = :modeId
    """)
    fun getAssignmentsForDayAndMode(dayOfWeek: Int, modeId: Int): Flow<List<RoutineDayAssignment>>



    @Query("""
        SELECT 
            ra.id AS assignmentId,
            ra.dayOfWeek,
            ra.modeId,
            ri.id AS itemId,
            ri.name AS itemName,
            ri.defaultDurationMinutes AS itemDefaultDuration
        FROM routine_day_assignments AS ra
        INNER JOIN routine_items AS ri
        ON ra.routineItemId = ri.id
        WHERE (ra.dayOfWeek = :dayOfWeek OR ra.dayOfWeek = ${RoutineDayAssignment.EVERYDAY})
        AND ra.modeId = :modeId
    """)
    fun getAssignmentsWithItemsForDayAndModeFlow(
        dayOfWeek: Int,
        modeId: Int
    ): Flow<List<RoutineDayAssignmentWithItem>>
}
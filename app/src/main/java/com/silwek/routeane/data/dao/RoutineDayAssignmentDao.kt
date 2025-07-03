package com.silwek.routeane.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.silwek.routeane.data.entities.AssignmentForDay
import com.silwek.routeane.data.entities.RoutineDayAssignment
import com.silwek.routeane.data.entities.RoutineDayAssignmentWithItem
import kotlinx.coroutines.flow.Flow

@Dao
interface RoutineDayAssignmentDao {
    @Insert
    suspend fun insert(assignment: RoutineDayAssignment): Long

    @Delete
    suspend fun delete(assignment: RoutineDayAssignment)

    @Query("SELECT * FROM routine_day_assignments")
    fun getAllAssignments(): Flow<List<RoutineDayAssignment>>

    @Query(
        """
        SELECT * FROM routine_day_assignments
        WHERE (dayOfWeek = :dayOfWeek OR dayOfWeek = ${RoutineDayAssignment.EVERYDAY})
        AND modeId = :modeId
    """
    )
    fun getAssignmentsForDayAndMode(dayOfWeek: Int, modeId: Int): Flow<List<RoutineDayAssignment>>

    @Query(
        """
        SELECT * FROM routine_day_assignments
        WHERE id = :assignmentId
    """
    )
    suspend fun getAssignment(assignmentId: Int): RoutineDayAssignment?


    @Query(
        """
        SELECT 
            ra.id AS assignmentId,
            ra.dayOfWeek,
            ra.modeId,
            ri.id AS itemId,
            ri.name AS itemName,
            ri.defaultDurationMinutes AS itemDefaultDuration,
            ri.iconName AS itemIconName
        FROM routine_day_assignments AS ra
        INNER JOIN routine_items AS ri
        ON ra.routineItemId = ri.id
        WHERE (ra.dayOfWeek = :dayOfWeek OR ra.dayOfWeek = ${RoutineDayAssignment.EVERYDAY})
        AND ra.modeId = :modeId
    """
    )
    fun getAssignmentsWithItemsForDayAndModeFlow(
        dayOfWeek: Int,
        modeId: Int
    ): Flow<List<RoutineDayAssignmentWithItem>>

    @Query(
        """
    SELECT ra.dayOfWeek, rm.id as modeId, rm.name as modeName, rm.iconName as iconName, ra.id as assignmentId
    FROM routine_day_assignments AS ra
    INNER JOIN routine_modes AS rm ON rm.id = ra.modeId
    WHERE ra.routineItemId = :routineItemId
"""
    )
    fun getAssignmentsForItem(routineItemId: Int): Flow<List<AssignmentForDay>>
}
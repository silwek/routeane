package com.silwek.routeane.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.silwek.routeane.data.entities.RoutineMode
import kotlinx.coroutines.flow.Flow

@Dao
interface RoutineModeDao {
    @Insert
    suspend fun insert(mode: RoutineMode): Long

    @Update
    suspend fun update(mode: RoutineMode)

    @Delete
    suspend fun delete(mode: RoutineMode)

    @Query("SELECT * FROM routine_modes")
    fun getAllModes(): Flow<List<RoutineMode>>

    @Query("SELECT * FROM routine_modes WHERE id = :id")
    suspend fun getModeById(id: Int): RoutineMode?
}
package com.silwek.routeane.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.silwek.routeane.data.entities.RoutineItem
import com.silwek.routeane.ui.components.RouteaneIcons
import kotlinx.coroutines.flow.Flow

@Dao
interface RoutineItemDao {
    @Insert
    suspend fun insert(item: RoutineItem): Long

    @Update
    suspend fun update(item: RoutineItem)

    @Query("UPDATE routine_items SET iconName = :iconName WHERE id = :id")
    suspend fun updateIcon(id: Int, iconName: String)

    @Delete
    suspend fun delete(item: RoutineItem)

    @Query("SELECT * FROM routine_items")
    fun getAllItems(): Flow<List<RoutineItem>>

    @Query("SELECT * FROM routine_items WHERE id = :id")
    suspend fun getItemById(id: Int): RoutineItem?
}
package com.silwek.routeane.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "routine_items")
data class RoutineItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val defaultDurationMinutes: Int? = null
)
package com.silwek.routeane.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.silwek.routeane.ui.components.RouteaneIcons

@Entity(tableName = "routine_items")
data class RoutineItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String = "",
    val defaultDurationMinutes: Int? = null,
    val iconName: String = RouteaneIcons.EMPTY_ICON // ex: "ic_work"
)
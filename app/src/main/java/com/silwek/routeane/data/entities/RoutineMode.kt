package com.silwek.routeane.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.silwek.routeane.ui.components.RouteaneIcons

@Entity(tableName = "routine_modes")
data class RoutineMode(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String, // example: "holidays", "sick", "full of energy"
    val iconName: String = RouteaneIcons.EMPTY_ICON // ex: "ic_work"
)
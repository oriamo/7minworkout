package com.example.a7minworkout

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "history_table")
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: String = "",
    val completed_sets: String = "",
    val completed_time: String = "",
    val isCompleted : Boolean = false
)
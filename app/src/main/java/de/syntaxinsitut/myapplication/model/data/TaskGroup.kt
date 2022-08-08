package de.syntaxinsitut.myapplication.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey

// ktlint-disable filename
@Entity
data class TaskGroup(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val color: Int
)

package de.syntaxinsitut.myapplication.model.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = arrayOf(
        ForeignKey(
            entity = TaskGroup::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("groupId"),
            onDelete = ForeignKey.CASCADE
        )
    )
)
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val task: String,
    val groupId: Long,
    var done: Boolean
)

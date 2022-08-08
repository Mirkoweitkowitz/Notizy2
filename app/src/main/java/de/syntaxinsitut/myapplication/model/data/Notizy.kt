package de.syntaxinsitut.myapplication.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Notizy(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    var title: String,
    var note: String,
    var color: Int
)

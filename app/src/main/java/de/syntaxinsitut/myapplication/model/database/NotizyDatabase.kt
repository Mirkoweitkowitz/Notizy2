package de.syntaxinsitut.myapplication.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import de.syntaxinsitut.myapplication.model.data.Notizy
import de.syntaxinsitut.myapplication.model.data.Task
import de.syntaxinsitut.myapplication.model.data.TaskGroup

@Database(entities = [Notizy::class, TaskGroup::class, Task::class], version = 1)
abstract class NotizyDatabase : RoomDatabase() {
    abstract val notizyDatabaseDao: NotizyDatabaseDao
}

private lateinit var INSTANCE: NotizyDatabase

@Synchronized
fun getDatabase(context: Context): NotizyDatabase {

    if (!::INSTANCE.isInitialized) {
        INSTANCE = Room.databaseBuilder(
            context.applicationContext,
            NotizyDatabase::class.java,
            "notizy_database"
        ).build()
    }

    return INSTANCE
}

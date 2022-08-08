package de.syntaxinsitut.myapplication.model.database

import androidx.lifecycle.LiveData
import androidx.room.* // ktlint-disable no-wildcard-imports
import de.syntaxinsitut.myapplication.model.data.Notizy
import de.syntaxinsitut.myapplication.model.data.Task
import de.syntaxinsitut.myapplication.model.data.TaskGroup

@Dao
interface NotizyDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(notizy: Notizy)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTaskGroup(taskGroup: TaskGroup): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    @Update
    suspend fun updateTaskGroup(taskGroup: TaskGroup)

    @Update
    suspend fun updateTask(task: Task)

    @Update
    suspend fun update(notizy: Notizy)

    @Query("SELECT * FROM Notizy")
    fun getAllNotizy(): LiveData<List<Notizy>>

    @Query("SELECT * FROM TaskGroup")
    fun getAllTaskGroup(): LiveData<List<TaskGroup>>

    @Query("SELECT * FROM Task WHERE groupId = :groupId")
    fun getAllTaskById(groupId: Long): LiveData<List<Task>>

    @Query("SELECT * FROM Task")
    fun getAllTask(): LiveData<List<Task>>

    @Query("DELETE FROM Notizy WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM TaskGroup WHERE id = :id")
    suspend fun deleteTaskGroupById(id: Long)
}

package de.syntaxinsitut.myapplication.model.repos

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import de.syntaxinsitut.myapplication.api.UserApi
import de.syntaxinsitut.myapplication.model.data.Notizy
import de.syntaxinsitut.myapplication.model.data.Task
import de.syntaxinsitut.myapplication.model.data.TaskGroup
import de.syntaxinsitut.myapplication.model.data.User
import de.syntaxinsitut.myapplication.model.database.NotizyDatabase
import java.lang.Exception

val TAG = "REPOSITORY"

class DataBaseRepository(private val database: NotizyDatabase,private val api:UserApi) {


    val notizyList: LiveData<List<Notizy>> = database.notizyDatabaseDao.getAllNotizy()
    val taskGroupList: LiveData<List<TaskGroup>> = database.notizyDatabaseDao.getAllTaskGroup()
    val taskList: LiveData<List<Task>> = database.notizyDatabaseDao.getAllTask()

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>>
        get() = _users

    suspend fun getUsers() {
        try {
            _users.value = api.retrofitService.getUsers()
        } catch (e: Exception) {
            Log.e(TAG, "Error loading Data from API: $e")
        }
    }

    suspend fun createUser(user: User) {
        try {
            api.retrofitService.createUser(user)
        } catch (e: Exception) {
            Log.e(TAG, "Error putting Data on API: $e")
        }
    }

    suspend fun insert(notizy: Notizy) {
        try {
            database.notizyDatabaseDao.insert(notizy)
        } catch (ex: Exception) {
            Log.e(TAG, ex.toString())
        }
    }

    suspend fun insertTask(task: Task) {
        try {
            database.notizyDatabaseDao.insertTask(task)
        } catch (ex: Exception) {
            Log.e(TAG, ex.toString())
        }
    }

    suspend fun insertTaskGroup(taskGroup: TaskGroup): Long? {
        try {
            return database.notizyDatabaseDao.insertTaskGroup(taskGroup)
        } catch (ex: Exception) {
            Log.e(TAG, ex.toString())
            return null
        }
    }

    suspend fun update(notizy: Notizy) {
        try {
            database.notizyDatabaseDao.update(notizy)
        } catch (ex: Exception) {
            Log.e(TAG, ex.toString())
        }
    }

    suspend fun updateTask(task: Task) {
        try {
            database.notizyDatabaseDao.updateTask(task)
        } catch (ex: Exception) {
            Log.e(TAG, ex.toString())
        }
    }

    suspend fun updateTaskGroup(taskGroup: TaskGroup) {
        try {
            database.notizyDatabaseDao.updateTaskGroup(taskGroup)
        } catch (ex: Exception) {
            Log.e(TAG, ex.toString())
        }
    }

    suspend fun deleteById(id: Long) {
        try {
            database.notizyDatabaseDao.deleteById(id)
        } catch (ex: Exception) {
            Log.e(TAG, ex.toString())
        }
    }

    suspend fun deleteTaskGroupById(id: Long) {
        try {
            database.notizyDatabaseDao.deleteTaskGroupById(id)
        } catch (ex: Exception) {
            Log.e(TAG, ex.toString())
        }
    }

    suspend fun delete(notizy: Notizy) {
        try {
            database.notizyDatabaseDao.deleteById(notizy.id)
        } catch (ex: Exception) {
            Log.e(TAG, ex.toString())
        }
    }
}

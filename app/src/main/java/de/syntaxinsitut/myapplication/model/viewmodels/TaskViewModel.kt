package de.syntaxinsitut.myapplication.model.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import de.syntaxinsitut.myapplication.model.data.Task
import de.syntaxinsitut.myapplication.model.data.TaskGroup
import de.syntaxinsitut.myapplication.model.database.getDatabase
import de.syntaxinsitut.myapplication.model.repos.DataBaseRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val _pickedColor = MutableLiveData<Int>()
    val pickedColor: LiveData<Int>
        get() = _pickedColor

    val database = getDatabase(application)
    val dataBaseRepository = DataBaseRepository(database)

    lateinit var insertTaskGroup: Job

    private val _currentInsertedId = MutableLiveData<Long>()
    val currentInsertedId: LiveData<Long>
        get() = _currentInsertedId

    val taskGroupList = dataBaseRepository.taskGroupList
    val taskList = dataBaseRepository.taskList

    fun deleteById(id: Long) {
        viewModelScope.launch {
            database.notizyDatabaseDao.deleteTaskGroupById(id)
        }
    }

    fun insertTaskGroup(taskGroup: TaskGroup) {
        insertTaskGroup =
            viewModelScope.launch {
                _currentInsertedId.value = dataBaseRepository.insertTaskGroup(taskGroup)
            }
    }

    fun insertTask(task: Task) {
        viewModelScope.launch {
            dataBaseRepository.insertTask(task)
        }
    }

    fun setPickedColor(color: Int) {
        _pickedColor.value = color
    }

    fun updateTask(task: Task) {
        viewModelScope.launch { dataBaseRepository.updateTask(task)
        }
    }

    fun searchNotes(task: Task) {
        viewModelScope.launch { dataBaseRepository.taskList }
    }
}

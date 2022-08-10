package de.syntaxinsitut.myapplication.model.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import de.syntaxinsitut.myapplication.api.UserApi
import de.syntaxinsitut.myapplication.model.data.Notizy
import de.syntaxinsitut.myapplication.model.database.getDatabase
import de.syntaxinsitut.myapplication.model.repos.DataBaseRepository
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    val database = getDatabase(application)
    val dataBaseRepository = DataBaseRepository(database,UserApi)
    val notizies = dataBaseRepository.notizyList

    private val _pickedColor = MutableLiveData<Int>()
    val pickedColor: LiveData<Int>
        get() = _pickedColor

    fun setPickedColor(color: Int) {
        _pickedColor.value = color
    }

    private val _currentSelectedNotizy = MutableLiveData<Notizy>()
    val currentSelectedNotizy: LiveData<Notizy>
        get() = _currentSelectedNotizy

    fun insertNotizy(notizy: Notizy) {
        viewModelScope.launch {
            dataBaseRepository.insert(notizy)
        }
    }

    fun updateNotizy(notizy: Notizy) {
        viewModelScope.launch {
            dataBaseRepository.update(notizy)
        }
    }

    fun deleteById(id: Long) {
        viewModelScope.launch {
            dataBaseRepository.deleteById(id)
        }
    }

    fun setCurrentNotizy(notizy: Notizy) {
        _currentSelectedNotizy.value = notizy
    }
}

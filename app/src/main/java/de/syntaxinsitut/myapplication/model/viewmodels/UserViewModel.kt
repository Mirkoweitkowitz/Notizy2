package de.syntaxinsitut.myapplication.model.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import de.syntaxinsitut.myapplication.api.UserApi
import de.syntaxinsitut.myapplication.model.data.User
import de.syntaxinsitut.myapplication.model.database.getDatabase
import de.syntaxinsitut.myapplication.model.repos.DataBaseRepository
import kotlinx.coroutines.launch

class UserViewModel (application: Application) : AndroidViewModel(application){

    private val database = getDatabase(application)
    private val repository = DataBaseRepository(database, UserApi)

    val users = repository.users

    fun loadData() {
        viewModelScope.launch {
            repository.getUsers()
        }
    }

    fun addNewUser(user: User) {
        viewModelScope.launch {
            repository.createUser(user)
        }
    }
}
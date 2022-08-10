package de.syntaxinsitut.myapplication.model.viewmodels

import android.app.Application
import android.graphics.Bitmap
import android.provider.ContactsContract
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import de.syntaxinsitut.myapplication.api.UserApi
import de.syntaxinsitut.myapplication.model.data.Notizy
import de.syntaxinsitut.myapplication.model.database.getDatabase
import de.syntaxinsitut.myapplication.model.repos.DataBaseRepository
import kotlinx.coroutines.launch



const val TAG = "FireBaseViewModel"

/**
 * Das FireBaseViewModel kümmert sich um die Kommunikation mit der Firebase Authentication
 * um einen SHA-1 Key zu generieren einfach folgene Zeilen ins Terminal kopieren
 * >>keytool -alias androiddebugkey -keystore ~/.android/debug.keystore -list -v -storepass android<<
 */
class FireBaseViewModel (application: Application) : AndroidViewModel(application){

    // Kommunikationspunkt mit der FirebaseAuth
    private val firebaseAuth = FirebaseAuth.getInstance()

    private val database = getDatabase(application)
    private val repository = DataBaseRepository(database, UserApi)

    val notizyList = repository.notizyList

    // currentuser ist null wenn niemand eingeloggt ist

    private val _currentUser = MutableLiveData<FirebaseUser?>(firebaseAuth.currentUser)
    val currentUser: LiveData<FirebaseUser?>
        get() = _currentUser

    private val _complete = MutableLiveData<Boolean>()
    val complete: LiveData<Boolean>
        get() = _complete

    private val _currentImageBitmap = MutableLiveData<Bitmap>()
    val currentImageBitmap: LiveData<Bitmap>
        get() = _currentImageBitmap

    // hier wird versucht einen User zu erstellen um diesen anschließend auch gleich
    // einzuloggen
    fun signUp(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                login(email, password)
            } else {
                Log.e(TAG, "SignUp failed: ${it.exception}")
            }
        }
    }

     fun login(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                _currentUser.value = firebaseAuth.currentUser
            } else {
                Log.e(TAG, "Login feiled: ${it.exception}")
            }
        }

        fun logout() {
            firebaseAuth.signOut()
            _currentUser.value = firebaseAuth.currentUser
        }
    }
    //    hier wird eine Nortiz erstellt
    fun insertNotiz(notizy: Notizy) {
        viewModelScope.launch {
            repository.insert(notizy)
            _complete.value = true
        }
    }
    // hier wird ein update von einer notiz durchgeführt
    fun updateNotiz(notizy: Notizy) {
        viewModelScope.launch {
            repository.update(notizy)
            _complete.value = true
        }
    }
    //  hier kann man eine notiz löschen
    fun deleteNotiz(notizy: Notizy) {
        viewModelScope.launch {
            repository.delete(notizy)
            _complete.value = true
        }
    }

    fun unsetComplete() {
        _complete.value = false
    }

    fun setBitmap(b: Bitmap) {
        _currentImageBitmap.value = b
    }

}
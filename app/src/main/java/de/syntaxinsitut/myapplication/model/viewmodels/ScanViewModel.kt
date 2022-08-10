package de.syntaxinsitut.myapplication.model.viewmodels

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import de.syntaxinsitut.myapplication.model.repos.ScanRepository

class ScanViewModel(application: Application) : AndroidViewModel(application) {

    val repository = ScanRepository()

    private val _currentImageBitmap = MutableLiveData<Bitmap>()
    val currentImageBitmap: LiveData<Bitmap>
        get() = _currentImageBitmap

    fun setCurrentBitmap(bitmap: Bitmap) {
        _currentImageBitmap.value = bitmap
    }
    fun upload(fileName: String){
        repository.upload(fileName,_currentImageBitmap.value!!)
    }
}

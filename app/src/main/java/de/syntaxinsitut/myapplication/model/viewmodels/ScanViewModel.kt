package de.syntaxinsitut.myapplication.model.viewmodels

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class ScanViewModel(application: Application) : AndroidViewModel(application) {

    private val _currentImageBitmap = MutableLiveData<Bitmap>()
    val currentImageBitmap: LiveData<Bitmap>
        get() = _currentImageBitmap

    fun setCurrentBitmap(bitmap: Bitmap) {
        _currentImageBitmap.value = bitmap
    }
}

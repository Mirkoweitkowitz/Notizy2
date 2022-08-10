package de.syntaxinsitut.myapplication.model.viewmodels

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import de.syntaxinsitut.myapplication.model.data.Scan
import de.syntaxinsitut.myapplication.model.repos.ScanRepository

class ScanViewModel(application: Application) : AndroidViewModel(application) {

    val repository = ScanRepository()
    val scanList: LiveData<List<Scan>> = repository.scanList

    private val _currentImageBitmap = MutableLiveData<Bitmap>()
    val currentImageBitmap: LiveData<Bitmap>
        get() = _currentImageBitmap

    fun setCurrentBitmap(bitmap: Bitmap) {
        _currentImageBitmap.value = bitmap
    }
    fun upload(fileName: String) {
        repository.upload(fileName, _currentImageBitmap.value!!)
    }

    fun downloadImage(uri: String, iv: ImageView, context: Context) {
        repository.downloadImage(uri, iv, context)
    }
}

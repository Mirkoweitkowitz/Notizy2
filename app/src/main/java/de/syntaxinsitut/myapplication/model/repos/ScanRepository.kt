package de.syntaxinsitut.myapplication.model.repos

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import de.syntaxinsitut.myapplication.model.data.Scan
import java.io.ByteArrayOutputStream

class ScanRepository {

    private val _scanList = MutableLiveData<List<Scan>>()
    val scanList: MutableLiveData<List<Scan>>
        get() = _scanList

    val scanImageDataBase = Firebase.firestore

    fun upload(fileName: String, bitmap: Bitmap) {

        val storageRef = FirebaseStorage.getInstance().getReference("Scans/$fileName")
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        storageRef.putBytes(data).addOnSuccessListener {
            println(it.metadata!!.reference)

        }
//gs://notizy-2f742.appspot.com/Scans/dennis
    }
}

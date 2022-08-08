package de.syntaxinsitut.myapplication.model.repos

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import de.syntaxinsitut.myapplication.model.data.Scan

class ScanRepository {

    private val _scanList = MutableLiveData<List<Scan>>()
    val scanList: MutableLiveData<List<Scan>>
        get() = _scanList

    val scanImageDataBase = Firebase.firestore

    /** fun upload(fileName: String, bitmap: Bitmap) {

     val storageRef = FirebaseStorage.getInstance().getReference("images/$fileName")
     val baos = ByteArrayOutputStream()
     bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
     val data = baos.toByteArray()

     storageRef.putBytes(data)
     }**/
}

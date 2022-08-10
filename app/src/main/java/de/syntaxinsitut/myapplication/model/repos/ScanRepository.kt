package de.syntaxinsitut.myapplication.model.repos

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import coil.load
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import de.syntaxinsitut.myapplication.model.data.Scan
import java.io.ByteArrayOutputStream

class ScanRepository {

    private val _scanList = MutableLiveData<List<Scan>>()
    val scanList: MutableLiveData<List<Scan>>
        get() = _scanList

    val scanImageDataBase = FirebaseFirestore.getInstance()

    init {
        scanImageDataBase.collection("Scans")
            .addSnapshotListener { value, error ->
                val mutableScans = mutableListOf<Scan>()
                for (document in value!!) {
                    try {
                        val scan = Scan(
                            document.data.get("title") as String,
                            document.data.get("uri") as String,
                        )
                        mutableScans.add(scan)
                    }catch (e: Exception){
                        continue
                    }

                }

                _scanList.value = mutableScans
            }
    }

    fun upload(fileName: String, bitmap: Bitmap) {

        val storageRef = FirebaseStorage.getInstance().getReference("Scans/$fileName")
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        storageRef.putBytes(data).addOnSuccessListener {
            val scan = Scan(fileName, it.metadata!!.reference.toString())
            scanImageDataBase.collection("Scans")
                .add(
                    hashMapOf(
                        "title" to scan.title,
                        "uri" to scan.uri
                    )
                )
        }
    }

    fun getAllScans() {
        scanImageDataBase.collection("Scans")
            .get()
            .addOnSuccessListener { result ->
                val mutableScans = mutableListOf<Scan>()
                for (document in result) {
                    val scan = Scan(
                        document.data.get("title") as String,
                        document.data.get("uri") as String,
                    )
                    mutableScans.add(scan)
                }

                _scanList.value = mutableScans
            }
    }

    fun downloadImage(refrence: String, imageView: ImageView, context: Context) {
        val gsReference = FirebaseStorage.getInstance().getReferenceFromUrl(refrence)
        println(gsReference)

        gsReference.getDownloadUrl().addOnSuccessListener(
            OnSuccessListener<Any> { uri ->
                imageView.load(uri.toString()) {
                    crossfade(true)
                }
            }
        ).addOnFailureListener(
            OnFailureListener {
                // Handle any errors
            }
        )
    }
}

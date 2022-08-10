package de.syntaxinsitut.myapplication.adapter

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import de.syntaxinsitut.myapplication.R
import de.syntaxinsitut.myapplication.model.data.Scan

class ScanAdapter(
    val dataset: List<Scan>,
    val context: Context,
    val downloadImageCallback: (String, ImageView, Context) -> Unit
) : RecyclerView.Adapter<ScanAdapter.ScanViewHolder>() {

    inner class ScanViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val titleTV = itemview.findViewById<TextView>(R.id.tv_scan_title)
        val scanIV = itemview.findViewById<ImageView>(R.id.iv_scan_preview)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScanViewHolder {
        return ScanViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.scan_item, parent, false))
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: ScanViewHolder, position: Int) {
        try {
            val scan = dataset[position]
            holder.titleTV.text = scan.title
            downloadImageCallback(scan.uri, holder.scanIV, context)
        }catch (ex: Exception){
            Log.d("", "")
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}

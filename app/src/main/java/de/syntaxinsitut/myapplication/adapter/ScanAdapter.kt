package de.syntaxinsitut.myapplication.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import de.syntaxinsitut.myapplication.R
import de.syntaxinsitut.myapplication.model.data.Notizy
import de.syntaxinsitut.myapplication.model.data.Scan

class ScanAdapter(
    val dataset: List<Scan>,
    val context: Context
) : RecyclerView.Adapter<ScanAdapter.ScanViewHolder>() {

    inner class ScanViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScanViewHolder {
        return ScanViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.scan_item, parent, false))
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: ScanViewHolder, position: Int) {
        val scan = dataset[position]
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}

package de.syntaxinsitut.myapplication.adapter

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import de.syntaxinsitut.myapplication.OnDragStartListener
import de.syntaxinsitut.myapplication.R
import de.syntaxinsitut.myapplication.model.data.Notizy
import de.syntaxinsitut.myapplication.ui.NotizyFragmentDirections

class NotizyAdapter(
    val dataset: MutableList<Notizy>,
    val context: Context,
    val deleteCallback: (Long) -> Unit,
    val setCallback: (Notizy) -> Unit,
    val listener: OnDragStartListener?
) : RecyclerView.Adapter<NotizyAdapter.NotizenViewHolder>() {

    inner class NotizenViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val notizyTitle = itemview.findViewById<TextView>(R.id.tv_notizy_item_title)
        val notizyNote = itemview.findViewById<TextView>(R.id.tv_notizy_item_note)
        val noitzyDeleteButton = itemview.findViewById<FloatingActionButton>(R.id.fb_delete_notizy_item)
        val cardView = itemview.findViewById<MaterialCardView>(R.id.cv_notizy_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotizenViewHolder {
        return NotizenViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.notizy_item, parent, false))
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: NotizenViewHolder, position: Int) {
        val notizy = dataset[position]

        holder.notizyTitle.text = notizy.title
        holder.notizyNote.text = notizy.note
        holder.cardView.setCardBackgroundColor(context.getColor(notizy.color))

        holder.noitzyDeleteButton.setOnClickListener {
            deleteCallback(notizy.id)
        }

        holder.cardView.setOnClickListener {
            setCallback(notizy)
            holder.itemView.findNavController().navigate(NotizyFragmentDirections.actionNotizyFragmentToEditNotizyFragment())
        }

        holder.cardView.setOnTouchListener { view, motionEvent ->
            if (motionEvent.actionMasked == MotionEvent.ACTION_DOWN) {
                try {
                    listener!!.onDragStart(holder)
                } catch (ex: Exception) {
                    Log.e("LISTENER ERRROR", ex.toString())
                }
            }
            false
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    fun moveNotizyViewItem(from: Int, to: Int) {
        val notizyToMove: Notizy = dataset.get(from)
        dataset.removeAt(from)
        if (to < from) {
            dataset.add(to, notizyToMove)
        } else {
            dataset.add(to - 1, notizyToMove)
        }
    }
}

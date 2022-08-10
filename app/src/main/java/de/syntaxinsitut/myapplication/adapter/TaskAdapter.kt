package de.syntaxinsitut.myapplication.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import de.syntaxinsitut.myapplication.R
import de.syntaxinsitut.myapplication.model.data.Notizy
import de.syntaxinsitut.myapplication.model.data.Task

class TaskAdapter(
    private var dataset: List<Task>,
    val context: Context,
    val updateCallback: (Task) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val checkBox = itemview.findViewById<CheckBox>(R.id.task_check_box)
        val title = itemview.findViewById<TextView>(R.id.detail_task_title_tv)
        val back = itemview.findViewById<ImageButton>(R.id.imgBackButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false))
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = dataset[position]

        holder.checkBox.isChecked = task.done
        holder.checkBox.text = task.task
        holder.checkBox.setOnCheckedChangeListener { compoundButton, b ->
            task.done = b
            updateCallback(task)
        }

        holder.back.setOnClickListener{
            holder.itemView.findNavController().navigateUp()
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

//    fun setData(arrNotesList: List<Notizy>) {
//        dataset = arrNotesList as List<Task>,
//    }
}
